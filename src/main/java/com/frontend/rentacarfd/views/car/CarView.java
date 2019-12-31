package com.frontend.rentacarfd.views.car;

import com.frontend.rentacarfd.domain.CarDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;

@Component
public class CarView extends VerticalLayout {
    private Grid<CarDto> grid = new Grid<>(CarDto.class);

    public CarView() {
        Button button1 = new Button("Click1");
        Button button2 = new Button("Click2");

        grid.setColumns("id", "brand", "model", "colour", "engineType", "engineCapacity", "productionYear", "costPerDay", "available");

        add(button1, button2, grid);
    }
}
