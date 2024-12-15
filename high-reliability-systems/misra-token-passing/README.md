### Plan
Input: `[listen_port, next_node_ip, next_node_port]`

While running:
```go
listen
initialize-exchange
config
reconfigure [`listen_port`, `next_node_ip`, `next_node_port`]
```

    0. Listen and pass mode
    1. Init mode

### Misra mutual exclusion by token passing implementation

Compile (run from project's main directory):
```go
go install .
```

Run:
```go
./mistra-token-passing
```