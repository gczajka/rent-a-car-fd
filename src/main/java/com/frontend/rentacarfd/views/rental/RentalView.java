package com.frontend.rentacarfd.views.rental;

import com.frontend.rentacarfd.client.RentalClient;
import com.frontend.rentacarfd.domain.RentalDto;
import com.frontend.rentacarfd.domain.UserDto;
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

        grid.setColumns("id", "startDate", "endDate", "duration", "cost", "carModel", "userSurname");
        add(grid);
    }

    public void refreshForAdmin() {
        List<RentalDto> rentals = rentalClient.getRentals();
        grid.setItems(rentals);
    }

    public void refreshForUser(UserDto userDto) {
        List<RentalDto> rentals = rentalClient.getRentalsByUsersId(userDto.getId());
        grid.setItems(rentals);
    }
}
