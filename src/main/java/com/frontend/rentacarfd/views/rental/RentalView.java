package com.frontend.rentacarfd.views.rental;

import com.frontend.rentacarfd.client.CarClient;
import com.frontend.rentacarfd.client.RentalClient;
import com.frontend.rentacarfd.domain.CarDto;
import com.frontend.rentacarfd.domain.RentalDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalView extends VerticalLayout {
    private Grid<RentalDto> grid = new Grid<>(RentalDto.class);
    private RentalClient rentalClient;

    public RentalView(@Autowired RentalClient rentalClient) {
        this.rentalClient = rentalClient;

        Button button1 = new Button("Add new rental");
        grid.setColumns("id", "startDate", "endDate", "duration", "cost", "carModel", "userSurname");
        List<RentalDto> rentals = rentalClient.getRentals();
        grid.setItems(rentals);

        add(button1, grid);
    }
}
