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
    private Button alreadyRegisteredButton = new Button("Registered users should press this button to proceed to Login section. New users should fill the fields below and press the Register button.");
    private Button registerButton = new Button("Register");

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

        alreadyRegisteredButton.addClickListener(e -> getUI().get().navigate("mainView"));
        registerButton.addClickListener(e -> {
           save();
           getUI().get().navigate("mainView");
        });

        add(applicationTitle, alreadyRegisteredButton, name, surname, email, phoneNumber, password, registerButton);
        setHorizontalComponentAlignment(Alignment.CENTER, applicationTitle, alreadyRegisteredButton, name, surname, email, phoneNumber, password, registerButton);
    }

    private void save() {
        UserDto userDto = new UserDto();

        userDto.setName(name.getValue());
        userDto.setSurname(surname.getValue());
        userDto.setEmail(email.getValue());
        userDto.setPhoneNumber(phoneNumber.getValue());
        userDto.setPassword(password.getValue());

        if(!userClient.isUserRegistered(userDto.getPhoneNumber())) {
            userClient.registerUser(userDto);
        } else {

        }
    }
}
