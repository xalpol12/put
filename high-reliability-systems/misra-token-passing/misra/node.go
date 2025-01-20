package misra

import (
	"misra-token-passing/logger"
	"misra-token-passing/model"
	"misra-token-passing/network"
	"misra-token-passing/utils"
	"os"
	"time"
)

type Node struct {
	client    network.Client
	m         int
	ping      int
	pong      int
	state     model.NodeState
	tokenChan chan int
}

func NewNode(args *utils.InitArgs) *Node {

	node := &Node{
		client: network.Client{
			NodePort: args.NodePort,
			ConnInfo: args.NextInRing,
			PingLoss: args.PingLoss,
			PongLoss: args.PongLoss,
		},
		m:         0,
		ping:      0,
		pong:      0,
		state:     model.NO_TOKEN,
		tokenChan: make(chan int, 100),
	}

	if args.PingLoss != 0 || args.PongLoss != 0 {
		logger.Success("Node initialized with non-zero token loss chance, ping loss chance: %.2f percent, pong loss chance: %.2f percent",
			args.PingLoss*100, args.PongLoss*100)
	}

	node.client.ReceiveCb = func(token int) {
		node.tokenChan <- token
	}

	if args.IsInit {
		logger.Info("Node is initiator, sending initial tokens...")
		node.send(model.PING)
		node.send(model.PONG)
		node.state = model.BOTH_TOKENS
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
		case model.NO_TOKEN:
			continue
		case model.PING_TOKEN:
			logger.Success("Ping token acquired. Entering critical section...")
			time.Sleep(1 * time.Second)
			logger.Success("Exiting critical section...")
			node.send(model.PING)
		case model.PONG_TOKEN:
			node.send(model.PONG)
		case model.BOTH_TOKENS:
			node.incarnate(node.ping)
			node.send(model.PING)
			node.send(model.PONG)
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
		case model.NO_TOKEN:
			node.state = model.PING_TOKEN
		case model.PONG_TOKEN:
			node.state = model.BOTH_TOKENS
		case model.PING_TOKEN, model.BOTH_TOKENS:
			logger.Error("Huh, shouldn't have happened...")
		}
	}
	if token < 0 {
		node.incarnate(token)
		switch node.state {
		case model.NO_TOKEN:
			node.state = model.PONG_TOKEN
		case model.PING_TOKEN:
			node.state = model.BOTH_TOKENS
		case model.PONG_TOKEN, model.BOTH_TOKENS:
			logger.Error("Huh, shouldn't have happened...")
		}
	}
}

func (node *Node) send(token model.TokenType) {

	var err error
	switch token {
	case model.PING:
		err = node.client.Send(node.ping, token)
		if err != nil {
			logger.Error("Error sending PING: %v", err)
			node.disconnect()
		}
		node.m = node.ping
		logger.Success("Ping: %d sent successfully", node.ping)
		if node.state == model.PING_TOKEN {
			node.state = model.NO_TOKEN
		}
		if node.state == model.BOTH_TOKENS {
			node.state = model.PONG_TOKEN
		}
	case model.PONG:
		err = node.client.Send(node.pong, token)
		if err != nil {
			logger.Error("Error sending PONG: %v", err)
			node.disconnect()
		}
		node.m = node.pong
		logger.Success("Pong: %d sent successfully", node.pong)
		if node.state == model.PONG_TOKEN {
			node.state = model.NO_TOKEN
		}
		if node.state == model.BOTH_TOKENS {
			node.state = model.PING_TOKEN
		}
	}
}

func (node *Node) regenerate(value int) {
	node.ping = utils.Abs(value)
	node.pong = -node.ping
	node.state = model.BOTH_TOKENS
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
