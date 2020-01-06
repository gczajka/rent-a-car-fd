package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.client.CarClient;
import com.frontend.rentacarfd.client.RentalClient;
import com.frontend.rentacarfd.domain.CarDto;
import com.frontend.rentacarfd.domain.RentalVesselDto;
import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.utils.LabelFactory;
import com.frontend.rentacarfd.views.utils.LabelStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import static com.frontend.rentacarfd.views.utils.StringStaticFinals.*;

@Component
public class CarView extends VerticalLayout {

    private Grid<CarDto> grid = new Grid<>(CarDto.class);
    private CarClient carClient;
    private RentalClient rentalClient;
    private Button addNewCar = new Button("Add new car");
    private Button refresh = new Button("Refresh rental availability");
    private Dialog newCarDialog = new Dialog();
    private Dialog updateDialog = new Dialog();
    private TextField brand = new TextField(BRAND);
    private TextField model = new TextField(MODEL);
    private TextField colour = new TextField(COLOUR);
    private TextField engineType = new TextField(ENGTYPE);
    private IntegerField engineCapacity = new IntegerField(ENGCAP);
    private IntegerField productionYear = new IntegerField(PRODYEAR);
    private IntegerField costPerDay = new IntegerField(COST);
    private TextField brandChange = new TextField(BRAND);
    private TextField modelChange = new TextField(MODEL);
    private TextField colourChange = new TextField(COLOUR);
    private TextField engineTypeChange = new TextField(ENGTYPE);
    private IntegerField engineCapacityChange = new IntegerField(ENGCAP);
    private IntegerField productionYearChange = new IntegerField(PRODYEAR);
    private IntegerField costPerDayChange = new IntegerField(COST);
    private Binder<CarDto> binderForSaving = new Binder<>();
    private Binder<CarDto> binderForUpdating = new Binder<>();
    private CarDto carDto = new CarDto();
    private UserDto loggedUserDto;
    private Long carId;
    private RentalView rentalView;


    public CarView(@Autowired CarClient carClient, @Autowired RentalClient rentalClient, @Autowired RentalView rentalView) {
        this.carClient = carClient;
        this.rentalClient = rentalClient;
        this.rentalView = rentalView;

        addNewCar.addClickListener(e -> newCarDialog.open());
        refresh.addClickListener(event -> refreshForEndedRentals());

        Button saveCar = new Button("Save car");
        bindFields();
        saveCar.addClickListener(e -> {
            if(areFieldsFilled()) {
                if(binderForSaving.writeBeanIfValid(carDto)) {
                    saveCar(carDto);
                }
            }
        });

        VerticalLayout newCarDialogLayout = new VerticalLayout();
        newCarDialogLayout.add(brand, model, colour, engineType, engineCapacity, productionYear, costPerDay, saveCar);
        newCarDialog.isCloseOnOutsideClick();
        newCarDialog.add(newCarDialogLayout);

        Button confirmUpdate = new Button("Update");
        confirmUpdate.addClickListener(e -> {
            if(areChangeFieldsFilled()) {
                if(binderForUpdating.writeBeanIfValid(carDto)) {
                    carDto.setId(carId);
                    carClient.updateCar(carDto);
                    refreshForAdmin();
                    updateDialog.close();
                }
            }
        });

        VerticalLayout optionsDialogLayout = new VerticalLayout();
        optionsDialogLayout.add(brandChange, modelChange, colourChange, engineTypeChange, engineCapacityChange, productionYearChange, costPerDayChange, confirmUpdate);
        updateDialog.isCloseOnOutsideClick();
        updateDialog.add(optionsDialogLayout);

        grid.setColumns("id", BRAND, MODEL, COLOUR, ENGTYPE, ENGCAP, PRODYEAR, COST);
        grid.addComponentColumn(carDto -> createUpdateButton(carDto));
        grid.addComponentColumn(carDto -> createDeleteButton(carDto));
        grid.addComponentColumn(carDto -> createRentalButton(carDto));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(addNewCar, refresh);
        LabelFactory labelFactory = new LabelFactory();
        Label label = labelFactory.createLabel(LabelStyle.TAB_INFO, "Pick a car that suits your needs. Standard rent period is 1 day. To lengthen your rental go to Rentals tab.");

        add(horizontalLayout, label, grid, newCarDialog);
    }

    void refreshForAdmin() {
        loggedUserDto = null;
        addNewCar.setEnabled(true);
        refresh.setEnabled(false);
        List<CarDto> cars = carClient.getCars();
        grid.setItems(cars);
    }

