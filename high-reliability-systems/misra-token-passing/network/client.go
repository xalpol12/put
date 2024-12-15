package network

import (
	"fmt"
	"net"
	"strconv"
)

type ConnectionInfo struct {
	Address string
	Port    int
}

// Send TODO: Read from env vars or default if port == null
func Send(info ConnectionInfo, data string) {
	conn, err := net.Dial("tcp", info.Address+":"+strconv.Itoa(info.Port))
	if err != nil {
		fmt.Println(err)
		return
	}

	_, err = conn.Write([]byte(data))
	if err != nil {
		fmt.Println(err)
		return
	}

	conn.Close()
}
