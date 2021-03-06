package com.frontend.rentacarfd.client;

import com.frontend.rentacarfd.domain.RentalDto;
import com.frontend.rentacarfd.domain.RentalVesselDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.RENTALS_BACKEND;
import static java.util.Optional.ofNullable;

@Component
public class RentalClient {
    @Autowired
    private RestTemplate restTemplate;
    private String endpoint = RENTALS_BACKEND;

    public List<RentalDto> getRentals() {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(RENTALS_BACKEND).build().encode().toUri();
            RentalDto[] response = restTemplate.getForObject(url, RentalDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new RentalDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<RentalDto> getRentalsByUsersId(Long usersId) {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(RENTALS_BACKEND + "/byUserId/" + usersId).build().encode().toUri();
            RentalDto[] response = restTemplate.getForObject(url, RentalDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new RentalDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public void createRental(RentalVesselDto rentalVesselDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(RENTALS_BACKEND).build().encode().toUri();
        restTemplate.postForObject(url, rentalVesselDto, RentalDto.class);
    }

    public void modifyRental(Long rentalId) {
        URI url = UriComponentsBuilder.fromHttpUrl(RENTALS_BACKEND).build().encode().toUri();
        restTemplate.put(url, rentalId);
    }

    public void deleteRental(Long rentalId) {
        URI url = UriComponentsBuilder.fromHttpUrl(RENTALS_BACKEND + "/" + rentalId).build().encode().toUri();
        restTemplate.delete(url);
    }
}

