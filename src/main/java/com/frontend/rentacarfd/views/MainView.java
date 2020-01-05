package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.utils.LabelFactory;
import com.frontend.rentacarfd.views.utils.LabelStyle;
import com.frontend.rentacarfd.views.utils.PagedTabs;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.*;

@Component
@Route(value = MAIN_VIEW)
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
    private Tab userAccountTab = new Tab("MyAccount");

    public MainView(CarView carView, UserView userView, RentalView rentalView, UserAccountView userAccountView, LogoutView logoutView) {
        this.carView = carView;
        this.userView = userView;
        this.rentalView = rentalView;
        this.userAccountView = userAccountView;
        this.logoutView = logoutView;

        LabelFactory labelFactory = new LabelFactory();
        Tab rentalTab = new Tab("Rentals");
        Tab logoutTab = new Tab();
        Label applicationTitle = labelFactory.createLabel(LabelStyle.APP_TITLE, "Rent-a-Car Service");

        tabs.add(carView, carTab);
        tabs.add(userView, userTab);
        tabs.add(rentalView, rentalTab);
        tabs.add(userAccountView, userAccountTab);
        tabs.add(logoutView, logoutTab);
        Button logoutButton = new Button("Log out");
        logoutTab.add(logoutButton);
        logoutButton.addClickListener(e ->logoutView.displayDialog());

        add(applicationTitle, tabs);
        setHorizontalComponentAlignment(Alignment.CENTER, applicationTitle);
    }

    void adminViewSetup() {
        userAccountTab.setEnabled(false);
        userTab.setEnabled(true);
        carView.refreshForAdmin();
        userView.refresh();
        rentalView.refreshForAdmin();
    }

    void nonAdminViewSetup(UserDto userDto) {
        carView.refreshForUser(userDto);
        rentalView.refreshForUser(userDto);
        userTab.setEnabled(false);
        userAccountTab.setEnabled(true);
        userAccountView.refreshForUser(userDto);
    }

    void setBackStartingTab() {
        tabs.select(carTab);
    }
}
