package com.xalpol12.recipes.controller.iface;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    public static class TestPath {
        public static final String ROOT = "/api/test";
        private TestPath() {};
    }

    @GetMapping(TestPath.ROOT)
    public ResponseEntity<String> returnString() {
        log.info("GET /test called");
        return ResponseEntity.ok("Test");
    }

}
