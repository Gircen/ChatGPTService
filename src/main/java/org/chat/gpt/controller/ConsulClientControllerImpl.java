package org.chat.gpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.chat.gpt.config.ApplicationConfig;
import org.chat.gpt.controller.template.ControllerImpl;
import org.chat.gpt.database.dao.repository.MessageRepositoryOutbox;
import org.chat.gpt.dto.OpenAiRequest;
import org.chat.gpt.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.ServiceUnavailableException;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


@EnableAutoConfiguration
@RestController
@RequestMapping("consul/")
public class ConsulClientControllerImpl extends ControllerImpl {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final ApplicationConfig applicationConfig;
    private final OpenAiService openAiService;

    public ConsulClientControllerImpl(DiscoveryClient discoveryClient, ApplicationConfig applicationConfig, MessageRepositoryOutbox repository, OpenAiService openAiService) {
        this.discoveryClient = discoveryClient;
        this.openAiService = openAiService;
        this.restTemplate = new RestTemplate();
        this.applicationConfig = applicationConfig;
    }

    public Optional<URI> serviceUrl() {
        return discoveryClient.getInstances(applicationConfig.getServiceName())
                .stream()
                .findFirst()
                .map(ServiceInstance::getUri);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<String> getMap() throws ExecutionException, JsonProcessingException, InterruptedException {
        return super.getMap();
    }

    @GetMapping("/health-check/")
    public ResponseEntity<String> checkHealth () {

        String message = "I a live!";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/discoveryClient/")
    public String discoveryPing() throws RestClientException, ServiceUnavailableException {
        URI service = serviceUrl()
                .map(s -> s.resolve("/consul/ping"))
                .orElseThrow(ServiceUnavailableException::new);
        return restTemplate.getForEntity(service, String.class).getBody();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
