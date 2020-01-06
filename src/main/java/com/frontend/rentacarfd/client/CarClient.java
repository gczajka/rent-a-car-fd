package com.frontend.rentacarfd.client;

import com.frontend.rentacarfd.domain.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.CARS_BACKEND;
import static java.util.Optional.ofNullable;

@Component
public class CarClient {
    @Autowired
    private RestTemplate restTemplate;

    public List<CarDto> getCars() {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(CARS_BACKEND).build().encode().toUri();
            CarDto[] response = restTemplate.getForObject(url, CarDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new CarDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public void saveCar(CarDto carDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(CARS_BACKEND).build().encode().toUri();
        restTemplate.postForObject(url, carDto, CarDto.class);
    }

    public void updateCar(CarDto carDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(CARS_BACKEND).build().encode().toUri();
        restTemplate.put(url, carDto);
    }

    public void deleteCar(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(CARS_BACKEND + "/" + id).build().encode().toUri();
        restTemplate.delete(url);
    }
}
