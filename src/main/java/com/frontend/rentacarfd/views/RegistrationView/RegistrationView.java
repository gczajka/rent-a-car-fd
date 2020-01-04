package com.frontend.rentacarfd.views.RegistrationView;

import com.frontend.rentacarfd.client.UserClient;
import com.frontend.rentacarfd.domain.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "")
public class RegistrationView extends VerticalLayout {
    @Autowired
    private UserClient userClient;
    private Binder<UserDto> binder = new Binder<>();
    private TextField name = new TextField("name");
    private TextField surname = new TextField("surname");
    private EmailField email = new EmailField("e-mail");
    private TextField phoneNumber = new TextField("phone number");
    private PasswordField password = new PasswordField("password");
    private Button alreadyRegisteredButton = new Button("Proceed to login section");
    private Button registerButton = new Button("Register");
    private UserDto userDto = new UserDto();

    public RegistrationView(UserClient userClient) {
        this.userClient = userClient;

        Span applicationTitle = new Span("Rent-a-Car Service");

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

        alreadyRegisteredButton.addClickListener(e -> getUI().get().navigate("loginView"));
        registerButton.addClickListener(e -> {
            binder.writeBeanIfValid(userDto);
            save(userDto);
        });

        add(applicationTitle, alreadyRegisteredButton, name, surname, email, phoneNumber, password, registerButton);
        setAlignItems(Alignment.CENTER);
    }

    private void save(UserDto userDto) {
        if(!userClient.isUserRegistered(userDto.getEmail())) {
            userClient.registerUser(userDto);
            getUI().get().navigate("loginView");
            clear();
        } else {

        }
    }

    private void clear() {
        name.clear();
        surname.clear();;
        email.clear();
        phoneNumber.clear();
        password.clear();
    }
}
