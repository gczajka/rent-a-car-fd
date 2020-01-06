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

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.USERS_BACKEND;
import static java.util.Optional.ofNullable;

@Component
public class UserClient {
    @Autowired
    private RestTemplate restTemplate;

    public List<UserDto> getUsers() {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(USERS_BACKEND).build().encode().toUri();
            UserDto[] response = restTemplate.getForObject(url, UserDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new UserDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public UserDto getUserByEmail(String email) {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(USERS_BACKEND + "/byEmail/" + email).build().encode().toUri();
            UserDto response = restTemplate.getForObject(url, UserDto.class);
            return ofNullable(response).orElse(new UserDto());
        } catch (RestClientException e) {
            return new UserDto();
        }
    }

    public boolean isUserRegistered(String email) {
        URI url = UriComponentsBuilder.fromHttpUrl(USERS_BACKEND + "/alreadyRegistered/" + email).build().encode().toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }

    public boolean doesUserHaveNoRents(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(USERS_BACKEND + "/hasNoRents/" + id).build().encode().toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }

    public void registerUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(USERS_BACKEND).build().encode().toUri();
        restTemplate.postForObject(url, userDto, UserDto.class);
    }

    public void updateUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(USERS_BACKEND).build().encode().toUri();
        restTemplate.put(url, userDto);
    }

    public void deleteUser(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(USERS_BACKEND + "/" + id).build().encode().toUri();
        restTemplate.delete(url);
    }
}
