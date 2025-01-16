package network

import (
	"fmt"
	"misra-token-passing/logger"
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
		logger.ErrorErr(err)
		return
	}

	defer func(ln net.Listener) {
		err := ln.Close()
		if err != nil {
			logger.ErrorErr(err)
		}
	}(ln)

	for {
		conn, err := ln.Accept()
		if err != nil {
			logger.ErrorErr(err)
			continue
		}

		go func() {
			defer conn.Close()

			buf := make([]byte, 1024)
			_, err := conn.Read(buf)
			if err != nil {
				logger.ErrorErr(err)
				return
			}

			logger.Info("Received message: %s\n", string(buf))
			if parsed, err := strconv.Atoi(string(buf)); err == nil {
				// TODO send through the channel
			} else {
				logger.ErrorErr(err)
			}
		}()
	}
}

func (client *Client) Send(value int) {
	conn, err := net.Dial("tcp", client.ConnInfo.Address+":"+strconv.Itoa(client.ConnInfo.Port))
	if err != nil {
		logger.ErrorErr(err)
		return
	}

	_, err = conn.Write([]byte(data))
	if err != nil {
		logger.ErrorErr(err)
		return
	}

	conn.Close()
}