    void refreshForUser(UserDto userDto) {
        loggedUserDto = userDto;
        addNewCar.setEnabled(false);
        refresh.setEnabled(true);
        List<CarDto> cars = carClient.getCars();
        grid.setItems(cars);
    }

    private void refreshForEndedRentals() {
        List<CarDto> cars = carClient.getCars();
        grid.setItems(cars);
    }

    private void saveCar(CarDto carDto) {
        carClient.saveCar(carDto);
        refreshForAdmin();
        newCarDialog.close();
        clearFields();
    }

    private void clearFields() {
        brand.clear();
        model.clear();
        colour.clear();
        engineType.clear();
        engineCapacity.clear();
        productionYear.clear();
        costPerDay.clear();
    }

    private Button createUpdateButton(CarDto carDto) {
        Button updateButton = new Button("Update");
        updateButton.addClickListener(e -> {
            carId = carDto.getId();
            binderForUpdating.readBean(carDto);
            updateDialog.open();
        });
        if(!carDto.isAvailable()) {
            updateButton.setEnabled(false);
        }
        if(loggedUserDto != null) {
            updateButton.setEnabled(false);
        }
        return updateButton;
    }

    private Button createDeleteButton(CarDto carDto) {
        Button deleteButton = new Button("Delete");
        deleteButton.addClickListener(e -> {
            carClient.deleteCar(carDto.getId());
            refreshForAdmin();
        });
        if(!carDto.isAvailable()) {
            deleteButton.setEnabled(false);
        }
        if(loggedUserDto != null) {
            deleteButton.setEnabled(false);
        }
        return deleteButton;
    }

    private Button createRentalButton(CarDto carDto) {
        Button rentalButton = new Button("Rent");
        rentalButton.addClickListener(e -> {
            rentalClient.createRental(new RentalVesselDto(loggedUserDto.getId(), carDto.getId()));
            carDto.setAvailable(false);
            rentalView.refreshForUser(loggedUserDto);
            refreshForUser(loggedUserDto);
        });
        if(loggedUserDto == null) {
            rentalButton.setEnabled(false);
        }
        if(!carDto.isAvailable()) {
            rentalButton.setEnabled(false);
        }
        return rentalButton;
    }

    private void bindFields() {
        binderForSaving.forField(brand)
                .bind(CarDto::getBrand, CarDto::setBrand);
        binderForSaving.forField(model)
                .bind(CarDto::getModel, CarDto::setModel);
        binderForSaving.forField(colour)
                .bind(CarDto::getColour, CarDto::setColour);
        binderForSaving.forField(engineType)
                .bind(CarDto::getEngineType, CarDto::setEngineType);
        binderForSaving.forField(engineCapacity)
                .bind(CarDto::getEngineCapacity, CarDto::setEngineCapacity);
        binderForSaving.forField(productionYear)
                .bind(CarDto::getProductionYear, CarDto::setProductionYear);
        binderForSaving.forField(costPerDay)
                .bind(CarDto::getCostPerDay, CarDto::setCostPerDay);

        binderForUpdating.forField(brandChange)
                .bind(CarDto::getBrand, CarDto::setBrand);
        binderForUpdating.forField(modelChange)
                .bind(CarDto::getModel, CarDto::setModel);
        binderForUpdating.forField(colourChange)
                .bind(CarDto::getColour, CarDto::setColour);
        binderForUpdating.forField(engineTypeChange)
                .bind(CarDto::getEngineType, CarDto::setEngineType);
        binderForUpdating.forField(engineCapacityChange)
                .bind(CarDto::getEngineCapacity, CarDto::setEngineCapacity);
        binderForUpdating.forField(productionYearChange)
                .bind(CarDto::getProductionYear, CarDto::setProductionYear);
        binderForUpdating.forField(costPerDayChange)
                .bind(CarDto::getCostPerDay, CarDto::setCostPerDay);
    }

    private boolean areFieldsFilled() {
        boolean state = false;
        if(!brand.getValue().equals("") && !model.getValue().equals("") && !colour.getValue().equals("") && !engineType.getValue().equals("") && engineCapacity.getValue() != null && productionYear.getValue() != null && costPerDay.getValue() != null) {
            state = true;
        }
        return state;
    }

    private boolean areChangeFieldsFilled() {
        boolean state = false;
        if(!brandChange.getValue().equals("") && !modelChange.getValue().equals("") && !colourChange.getValue().equals("") && !engineTypeChange.getValue().equals("") && engineCapacityChange.getValue() != null && productionYearChange.getValue() != null && costPerDayChange.getValue() != null) {
            state = true;
        }
        return state;
    }

}
