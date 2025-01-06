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

type Client struct {
	NodePort  int
	ConnInfo  ConnectionInfo
	ReceiveCb func(message string)
	SendCb    func(val int)
}

func (client *Client) Listen() {
	ln, err := net.Listen("tcp", "localhost:"+strconv.Itoa(client.NodePort))
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

			fmt.Printf("Received message: %s\n", string(buf))
			if parsed, err := strconv.Atoi(string(buf)); err == nil {
				client.ReceiveCb(strconv.Itoa(parsed))
				client.SendCb(parsed)
				client.send(strconv.Itoa(parsed))
			} else {
				fmt.Println(err)
			}
		}()
	}
}

func (client *Client) send(data string) {
	conn, err := net.Dial("tcp", client.ConnInfo.Address+":"+strconv.Itoa(client.ConnInfo.Port))
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
