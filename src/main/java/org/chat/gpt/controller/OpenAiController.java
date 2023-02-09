package org.chat.gpt.controller;

import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Data
public class OpenAiController {
    private static RestTemplate restTemplate;

    @Async
    public void sendMessage() {

    }
}
