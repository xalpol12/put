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

func Send(data string, info ConnectionInfo) {
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

func Listen(callback func(message string), nodePort int) {
	ln, err := net.Listen("tcp", "localhost:"+strconv.Itoa(nodePort))
	if err != nil {
		fmt.Println(ln)
		return
	}

	defer func(ln net.Listener) {
		err := ln.Close()
		if err != nil {
			fmt.Println(err)
		}
	}(ln)

	for {
		conn, err := ln.Accept()
		if err != nil {
			fmt.Println(err)
			continue
		}

		go handleConnection(callback, conn)
	}
}

func handleConnection(callback func(message string), conn net.Conn) {
	defer conn.Close()

	buf := make([]byte, 1024)
	_, err := conn.Read(buf)
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Printf("Received message: %s\n", string(buf))
	if parsed, err := strconv.Atoi(string(buf)); err == nil {
		callback(strconv.Itoa(parsed))
	} else {
		fmt.Println(err)
	}
}
