package com.frontend.rentacarfd.views.logout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.dialog.Dialog;
import org.springframework.stereotype.Component;


@Component
public class LogoutView extends VerticalLayout {
    Dialog dialog = new Dialog();
    public LogoutView() {
        Button logoutButton = new Button("Log out");
        logoutButton.addClickListener(e -> logOut());
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(e -> cancel());
        HorizontalLayout dialogLayout = new HorizontalLayout();
        dialogLayout.add(logoutButton, cancelButton);
        dialog.add(dialogLayout);
    }

    public void logOut() {
        dialog.close();
        getUI().get().navigate("loginView");
    }

    public void cancel() {
        dialog.close();
    }

    public void displayDialog() {
        dialog.open();
    }

}
