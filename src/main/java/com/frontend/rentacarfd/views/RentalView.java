package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.client.RentalClient;
import com.frontend.rentacarfd.domain.RentalDto;
import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.utils.LabelFactory;
import com.frontend.rentacarfd.views.utils.LabelStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalView extends VerticalLayout {
    private Grid<RentalDto> grid = new Grid<>(RentalDto.class);
    private RentalClient rentalClient;
    private UserDto loggedUserDto;
    private Long rentalId;

    public RentalView(@Autowired RentalClient rentalClient) {
        this.rentalClient = rentalClient;

        grid.setColumns("id", "startDate", "endDate", "duration", "cost", "carModel", "userId");
        grid.addComponentColumn(rentalDto -> createAddDayButton(rentalDto));
        grid.addComponentColumn(rentalDto -> createEndRentalButton(rentalDto));

        LabelFactory labelFactory = new LabelFactory();
        Label label = labelFactory.createLabel(LabelStyle.TAB_INFO, "To lengthen your rental click the \"Add 1 day button\"");

        add(label, grid);
    }

    void refreshForAdmin() {
        loggedUserDto = null;
        List<RentalDto> rentals = rentalClient.getRentals();
        grid.setItems(rentals);
    }

    void refreshForUser(UserDto userDto) {
        loggedUserDto = userDto;
        List<RentalDto> rentals = rentalClient.getRentalsByUsersId(userDto.getId());
        grid.setItems(rentals);
    }

    private Button createAddDayButton(RentalDto rentalDto) {
        Button addDay = new Button("Add 1 day");
        addDay.addClickListener(e -> {
            rentalId = rentalDto.getId();
            addDay(rentalId);
        });
        return addDay;
    }

    private void addDay(Long rentalId) {
        rentalClient.modifyRental(rentalId);
        refreshForUser(loggedUserDto);
    }

    private Button createEndRentalButton(RentalDto rentalDto) {
        Button endRental = new Button("End rental");
        endRental.addClickListener(e -> {
            rentalId = rentalDto.getId();
            deleteRental(rentalId);
        });
        return endRental;
    }

    private void deleteRental(Long rentalId) {
        rentalClient.deleteRental(rentalId);
        refreshForUser(loggedUserDto);
    }
}
