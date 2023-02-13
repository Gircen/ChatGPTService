package org.chat.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.chat.gpt.config.OpenAiConfig;
import org.chat.gpt.dto.OpenAiRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class OpenAiService {

    private static RestTemplate restTemplate;
    private static OpenAiConfig openAiConfig;
    private static URI uri ;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAiService(OpenAiConfig openAiConfig) throws URISyntaxException {
        restTemplate = new RestTemplate();
        OpenAiService.openAiConfig = openAiConfig;
        uri = new URI(openAiConfig.getUrl());
    }

    AsyncResult<String> sendMessage(OpenAiRequest openAiRequest) throws JsonProcessingException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("Authorization",String.format("Bearer %s",openAiConfig.getKey()));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        HttpEntity<String> request =
                new HttpEntity<String>(ow.writeValueAsString(openAiRequest), header);
        String result = restTemplate.postForObject(uri,request,String.class);
        return new AsyncResult<>(result);
    }

    @Async
    public void doWork(OpenAiRequest openAiRequest) throws JsonProcessingException, InterruptedException, ExecutionException {
        Future<String> future = sendMessage(openAiRequest);
        while (!future.isDone())
            Thread.sleep(100);
        save(future.get());
    }
    private void save(String result){

    }
}
