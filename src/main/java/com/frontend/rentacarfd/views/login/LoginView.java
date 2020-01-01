package com.frontend.rentacarfd.views.login;

import com.frontend.rentacarfd.client.LoginClient;
import com.frontend.rentacarfd.domain.LoginDto;
import com.frontend.rentacarfd.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("loginView")
public class LoginView extends VerticalLayout {
    private EmailField email = new EmailField("e-mail");
    private PasswordField password = new PasswordField("password");
    private Button logIn = new Button(" Log in");
    private Button register = new Button(" Register");
    private Binder<LoginDto> binder = new Binder<>();
    @Autowired
    private MainView mainView;
    @Autowired
    private LoginClient loginClient;

    public LoginView(MainView mainView, LoginClient loginClient) {
        this.mainView = mainView;
        this.loginClient = loginClient;

        add(email, password, logIn, register);
        setHorizontalComponentAlignment(Alignment.CENTER, email, password, logIn, register);

        binder.forField(email)
                .bind(LoginDto::getEmail, LoginDto::setEmail);
        binder.forField(password)
                .bind(LoginDto::getPassword, LoginDto::setPassword);

        logIn.addClickListener(e -> logIn());
        register.addClickListener(e -> getUI().get().navigate(""));

    }

    private void logIn() {
        LoginDto loginDto = new LoginDto();

        loginDto.setEmail(email.getValue());
        loginDto.setPassword(password.getValue());

        if((loginDto.getEmail().equals("admin@admin.com")) && (loginDto.getPassword().equals("admin"))) {
            mainView.refresh();
            mainView.setBackStartingTab();
            getUI().get().navigate("mainView");
        } else {
            boolean isConfirmed = loginClient.isLoginRegistered(loginDto.getEmail(), loginDto.getPassword());
            if(isConfirmed) {
                mainView.nonAdminViewSetup();
                mainView.setBackStartingTab();
            getUI().get().navigate("mainView");
        } else {

        }}
    }
}
