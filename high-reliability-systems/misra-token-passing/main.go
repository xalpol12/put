package main

import (
	"misra-token-passing/misra"
	"misra-token-passing/network"
	"misra-token-passing/utils"
)

func main() {
	isInit, nodePort, nextInRing := utils.ReadArguments()
	if isInit {

	}
	client := network.Client{NodePort: nodePort, ConnInfo: *nextInRing, ReceiveCb: misra.ReceiveCb, SendCb: misra.SendCb}
	client.Listen()
}

func init() {

}
