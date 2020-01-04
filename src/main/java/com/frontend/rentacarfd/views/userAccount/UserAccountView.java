package com.frontend.rentacarfd.views.userAccount;

import com.frontend.rentacarfd.client.UserClient;
import com.frontend.rentacarfd.domain.CarDto;
import com.frontend.rentacarfd.domain.RentalDto;
import com.frontend.rentacarfd.domain.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccountView extends VerticalLayout {
    private UserClient userClient;
    private TextField name = new TextField("name");
    private TextField surname = new TextField("surname");
    private TextField email = new TextField("email");
    private TextField phoneNumber = new TextField("phoneNumber");
    private TextField password = new TextField("password");
    private Binder<UserDto> binder = new Binder<>();
    private UserDto loggedUserDto = new UserDto();
    private long userId;

    public UserAccountView(@Autowired UserClient userClient) {
        this.userClient = userClient;
        bindFields();
        Button updateUser = new Button("Update");
        binder.writeBeanIfValid(loggedUserDto);
        updateUser.addClickListener(e -> {
            binder.writeBeanIfValid(loggedUserDto);
            updateUser(loggedUserDto);
        });

        VerticalLayout layout = new VerticalLayout();
        layout.add(name, surname, email, phoneNumber, password, updateUser);
        add(layout);
    }

    public void refreshForUser(UserDto userDto) {
        loggedUserDto = userDto;
        userId = userDto.getId();
        binder.readBean(userDto);
    }

    public void updateUser(UserDto userDto) {
        userDto.setId(userId);
        userClient.updateUser(userDto);
    }

    private void bindFields() {
        binder.forField(name)
                .bind(UserDto::getName, UserDto::setName);
        binder.forField(surname)
                .bind(UserDto::getSurname, UserDto::setSurname);
        binder.forField(email)
                .bind(UserDto::getEmail, UserDto::setEmail);
        binder.forField(phoneNumber)
                .bind(UserDto::getPhoneNumber, UserDto::setPhoneNumber);
        binder.forField(password)
                .bind(UserDto::getPassword, UserDto::setPassword);
    }
}
