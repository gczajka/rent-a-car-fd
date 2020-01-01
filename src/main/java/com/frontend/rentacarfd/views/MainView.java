package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.views.car.CarView;
import com.frontend.rentacarfd.views.login.LoginView;
import com.frontend.rentacarfd.views.logout.LogoutView;
import com.frontend.rentacarfd.views.rental.RentalView;
import com.frontend.rentacarfd.views.user.UserView;
import com.frontend.rentacarfd.views.utils.PagedTabs;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {
    @Autowired
    private final CarView carView;
    @Autowired
    private final UserView userView;
    @Autowired
    private final RentalView rentalView;
    @Autowired
    private final LoginView loginView;
    @Autowired
    private final LogoutView logoutView;

    public MainView(CarView carView, UserView userView, RentalView rentalView, LoginView loginView, LogoutView logoutView) {
        this.carView = carView;
        this.userView = userView;
        this.rentalView = rentalView;
        this.loginView = loginView;
        this.logoutView = logoutView;

        PagedTabs tabs = new PagedTabs();
        Tab loginTab = new Tab("Login");
        Tab carTab = new Tab("Cars");
        Tab userTab = new Tab("Users");
        Tab rentalTab = new Tab("Rentals");
        Tab logoutTab = new Tab("Logout");
        tabs.add(loginView, loginTab);
        tabs.add(carView, carTab);
        tabs.add(userView, userTab);
        tabs.add(rentalView, rentalTab);
        tabs.add(logoutView, logoutTab);
        carTab.setEnabled(false);
        userTab.setEnabled(false);
        rentalTab.setEnabled(false);
        logoutTab.setEnabled(false);
        add(tabs);
    }
}
