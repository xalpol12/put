package utils

import (
	"flag"
	"misra-token-passing/network"
)

var (
	isInit     bool
	nextInRing network.ConnectionInfo
	nodePort   int
)

func ReadArguments() (bool, int, *network.ConnectionInfo) {
	isInitPtr := flag.Bool("initiator", false, "Is initiator?")
	nodePortPtr := flag.Int("node_port", 8089, "Port number of the node in the ring")
	nextIpPtr := flag.String("next_ip", "127.0.0.1", "IP address of the next node in the ring")
	nextPortPtr := flag.Int("next_port", 8090, "Port number of the next node in the ring")

	flag.Parse()

	isInit = *isInitPtr
	nodePort = *nodePortPtr
	nextIp := *nextIpPtr
	nextPort := *nextPortPtr
	nextInRing = network.ConnectionInfo{Address: nextIp, Port: nextPort}
	return isInit, nodePort, &nextInRing
}
