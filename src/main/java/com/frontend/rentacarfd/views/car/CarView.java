package com.frontend.rentacarfd.views.car;

import com.frontend.rentacarfd.client.CarClient;
import com.frontend.rentacarfd.domain.CarDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarView extends VerticalLayout {
    private Grid<CarDto> grid = new Grid<>(CarDto.class);
    private CarClient carClient;

    public CarView(@Autowired CarClient carClient) {
        this.carClient = carClient;

        Button button1 = new Button("Add new car");
        grid.setColumns("id", "brand", "model", "colour", "engineType", "engineCapacity", "productionYear", "costPerDay", "available");
        List<CarDto> cars = carClient.getCars();
        grid.setItems(cars);

        add(button1, grid);
    }
}
