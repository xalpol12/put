package main

import (
	"misra-token-passing/misra"
	"misra-token-passing/utils"
)

func main() {
	isInit, nodePort, nextInRing := utils.ReadArguments()
	node := misra.NewNode(isInit, nodePort, nextInRing)
	node.Start()
	select {}
}
