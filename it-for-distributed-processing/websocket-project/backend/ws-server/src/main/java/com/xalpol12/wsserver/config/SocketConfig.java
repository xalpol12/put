package com.xalpol12.wsserver.config;

import com.xalpol12.wsserver.handler.TextSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfig {

    @Bean
    TextSocketHandler textSocketHandler()  {
        return new TextSocketHandler();
    }
}
