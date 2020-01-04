package com.frontend.rentacarfd.views.login;

import com.frontend.rentacarfd.client.LoginClient;
import com.frontend.rentacarfd.client.UserClient;
import com.frontend.rentacarfd.domain.LoginDto;
import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Route("loginView")
public class LoginView extends VerticalLayout {
    private EmailField email = new EmailField("e-mail");
    private PasswordField password = new PasswordField("password");
    private Button logIn = new Button(" Log in");
    private Button register = new Button(" Register");
    private Binder<LoginDto> binder = new Binder<>();
    private UserDto userDto;
    @Autowired
    private MainView mainView;
    @Autowired
    private LoginClient loginClient;
    @Autowired
    private UserClient userClient;

    public LoginView(MainView mainView, LoginClient loginClient, UserClient userClient) {
        this.mainView = mainView;
        this.loginClient = loginClient;
        this.userClient = userClient;

        binder.forField(email)
                .bind(LoginDto::getEmail, LoginDto::setEmail);
        binder.forField(password)
                .bind(LoginDto::getPassword, LoginDto::setPassword);

        logIn.addClickListener(e -> logIn());
        register.addClickListener(e -> {
            getUI().get().navigate("");
            clear();
        });

        add(email, password, logIn, register);
        setAlignItems(Alignment.CENTER);
    }

    private void logIn() {
        LoginDto loginDto = new LoginDto();
        binder.writeBeanIfValid(loginDto);
        clear();

        if((loginDto.getEmail().equals("admin")) && (loginDto.getPassword().equals("admin"))) {
            mainView.adminViewSetup();
            mainView.setBackStartingTab();
            getUI().get().navigate("mainView");
        } else {
            boolean isConfirmed = loginClient.isLoginRegistered(loginDto);
            if(isConfirmed) {
                userDto = userClient.getUserByEmail(loginDto.getEmail());
                mainView.nonAdminViewSetup(userDto);
                mainView.setBackStartingTab();
            getUI().get().navigate("mainView");
        } else {

        }}
    }

    private void clear() {
        email.clear();
        password.clear();
    }
}
