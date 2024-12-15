package utils

import (
	"fmt"
	"os"
	"strconv"
)

func GetEnvOrDefault(envKey string, defaultValue string) string {
	if value := os.Getenv(envKey); value != "" {
		return value
	}
	return defaultValue
}

func GetIntEnvOrDefault(envKey string, defaultValue int) int {
	if value := os.Getenv(envKey); value != "" {
		if parsed, err := strconv.Atoi(value); err == nil {
			return parsed
		}
		fmt.Printf("Invalid value for %s: %s (using default %d)\n", envKey, value, defaultValue)
	}
	return defaultValue
}
