package com.frontend.rentacarfd.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.VERIFICATION_BACKEND;

@Component
public class EmailValidatorClient {
    @Autowired
    private RestTemplate restTemplate;

    public boolean isEmailValid(String email) {
        URI url = UriComponentsBuilder.fromHttpUrl(VERIFICATION_BACKEND + "/" + email).build().encode().toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }
}
