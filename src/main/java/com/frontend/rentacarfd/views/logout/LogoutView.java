package com.frontend.rentacarfd.views.logout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;

@Component
public class LogoutView extends VerticalLayout {

    public LogoutView() {
        Button button = new Button("Log out");
        add(button);
    }

}
