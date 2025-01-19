package misra

import (
	"math/rand"
	"misra-token-passing/logger"
	"misra-token-passing/network"
	"misra-token-passing/utils"
	"os"
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
	client    network.Client
	m         int
	ping      int
	pong      int
	state     NodeState
	tokenChan chan int
	pingLoss  float64
	pongLoss  float64
}

var (
	rng = rand.New(rand.NewSource(time.Now().UnixNano()))
)

func NewNode(args *utils.InitArgs) *Node {

	node := &Node{
		client: network.Client{
			NodePort: args.NodePort,
			ConnInfo: args.NextInRing,
		},
		m:         0,
		ping:      0,
		pong:      0,
		state:     NO_TOKEN,
		tokenChan: make(chan int, 100),
		pingLoss:  args.PingLoss,
		pongLoss:  args.PongLoss,
	}

	rand.Seed(time.Now().UnixNano())
	if args.PingLoss != 0 || args.PongLoss != 0 {
		logger.Success("Node initialized with non-zero token loss chance, ping loss chance: %d%, pong loss chance %d%",
			args.PingLoss*100, args.PongLoss*100)
	}

	node.client.ReceiveCb = func(token int) {
		node.tokenChan <- token
	}

	if args.IsInit {
		logger.Info("Node is initiator, sending initial tokens...")
		node.send(PING)
		node.send(PONG)
		node.state = BOTH_TOKENS
	}

	return node
}

func (node *Node) Start() {
	go node.listen()
	go node.handleState()
	go node.processTokens()
}

func (node *Node) listen() {
	node.client.Listen()
}

func (node *Node) handleState() {
	for {
		switch node.state {
		case NO_TOKEN:
			continue
		case PING_TOKEN:
			logger.Success("Ping token acquired. Entering critical section...")
			time.Sleep(1 * time.Second)
			logger.Success("Exiting critical section...")
			node.send(PING)
		case PONG_TOKEN:
			node.send(PONG)
		case BOTH_TOKENS:
			node.incarnate(node.ping)
			node.send(PING)
			node.send(PONG)
		}
	}
}

func (node *Node) processTokens() {
	for token := range node.tokenChan {
		node.processToken(token)
		time.Sleep(50 * time.Millisecond)
	}
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
		case PING_TOKEN:
			node.state = BOTH_TOKENS
		case PONG_TOKEN, BOTH_TOKENS:
			logger.Error("Huh, shouldn't have happened...")
		}
	}
}

func (node *Node) send(token TokenType) {

	// Random loss possibility
	if node.pingLoss != 0 || node.pongLoss != 0 {
		number := rng.Float64()
		switch token {
		case PING:
			if number <= node.pingLoss {
				logger.Warn("Ping: %d lost, has not been sent!", node.ping)
				return
			}
		case PONG:
			if number <= node.pongLoss {
				logger.Warn("Pong: %d lost, has not been sent!", node.pong)
				return
			}
		}
	}

	switch token {
	case PING:
		err := node.client.Send(node.ping)
		if err != nil {
			logger.Error("Error sending PING: %v", err)
			node.disconnect()
		}
		node.m = node.ping
		logger.Success("Ping: %d sent successfully", node.ping)
		if node.state == PING_TOKEN {
			node.state = NO_TOKEN
		}
		if node.state == BOTH_TOKENS {
			node.state = PONG_TOKEN
		}
	case PONG:
		err := node.client.Send(node.pong)
		if err != nil {
			logger.Error("Error sending PONG: %v", err)
			node.disconnect()
		}
		node.m = node.pong
		logger.Success("Pong: %d sent successfully", node.pong)
		if node.state == PONG_TOKEN {
			node.state = NO_TOKEN
		}
		if node.state == BOTH_TOKENS {
			node.state = PING_TOKEN
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
	logger.Info("Incarnated ping: %d, pong: %d", node.ping, node.pong)
}

func (node *Node) disconnect() {
	err := node.client.Close()
	if err != nil {
		logger.Error("Error closing client: %v", err)
	}
	os.Exit(1)
}
