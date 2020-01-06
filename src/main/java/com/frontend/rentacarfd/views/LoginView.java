package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.client.LoginClient;
import com.frontend.rentacarfd.client.UserClient;
import com.frontend.rentacarfd.domain.LoginDto;
import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.utils.LabelFactory;
import com.frontend.rentacarfd.views.utils.LabelStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.*;

@Component
@Route(LOGIN_VIEW)
public class LoginView extends VerticalLayout {
    private EmailField email = new EmailField(EMAIL);
    private PasswordField password = new PasswordField(PASS);
    private Binder<LoginDto> binder = new Binder<>();
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

        LabelFactory labelFactory = new LabelFactory();
        Label applicationTitle = labelFactory.createLabel(LabelStyle.APP_TITLE, "Rent-a-Car Service");
        Label sectionTitle = labelFactory.createLabel(LabelStyle.SECTION_NAME, "Login section");

        Button logIn = new Button(" Log in");
        Button register = new Button(" Register");

        binder.forField(email)
                .bind(LoginDto::getEmail, LoginDto::setEmail);
        binder.forField(password)
                .bind(LoginDto::getPassword, LoginDto::setPassword);

        logIn.addClickListener(e -> logIn());
        register.addClickListener(e -> {
            clear();
            getUI().get().navigate(REGISTRATION_VIEW);
        });

        add(applicationTitle, sectionTitle, email, password, logIn, register);
        setAlignItems(Alignment.CENTER);
    }

    private void logIn() {
        LoginDto loginDto = new LoginDto();
        if(areFieldsFilled()) {
            if(binder.writeBeanIfValid(loginDto)) {
                if ((loginDto.getEmail().equals("admin")) && (loginDto.getPassword().equals("admin"))) {
                    clear();
                    mainView.adminViewSetup();
                    mainView.setBackStartingTab();
                    getUI().get().navigate(MAIN_VIEW);
                } else {
                    boolean isConfirmed = loginClient.isLoginRegistered(loginDto);
                    if (isConfirmed) {
                        clear();
                        UserDto userDto = userClient.getUserByEmail(loginDto.getEmail());
                        mainView.nonAdminViewSetup(userDto);
                        mainView.setBackStartingTab();
                        getUI().get().navigate(MAIN_VIEW);
                    }
                }
            }
        }
    }

    private void clear() {
        email.clear();
        password.clear();
    }

    private boolean areFieldsFilled() {
        boolean state = false;
        if(!email.getValue().equals("") && !password.getValue().equals("")) {
            state = true;
        }
        return state;
    }
}
