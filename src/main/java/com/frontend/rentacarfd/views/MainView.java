package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.views.car.CarView;
import com.frontend.rentacarfd.views.login.LoginView;
import com.frontend.rentacarfd.views.rental.RentalView;
import com.frontend.rentacarfd.views.user.UserView;
import com.frontend.rentacarfd.views.utils.PagedTabs;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    public MainView(CarView carView, UserView userView, RentalView rentalView, LoginView loginView) {
        this.carView = carView;
        this.userView = userView;
        this.rentalView = rentalView;
        this.loginView = loginView;

        PagedTabs tabs = new PagedTabs();
        tabs.add(loginView, "Login");
        tabs.add(carView, "Cars");
        tabs.add(userView, "Users");
        tabs.add(rentalView, "Rentals");

        add(tabs);
    }
}
