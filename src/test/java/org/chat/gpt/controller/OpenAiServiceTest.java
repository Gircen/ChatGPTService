package org.chat.gpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.chat.gpt.config.OpenAiConfig;
import org.chat.gpt.service.OpenAiService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;

import java.net.URISyntaxException;

//OpenAiRequest openAiRequest = new OpenAiRequest();
//        openAiRequest.setModel("text-davinci-003");
//        openAiRequest.setPrompt("Convert this text to a programmatic command:\n\nExample: Ask Constance if we need some bread\nOutput: send-msg `find constance` Do we need some bread?\n\nReach out to the ski store and figure out if I can get my skis fixed before I leave on Thursday");
//        openAiRequest.setTemperature(0);
//        openAiRequest.setMaxTokens(100);
//        openAiRequest.setTopP(1.0);
//        openAiRequest.setFrequencyPenalty(0.2);
//        openAiRequest.setPresencePenalty(0.0);
//        Set<String> stop = new LinkedHashSet<>();
//        stop.add("###");
//        openAiRequest.setStop(stop);
class OpenAiServiceTest {

    OpenAiConfig openAiConfig = new OpenAiConfig();
    @Autowired
    OpenAiService openAiService;
    AsyncResult<String>  res;

    @BeforeEach
    void setUp() throws URISyntaxException {
        openAiService = new OpenAiService(openAiConfig);
    }

    @Test
    void test() throws JsonProcessingException {
//        res = openAiController.sendMessage();
    }
    @AfterEach
    void tearDown() throws JsonProcessingException {
        System.out.println(res);

    }
}