package com.frontend.rentacarfd.client;

import com.frontend.rentacarfd.domain.RentalDto;
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
public class RentalClient {
    @Autowired
    private RestTemplate restTemplate;
    private String endpoint = "http://localhost:8080/v1/rentals";

    public List<RentalDto> getRentals() {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(endpoint).build().encode().toUri();
            RentalDto[] response = restTemplate.getForObject(url, RentalDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new RentalDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<RentalDto> getRentalsByUsersId(Long usersId) {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(endpoint + "/byUserId/" + usersId).build().encode().toUri();
            RentalDto[] response = restTemplate.getForObject(url, RentalDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new RentalDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }
}

