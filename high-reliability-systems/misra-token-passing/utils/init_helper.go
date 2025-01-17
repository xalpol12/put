package utils

import (
	"flag"
	"fmt"
	"misra-token-passing/network"
	"os"
)

var (
	isInit     bool
	nextInRing network.ConnectionInfo
	nodePort   int
)

func ReadArguments() (bool, int, *network.ConnectionInfo) {
	isInitPtr := flag.Bool("initiator", false, "Is initiator?")
	nodePortPtr := flag.Int("node_port", 8089, "Port number of the node in the ring")
	nextIpPtr := flag.String("next_ip", "", "IP address of the next node in the ring")
	nextPortPtr := flag.Int("next_port", 0, "Port number of the next node in the ring")

	flag.Parse()

	if *nextPortPtr == 0 || *nextIpPtr == "" {
		fmt.Println("Minimal usage: go run main.go -next_ip <ip> -next_port <port>")
		fmt.Println("With all flags: go run main.go -initiator -node_port <port> -next_ip <ip> -next_port <port>")
		os.Exit(1)
	}

	isInit = *isInitPtr
	nodePort = *nodePortPtr
	nextIp := *nextIpPtr
	nextPort := *nextPortPtr
	nextInRing = network.ConnectionInfo{Address: nextIp, Port: nextPort}
	return isInit, nodePort, &nextInRing
}
