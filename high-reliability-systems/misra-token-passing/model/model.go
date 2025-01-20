package model

type TokenType int

const (
	PING TokenType = iota
	PONG
)

type NodeState int

const (
	NO_TOKEN NodeState = iota
	PING_TOKEN
	PONG_TOKEN
	BOTH_TOKENS
)
