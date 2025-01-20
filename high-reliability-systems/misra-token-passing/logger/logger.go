package logger

import (
	"fmt"
	"time"
)

func Error(format string, args ...interface{}) {
	log(red, warn, format, args...)
}

func ErrorErr(error error) {
	log(red, err, "%s", error)
}

func Warn(format string, args ...interface{}) {
	log(yellow, warn, format, args...)
}

func Info(format string, args ...interface{}) {
	log(white, info, format, args...)
}

func Success(format string, args ...interface{}) {
	log(green, success, format, args...)
}

type color string

const (
	red    color = "\033[31m"
	yellow color = "\033[33m"
	green  color = "\033[32m"
	white  color = "\033[37m"
)
const (
	colorReset = "\033[0m"
)

type label string

const (
	err     label = "ERROR"
	warn    label = "WARN"
	info    label = "INFO"
	success label = "SUCCESS"
)

func log(color color, label label, format string, args ...interface{}) {
	timestamp := time.Now().Format(`2006-01-02 15:04:05`)
	message := fmt.Sprintf(format, args...)
	fmt.Printf("%s%s [%s] %s%s\n", color, timestamp, label, message, colorReset)
}
