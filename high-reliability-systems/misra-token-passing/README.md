### Misra mutual exclusion by token passing implementation
Implementation of Misra mutual exclusion token passing algorithm in ring topology with token loss detection.
Algorithm is implemented for unidirectional ring topology, nodes communicate with each other using TCP connection.
Each node sends a token formatted as such:
```markdown
<int>\n
```
Node listens on a given port for messages, parses message looking for the \n endline character and proceeds with the algorithm.
Critical section is entered when node holds the most recent (not yet seen) token.
To prevent standard int overflow and extend the algorithm's lifespan, int64 tokens can be used.

Compile (run from project's main directory):
```go
go install .
```

Run:
```go
./mistra-token-passing -next_ip <ip> -next_port <port>
```

All args:

| Argument Name | Description                                        | Possible Values          | Default Value    |
|---------------|----------------------------------------------------|--------------------------|------------------|
| `-initiator`  | Indicates if the node is the initiator of the ring | `true` / `false`         | `false`          |
| `-node_port`  | Port number of the current node in the ring        | Any valid port number    | `8089`           |
| `-next_ip`    | IP address of the next node in the ring            | Any valid IP address     | provided by user |
| `-next_port`  | Port number of the next node in the ring           | Any valid port number    | provided by user |
| `-ping_loss`  | Probability of ping loss (0 to 1)                  | Decimal values [0.0–1.0] | `0`              |
| `-pong_loss`  | Probability of pong loss (0 to 1)                  | Decimal values [0.0–1.0] | `0`              |

If `-next_ip` or `-next_port` is not provided, the program will display a usage message and exit.