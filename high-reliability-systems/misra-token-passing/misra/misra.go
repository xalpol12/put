package misra

import (
	"fmt"
	"misra-token-passing/model"
	"misra-token-passing/utils"
	"strconv"
	"time"
)

func (node *model.Node) ReceiveToken(token int) {
	value, err := strconv.Atoi(message)
	if err != nil {
		fmt.Println(err)
	}

	if utils.Abs(value) < utils.Abs(m) {
		fmt.Printf("Received: %d, less than expected: %d\n", value, m)
		return
	}
	if utils.Abs(m) == utils.Abs(value) {
		regenerate(value)
	}
	if m+value == 0 {
		fmt.Println("Computing 1 sec...")
		time.Sleep(1 * time.Second)
		incarnate(value)
	}
}

func SendCb(value int) {
	m = value
}
