package org.chat.gpt.controller;

import org.chat.gpt.config.ApplicationConfig;
import org.chat.gpt.controller.template.ControllerImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.ServiceUnavailableException;
import java.net.URI;
import java.util.Optional;


@EnableAutoConfiguration
@RestController
@RequestMapping("consul/")
public class ConsulClientControllerImpl extends ControllerImpl {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final ApplicationConfig applicationConfig;


    public ConsulClientControllerImpl(DiscoveryClient discoveryClient, ApplicationConfig applicationConfig) {
        this.discoveryClient = discoveryClient;
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
    public ResponseEntity<String> getMap() throws RestClientException {
        return super.getMap();
    }

    @GetMapping("/health-check/")
    public ResponseEntity<String> checkHealth () throws Exception {
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
