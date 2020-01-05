package com.frontend.rentacarfd.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class EmailValidatorClient {
    @Autowired
    private RestTemplate restTemplate;
    private String endpoint = "http://localhost:8080/v1/verification";

    public boolean isEmailValid(String email) {
        URI url = UriComponentsBuilder.fromHttpUrl(endpoint + "/" + email).build().encode().toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }
}
