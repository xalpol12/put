package com.xalpol12.wsserver.controller;

import com.xalpol12.wsserver.model.dto.SessionDTO;
import com.xalpol12.wsserver.model.dto.SessionResponse;
import com.xalpol12.wsserver.model.dto.UserDTO;
import com.xalpol12.wsserver.service.GameSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GameSessionController {

    private final GameSessionService gameSessionService;

    @PostMapping("/sessions")
    public ResponseEntity<SessionResponse> addNewSession(@RequestBody SessionDTO sessionDTO) {
        SessionResponse response = gameSessionService.addNewSession(sessionDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sessions/{sid}")
    public ResponseEntity<SessionResponse> addUserToSession(@PathVariable("sid") String sessionId, @RequestBody UserDTO userDTO) {
        SessionResponse response = gameSessionService.addUserToSession(userDTO.userId(), sessionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
