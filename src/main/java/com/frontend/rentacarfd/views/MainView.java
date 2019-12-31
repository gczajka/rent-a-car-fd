package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.views.utils.PagedTabs;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    public MainView() {
        Button button1 = new Button("Click1");
        Button button2 = new Button("Click2");
        Button button3 = new Button("Click3");

        VerticalLayout layout = new VerticalLayout();
        layout.add(button1, button2);

        PagedTabs tabs = new PagedTabs();
        tabs.add(layout, "Tab caption 1");
        tabs.add(button3, "Tab caption 2");

        add(tabs);
    }
}
