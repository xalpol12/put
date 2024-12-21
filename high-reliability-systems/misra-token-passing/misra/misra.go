package misra

import (
	"fmt"
	"misra-token-passing/network"
	"strconv"
	"time"
)

var m = 0
var ping = 1
var pong = -1

func Receive(message string) {
	value, err := strconv.Atoi(message)
	if err != nil {
		fmt.Println(err)
	}

	if abs(value) < abs(m) {
		return
	}
	if abs(m) == abs(value) {
		regenerate(value)
	}
	fmt.Println("Computing 1 sec...")
	time.Sleep(1 * time.Second)
	if m+value == 0 {
		incarnate(value)
	}
}

func regenerate(value int) {
	ping = abs(value)
	pong = -ping
}

func incarnate(value int) {
	ping = abs(value) + 1
	pong = -ping
}

func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}

func Send(value int, info network.ConnectionInfo) {
	m = value
	network.Send(strconv.Itoa(value), info)
}
