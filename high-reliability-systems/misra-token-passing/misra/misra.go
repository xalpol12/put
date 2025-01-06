package misra

import (
	"fmt"
	"strconv"
	"time"
)

var m = 0
var ping = 1
var pong = -1

func ReceiveCb(message string) {
	value, err := strconv.Atoi(message)
	if err != nil {
		fmt.Println(err)
	}

	if abs(value) < abs(m) {
		fmt.Printf("Received: %d, less than expected: %d\n", value, m)
		return
	}
	if abs(m) == abs(value) {
		regenerate(value)
	}
	if m+value == 0 {
		fmt.Println("Computing 1 sec...")
		time.Sleep(1 * time.Second)
		incarnate(value)
	}
}

func regenerate(value int) {
	ping = abs(value)
	pong = -ping
	fmt.Printf("Regenerated ping: %d, pong: %d\n", ping)
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

func SendCb(value int) {
	m = value
}
