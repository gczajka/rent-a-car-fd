package com.frontend.rentacarfd.views.logout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;

@Component
public class LogoutView extends VerticalLayout {

    public LogoutView() {
        Button button = new Button("If you wish to logout just click this button", new Icon(VaadinIcon.EJECT));
        button.setIconAfterText(true);
        add(button);
        setHorizontalComponentAlignment(Alignment.CENTER, button);
    }

}
