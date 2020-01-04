package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.car.CarView;
import com.frontend.rentacarfd.views.logout.LogoutView;
import com.frontend.rentacarfd.views.rental.RentalView;
import com.frontend.rentacarfd.views.user.UserView;
import com.frontend.rentacarfd.views.userAccount.UserAccountView;
import com.frontend.rentacarfd.views.utils.PagedTabs;
import com.vaadin.flow.component.button.Button;
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
    private final UserAccountView userAccountView;
    @Autowired
    private final LogoutView logoutView;

    private PagedTabs tabs = new PagedTabs();
    private Tab carTab = new Tab("Cars");
    private Tab userTab = new Tab("Users");
    private Tab rentalTab = new Tab("Rentals");
    private Tab userAccountTab = new Tab("MyAccount");
    private Tab logoutTab = new Tab();
    private UserDto loggedUserDto;

    public MainView(CarView carView, UserView userView, RentalView rentalView, UserAccountView userAccountView, LogoutView logoutView) {
        this.carView = carView;
        this.userView = userView;
        this.rentalView = rentalView;
        this.userAccountView = userAccountView;
        this.logoutView = logoutView;

        tabs.add(carView, carTab);
        tabs.add(userView, userTab);
        tabs.add(rentalView, rentalTab);
        tabs.add(userAccountView, userAccountTab);
        tabs.add(logoutView, logoutTab);
        Button logoutButton = new Button("Log out");
        logoutTab.add(logoutButton);
        logoutButton.addClickListener(e -> {
            logoutView.displayDialog();
        });

        add(tabs);
    }

    public void adminViewSetup() {
        loggedUserDto = null;
        userAccountTab.setEnabled(false);
        userTab.setEnabled(true);
        carView.refreshForAdmin();
        userView.refresh();
        rentalView.refreshForAdmin();
    }

    public void nonAdminViewSetup(UserDto userDto) {
        loggedUserDto = userDto;
        carView.refreshForUser(userDto);
        rentalView.refreshForUser(userDto);
        userTab.setEnabled(false);
        userAccountTab.setEnabled(true);
        userAccountView.refreshForUser(userDto);
    }

    public void setBackStartingTab() {
        tabs.select(carTab);
    }
}
