package com.frontend.rentacarfd.client;

import com.frontend.rentacarfd.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class UserClient {
    @Autowired
    private RestTemplate restTemplate;
    private String endpoint = "http://localhost:8080/v1/users";

    public List<UserDto> getUsers() {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(endpoint).build().encode().toUri();
            UserDto[] response = restTemplate.getForObject(url, UserDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new UserDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public boolean isUserRegistered(String email) {
        URI url = UriComponentsBuilder.fromHttpUrl(endpoint + "/alreadyRegistered/" + email).build().encode().toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }

    public void registerUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(endpoint).build().encode().toUri();
        restTemplate.postForObject(url, userDto, UserDto.class);
    }
}
