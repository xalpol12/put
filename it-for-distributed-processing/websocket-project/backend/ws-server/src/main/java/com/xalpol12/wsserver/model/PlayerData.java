package com.xalpol12.wsserver.model;

import lombok.Data;

@Data
public class PlayerData {
    private Integer score = 0;

    public void incrementScore() {
        score++;
    }
}
