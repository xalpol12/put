package utils

import (
	"flag"
	"fmt"
	"misra-token-passing/network"
	"os"
)

type InitArgs struct {
	IsInit     bool
	NextInRing *network.ConnectionInfo
	NodePort   int
	PingLoss   float64
	PongLoss   float64
	SleepTime  int
}

func ReadArguments() *InitArgs {
	isInitPtr := flag.Bool("initiator", false, "Is initiator?")
	nodePortPtr := flag.Int("node_port", 8089, "Port number of the node in the ring")
	nextIpPtr := flag.String("next_ip", "", "IP address of the next node in the ring")
	nextPortPtr := flag.Int("next_port", 0, "Port number of the next node in the ring")
	pingLossPtr := flag.Float64("ping_loss", 0, "Chance of the ping loss (values from 0 to 1)")
	pongLossPtr := flag.Float64("pong_loss", 0, "Chance of the pong loss (values from 0 to 1)")
	sleepTimePtr := flag.Int("sleep_time", 1, "Sleep duration when in critical section")

	flag.Parse()

	if *nextPortPtr == 0 || *nextIpPtr == "" {
		fmt.Println("Minimal usage: go run main.go -next_ip <ip> -next_port <port>")
		fmt.Println("With all flags: go run main.go -initiator -node_port <port> -next_ip <ip> -next_port <port> -ping_loss <0..1> -pong_loss <0..1>")
		os.Exit(1)
	}

	return &InitArgs{
		IsInit:     *isInitPtr,
		NodePort:   *nodePortPtr,
		NextInRing: &network.ConnectionInfo{Address: *nextIpPtr, Port: *nextPortPtr},
		PingLoss:   *pingLossPtr,
		PongLoss:   *pongLossPtr,
		SleepTime:  *sleepTimePtr,
	}
}
