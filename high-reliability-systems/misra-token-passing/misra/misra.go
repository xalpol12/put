package misra

import (
	"misra-token-passing/network"
	"strconv"
)

var sum = 0
var ping = 0
var pong = 0

func Receive(value int) {
	if abs(value) < abs(sum) {
		return
	}
	if sum == value {
		regenerate(value)
	}
	incarnate(value)
}

func regenerate(value int) {
	ping = abs(value)
	pong = -ping
}

func incarnate(value int) {
	ping = abs(value) + 1
	pong = -ping
}

func SendPing(info network.ConnectionInfo) {
	go network.Send(info, strconv.Itoa(ping))
}

func SendPong(info network.ConnectionInfo) {
	go network.Send(info, strconv.Itoa(pong))
}

func send() {

}

func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}
