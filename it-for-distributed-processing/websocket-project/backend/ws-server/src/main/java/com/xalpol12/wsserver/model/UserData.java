package com.xalpol12.wsserver.model;

import lombok.Data;

@Data
public class UserData {
    private Integer score = 0;

    public void incrementScore() {
        score++;
    }
}
