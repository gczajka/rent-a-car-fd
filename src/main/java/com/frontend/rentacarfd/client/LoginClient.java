package com.frontend.rentacarfd.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class LoginClient {
    @Autowired
    private RestTemplate restTemplate;
    private String endpoint = "http://localhost:8080/v1/logins";

    public boolean isLoginRegistered(String email, String password) {
        URI url = UriComponentsBuilder.fromHttpUrl(endpoint + "/alreadyRegistered/" + email + "&" + password).build().encode().toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }
}
