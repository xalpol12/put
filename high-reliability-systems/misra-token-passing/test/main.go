package test

import (
	"bufio"
	"flag"
	"fmt"
	"net"
	"os"
	"strconv"
)

var (
	clientSendPort   int
	serverListenPort int
	input            string
)

func main() {
	sendPortPtr := flag.Int("send_port", 8089, "Port number of the node in the ring")
	listenPortPtr := flag.Int("listen_port", 8090, "Server port listen")

	flag.Parse()
	clientSendPort = *sendPortPtr
	serverListenPort = *listenPortPtr

	fmt.Printf("Client sending to port: %d, server listening on port: %d\n", clientSendPort, serverListenPort)
	go listen()

	scanner := bufio.NewScanner(os.Stdin)
	for {
		fmt.Printf("Message to send to port %d: ", clientSendPort)
		scanner.Scan()
		input = scanner.Text()
		send(input)
	}
}

func send(data string) {
	conn, err := net.Dial("tcp", "localhost:"+strconv.Itoa(clientSendPort))
	if err != nil {
		fmt.Println(err)
		return
	}

	_, err = conn.Write([]byte(data))
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Printf("Message %s sent to port %d: ", data, clientSendPort)

	conn.Close()
}

func listen() {
	ln, err := net.Listen("tcp", "localhost:"+strconv.Itoa(serverListenPort))
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

		go func() {
			defer conn.Close()

			buf := make([]byte, 1024)
			_, err := conn.Read(buf)
			if err != nil {
				fmt.Println(err)
				return
			}

			fmt.Printf("Server:%d received message: %s\n", serverListenPort, string(buf))
		}()
	}

}
