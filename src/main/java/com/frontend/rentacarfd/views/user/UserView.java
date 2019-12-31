package com.frontend.rentacarfd.views.user;

import com.frontend.rentacarfd.client.CarClient;
import com.frontend.rentacarfd.client.UserClient;
import com.frontend.rentacarfd.domain.CarDto;
import com.frontend.rentacarfd.domain.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserView extends VerticalLayout {
    private Grid<UserDto> grid = new Grid<>(UserDto.class);
    private UserClient userClient;

    public UserView(@Autowired UserClient userClient) {
        this.userClient = userClient;

        Button button1 = new Button("Add new user");
        grid.setColumns("id", "name", "surname", "email", "phoneNumber", "password");
        List<UserDto> users = userClient.getUsers();
        grid.setItems(users);

        add(button1, grid);
    }
}
