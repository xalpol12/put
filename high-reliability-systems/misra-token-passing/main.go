package main

import (
	"misra-token-passing/misra"
	"misra-token-passing/utils"
)

func main() {
	args := utils.ReadArguments()
	node := misra.NewNode(args)
	node.Start()
	select {}
}
