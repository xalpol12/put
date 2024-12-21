package main

import (
	"flag"
	"misra-token-passing/misra"
	"misra-token-passing/network"
	"misra-token-passing/utils"
)

var (
	nextInRing network.ConnectionInfo
	nodePort   int
)

func main() {
	setVariables()
	network.Listen(misra.Receive, nodePort)
}

func setVariables() {
	nodePortPtr := flag.Int("node_port", 0, "Port number of the node in the ring")
	nextIpPtr := flag.String("next_ip", "", "IP address of the next node in the ring")
	nextPortPtr := flag.Int("next_port", 0, "Port number of the next node in the ring")

	flag.Parse()

	nodePort = *nodePortPtr
	if nodePort == 0 {
		utils.GetIntEnvOrDefault("NODE_PORT", 8089)
	}

	nextIp := *nextIpPtr
	if nextIp == "" {
		utils.GetEnvOrDefault("NEXT_IP", "192.168.0.1")
	}

	nextPort := *nextPortPtr
	if nextPort == 0 {
		utils.GetIntEnvOrDefault("NEXT_PORT", 8090)
	}

	nextInRing = network.ConnectionInfo{Address: nextIp, Port: nextPort}
}
