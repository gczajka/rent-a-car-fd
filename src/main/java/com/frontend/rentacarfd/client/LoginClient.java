package com.frontend.rentacarfd.client;

import com.frontend.rentacarfd.domain.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.LOGINS_BACKEND;

@Component
public class LoginClient {
    @Autowired
    private RestTemplate restTemplate;

    public boolean isLoginRegistered(LoginDto loginDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(LOGINS_BACKEND + "/alreadyRegistered/" + loginDto.getEmail() + "&" + loginDto.getPassword()).build().encode().toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }
}
