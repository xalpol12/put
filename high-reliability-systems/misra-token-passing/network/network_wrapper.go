package network

import (
	"bufio"
	"fmt"
	"io"
	"math/rand"
	"misra-token-passing/logger"
	"misra-token-passing/model"
	"net"
	"strconv"
	"strings"
	"sync"
	"time"
)

type ConnectionInfo struct {
	Address string
	Port    int
}

type Client struct {
	NodePort    int
	ConnInfo    *ConnectionInfo
	ReceiveCb   func(val int)
	SendCb      func(val int)
	PingLoss    float64
	PongLoss    float64
	conn        net.Conn
	mutex       sync.Mutex
	isConnected bool
}

var (
	rng = rand.New(rand.NewSource(time.Now().UnixNano()))
)

func (client *Client) Send(value int, token model.TokenType) error {
	client.mutex.Lock()
	defer client.mutex.Unlock()

	if !client.isConnected {
		err := client.connect()
		if err != nil {
			return fmt.Errorf("failed to establish connection: %w", err)
		}
	}

	if !client.tokenDropped(token) {
		data := []byte(strconv.Itoa(value) + "\n")
		_, err := client.conn.Write(data)
		if err != nil {
			client.isConnected = false
			client.conn.Close()
			return fmt.Errorf("send failed: %w", err)
		}

		if client.SendCb != nil {
			client.SendCb(value)
		}
	}

	return nil
}

func (client *Client) connect() error {
	if client.isConnected {
		return nil
	}

	addr := fmt.Sprintf("%s:%d", client.ConnInfo.Address, client.ConnInfo.Port)
	conn, err := net.Dial("tcp", addr)
	if err != nil {
		return fmt.Errorf("connect fail: %w", err)
	}

	client.conn = conn
	client.isConnected = true
	return nil
}

func (client *Client) Listen() {
	ln, err := net.Listen("tcp", "localhost:"+strconv.Itoa(client.NodePort))
	if err != nil {
		logger.ErrorErr(err)
		return
	}
	defer ln.Close()

	logger.Info("Listening on port %d", client.NodePort)

	for {
		conn, err := ln.Accept()
		if err != nil {
			logger.ErrorErr(err)
			continue
		}

		go client.handleConnection(conn)
	}
}

func (client *Client) handleConnection(conn net.Conn) {
	defer func() {
		if err := conn.Close(); err != nil {
			logger.Error("Error closing connection: %v", err)
		}
	}()

	reader := bufio.NewReader(conn)
	for {
		message, err := reader.ReadString('\n')
		if err != nil {
			if err == io.EOF {
				logger.Info("Client disconnected")
			} else {
				logger.Error("Error reading message: %v", err)
			}
			return
		}
		message = strings.TrimSpace(message)
		logger.Info("Received message: %s", message)

		parsed, parsedErr := strconv.Atoi(message)
		if parsedErr != nil {
			logger.Error("Failed to parse message: %s", message)
			continue
		}

		if client.ReceiveCb != nil {
			client.ReceiveCb(parsed)
		}
	}
}

func (client *Client) Close() error {
	client.mutex.Lock()
	defer client.mutex.Unlock()

	if !client.isConnected {
		return nil
	}

	err := client.conn.Close()
	client.isConnected = false
	return err
}

func (client *Client) tokenDropped(token model.TokenType) bool {

	// Random loss possibility
	var tokenLost bool
	if client.PingLoss != 0 || client.PongLoss != 0 {
		number := rng.Float64()
		switch token {
		case model.PING:
			if number <= client.PingLoss {
				logger.Warn("Ping lost, has not been sent!")
				tokenLost = true
			}
		case model.PONG:
			if number <= client.PongLoss {
				logger.Warn("Pong lost, has not been sent!")
				tokenLost = true
			}
		}
	}

	return tokenLost
}
