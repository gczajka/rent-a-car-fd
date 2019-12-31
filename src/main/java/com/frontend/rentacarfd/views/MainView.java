package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.views.car.CarView;
import com.frontend.rentacarfd.views.utils.PagedTabs;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {
    @Autowired
    private final CarView carView;

    public MainView(CarView carView) {
        this.carView = carView;

        Button button3 = new Button("Click3");

        PagedTabs tabs = new PagedTabs();
        tabs.add(carView, "Cars");
        tabs.add(button3, "Tab caption 2");

        add(tabs);
    }
}
