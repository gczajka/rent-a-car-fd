package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.client.EmailValidatorClient;
import com.frontend.rentacarfd.client.UserClient;
import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.utils.LabelFactory;
import com.frontend.rentacarfd.views.utils.LabelStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.*;


@Route(value = REGISTRATION_VIEW)
public class RegistrationView extends VerticalLayout {
    @Autowired
    private UserClient userClient;
    @Autowired
    private EmailValidatorClient emailValidatorClient;
    private Binder<UserDto> binder = new Binder<>();
    private TextField name = new TextField(NAME);
    private TextField surname = new TextField(SURNAME);
    private EmailField email = new EmailField(EMAIL);
    private TextField phoneNumber = new TextField(PHONENUM);
    private PasswordField password = new PasswordField(PASS);

    private UserDto userDto = new UserDto();

    public RegistrationView(UserClient userClient) {
        this.userClient = userClient;

        LabelFactory labelFactory = new LabelFactory();
        Label applicationTitle = labelFactory.createLabel(LabelStyle.APP_TITLE, "Rent-a-Car Service");
        Label sectionTitle = labelFactory.createLabel(LabelStyle.SECTION_NAME, "Registration section");
        Button alreadyRegisteredButton = new Button("I want to proceed directly to the login section");
        Button registerButton = new Button("Register");

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

        alreadyRegisteredButton.addClickListener(e -> getUI().get().navigate(LOGIN_VIEW));
        registerButton.addClickListener(e -> {
            binder.writeBeanIfValid(userDto);
            save(userDto);
        });

        add(applicationTitle, sectionTitle, name, surname, email, phoneNumber, password, registerButton, alreadyRegisteredButton);
        setAlignItems(Alignment.CENTER);
    }

    private void save(UserDto userDto) {
        if((!userClient.isUserRegistered(userDto.getEmail())) && (emailValidatorClient.isEmailValid(userDto.getEmail()))) {
            userClient.registerUser(userDto);
            getUI().get().navigate(LOGIN_VIEW);
            clear();
        }
    }

    private void clear() {
        name.clear();
        surname.clear();
        email.clear();
        phoneNumber.clear();
        password.clear();
    }
}
