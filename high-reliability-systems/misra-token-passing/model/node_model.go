package model

import (
	"misra-token-passing/logger"
	"misra-token-passing/network"
	"misra-token-passing/utils"
	"time"
)

type NodeState int

const (
	NO_TOKEN NodeState = iota
	PING_TOKEN
	PONG_TOKEN
	BOTH_TOKENS
)

type TokenType int

const (
	PING TokenType = iota
	PONG
)

type Node struct {
	client network.Client
	m      int
	ping   int
	pong   int
	state  NodeState
}

func (node *Node) send(token TokenType) {
	switch token {
	case PING:
		// TODO: send
		node.client.Send(node.ping)
		node.m = node.ping
		logger.Info("Ping: %d sent successfully", node.ping)
		if node.state == PING_TOKEN {
			node.state = NO_TOKEN
		}
		if node.state == BOTH_TOKENS {
			node.state = PONG_TOKEN
		}
	case PONG:
		node.client.Send(node.pong)
		node.m = node.pong
		logger.Info("Pong: %d sent successfully", node.pong)
		if node.state == PONG_TOKEN {
			node.state = NO_TOKEN
		}
		if node.state == BOTH_TOKENS {
			node.state = PING_TOKEN
		}
	}
}

func (node *Node) listen() {

}

func (node *Node) handleState() {
	go func() {
		for {
			switch node.state {
			case NO_TOKEN:
				// TODO reading received messages
			case PING_TOKEN:
				logger.Success("Ping token acquired. Entering critical section...")
				time.Sleep(1 * time.Second)
				logger.Success("Exiting critical section...")
				node.send(PING)
				// TODO reading received messages
			case PONG_TOKEN:
				logger.Info("Pong")
				node.send(PONG)
			case BOTH_TOKENS:
				node.incarnate(node.ping)
				node.send(PING)
				node.send(PONG)
				logger.Info("BOTH")
			}
		}
	}()
}

func (node *Node) processToken(token int) {
	// token < m: ignore
	// token == m: ping or pong lost
	// token > 0 save as ping
	// token < 0 save as pong
	if utils.Abs(token) < node.m {
		logger.Info("Received old token of value: %d", token)
	}
	if token == node.m {
		if node.m > 0 {
			logger.Warn("Pong lost, regenerating...")
			node.regenerate(token)
			return
		}
		if node.m < 0 {
			logger.Warn("Ping lost, regenerating...")
			node.regenerate(token)
			return
		}
	}
	if token > 0 {
		node.incarnate(token)
		switch node.state {
		case NO_TOKEN:
			node.state = PING_TOKEN
		case PONG_TOKEN:
			node.state = BOTH_TOKENS
		case PING_TOKEN, BOTH_TOKENS:
			logger.Error("Huh, shouldn't have happened...")
		}
	}
	if token < 0 {
		node.incarnate(token)
		switch node.state {
		case NO_TOKEN:
			node.state = PONG_TOKEN
		case PONG_TOKEN:
			node.state = BOTH_TOKENS
		case PING_TOKEN, BOTH_TOKENS:
			logger.Error("Huh, shouldn't have happened...")
		}
	}
}

func (node *Node) regenerate(value int) {
	node.ping = utils.Abs(value)
	node.pong = -node.ping
	node.state = BOTH_TOKENS
	logger.Warn("Regenerated ping: %d, pong: %d", node.ping, node.pong)
}

func (node *Node) incarnate(value int) {
	node.ping = utils.Abs(value) + 1
	node.pong = -node.ping
	logger.Warn("Incarnated ping: %d, pong: %d", node.ping, node.pong)
}
