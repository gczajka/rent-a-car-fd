package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.views.car.CarView;
import com.frontend.rentacarfd.views.logout.LogoutView;
import com.frontend.rentacarfd.views.rental.RentalView;
import com.frontend.rentacarfd.views.user.UserView;
import com.frontend.rentacarfd.views.utils.PagedTabs;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Route(value = "mainView")
public class MainView extends VerticalLayout {
    @Autowired
    private final CarView carView;
    @Autowired
    private final UserView userView;
    @Autowired
    private final RentalView rentalView;
    @Autowired
    private final LogoutView logoutView;

    PagedTabs tabs = new PagedTabs();
    Tab carTab = new Tab("Cars");
    Tab userTab = new Tab("Users");
    Tab rentalTab = new Tab("Rentals");
    Tab logoutTab = new Tab("Logout");

    public MainView(CarView carView, UserView userView, RentalView rentalView, LogoutView logoutView) {
        this.carView = carView;
        this.userView = userView;
        this.rentalView = rentalView;
        this.logoutView = logoutView;

        tabs.add(carView, carTab);
        tabs.add(userView, userTab);
        tabs.add(rentalView, rentalTab);
        tabs.add(logoutView, logoutTab);
        add(tabs);
    }

    public void refresh() {
        carView.refresh();
        userView.refresh();
        rentalView.refresh();
    }

    public void setBackStartingTab() {
        tabs.select(carTab);
    }

    public void nonAdminViewSetup() {
        carView.refresh();
        userTab.setEnabled(false);
        rentalTab.setEnabled(false);
    }
}
