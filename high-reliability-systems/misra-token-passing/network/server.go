package network

import (
	"fmt"
	"misra-token-passing/misra"
	"net"
	"strconv"
)

func OpenServer(port int) {
	ln, err := net.Listen("tcp", "localhost:"+strconv.Itoa(port))
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

		go handleConnection(conn)
	}
}

func handleConnection(conn net.Conn) {
	defer conn.Close()

	buf := make([]byte, 1024)
	_, err := conn.Read(buf)
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Printf("Received message: %s\n", string(buf))
	if parsed, err := strconv.Atoi(string(buf)); err == nil {
		misra.Receive(parsed)
	} else {
		fmt.Println(err)
	}
}
