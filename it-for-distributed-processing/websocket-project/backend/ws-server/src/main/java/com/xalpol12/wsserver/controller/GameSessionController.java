package com.xalpol12.wsserver.controller;

import com.xalpol12.wsserver.model.dto.SessionDTO;
import com.xalpol12.wsserver.model.dto.UserDTO;
import com.xalpol12.wsserver.service.GameSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class GameSessionController {

    private final GameSessionService gameSessionService;

    @PostMapping
    public ResponseEntity<String> addNewSession(@RequestBody SessionDTO sessionDTO) {
        String sessionId = gameSessionService.addNewSession(sessionDTO);
        // TODO: return sessionId and clientId
        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }

    @PostMapping("/{sid}")
    public ResponseEntity<Void> addUserToSession(@PathVariable("sid") String sessionId, @RequestBody UserDTO userDTO) {
        gameSessionService.addUserToSession(userDTO.userId(), sessionId);
        // TODO: return sessionId and clientId
        return ResponseEntity.ok().build();
    }
}
