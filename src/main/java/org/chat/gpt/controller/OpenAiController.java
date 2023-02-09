package org.chat.gpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
import lombok.Data;
import netscape.javascript.JSObject;
import org.chat.gpt.config.OpenAiConfig;
import org.chat.gpt.dto.OpenAiRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class OpenAiController {

    private static RestTemplate restTemplate;
    private static OpenAiConfig openAiConfig;
    private static URI uri ;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAiController(OpenAiConfig openAiConfig) throws URISyntaxException {
        restTemplate = new RestTemplate();
        OpenAiController.openAiConfig = openAiConfig;
        uri = new URI(openAiConfig.getUrl());
    }

    @Async
    public AsyncResult<String> sendMessage() throws JsonProcessingException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("Authorization",String.format("Bearer %s",openAiConfig.getKey()));
        OpenAiRequest openAiRequest = new OpenAiRequest();
        openAiRequest.setModel("text-davinci-003");
        openAiRequest.setPrompt("Convert this text to a programmatic command:\n\nExample: Ask Constance if we need some bread\nOutput: send-msg `find constance` Do we need some bread?\n\nReach out to the ski store and figure out if I can get my skis fixed before I leave on Thursday");
        openAiRequest.setTemperature(0);
        openAiRequest.setMaxTokens(100);
        openAiRequest.setTopP(1.0);
        openAiRequest.setFrequencyPenalty(0.2);
        openAiRequest.setPresencePenalty(0.0);
        Set<String> stop = new LinkedHashSet<>();
        stop.add("###");
        openAiRequest.setStop(stop);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        HttpEntity<String> request =
                new HttpEntity<String>(ow.writeValueAsString(openAiRequest), header);
        String result = restTemplate.postForObject(uri,request,String.class);
        return new AsyncResult<>(result);
    }
}
