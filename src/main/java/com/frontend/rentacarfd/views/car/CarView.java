package com.frontend.rentacarfd.views.car;

import com.frontend.rentacarfd.client.CarClient;
import com.frontend.rentacarfd.domain.CarDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarView extends VerticalLayout {
    private Grid<CarDto> grid = new Grid<>(CarDto.class);
    private CarClient carClient;
    private Button addNewCar = new Button("Add new car");
    private Dialog newCarDialog = new Dialog();
    private Dialog updateDialog = new Dialog();
    private TextField brand = new TextField("brand");
    private TextField model = new TextField("model");
    private TextField colour = new TextField("colour");
    private TextField engineType = new TextField("engineType");
    private IntegerField engineCapacity = new IntegerField("engineCapacity");
    private IntegerField productionYear = new IntegerField("productionYear");
    private IntegerField costPerDay = new IntegerField("costPerDay");
    private TextField brandChange = new TextField("brand");
    private TextField modelChange = new TextField("model");
    private TextField colourChange = new TextField("colour");
    private TextField engineTypeChange = new TextField("engineType");
    private IntegerField engineCapacityChange = new IntegerField("engineCapacity");
    private IntegerField productionYearChange = new IntegerField("productionYear");
    private IntegerField costPerDayChange = new IntegerField("costPerDay");
    private Binder<CarDto> binderForSaving = new Binder<>();
    private Binder<CarDto> binderForUpdating = new Binder<>();
    private CarDto carDto = new CarDto();
    private Long carId;


    public CarView(@Autowired CarClient carClient) {
        this.carClient = carClient;

        Button saveCar = new Button("Save car");
        bindFields();

        addNewCar.addClickListener(e -> newCarDialog.open());
        saveCar.addClickListener(e -> {
            binderForSaving.writeBeanIfValid(carDto);
            saveCar(carDto);
        });

        VerticalLayout newCarDialogLayout = new VerticalLayout();
        newCarDialogLayout.add(brand, model, colour, engineType, engineCapacity, productionYear, costPerDay, saveCar);
        newCarDialog.isCloseOnOutsideClick();
        newCarDialog.add(newCarDialogLayout);


        Button confirmUpdate = new Button("Update");
        confirmUpdate.addClickListener(e -> {
            binderForUpdating.writeBeanIfValid(carDto);
            carDto.setId(carId);
            carClient.updateCar(carDto);
            refreshForAdmin();
            updateDialog.close();
        });
        VerticalLayout optionsDialogLayout = new VerticalLayout();
        optionsDialogLayout.add(brandChange, modelChange, colourChange, engineTypeChange, engineCapacityChange, productionYearChange, costPerDayChange, confirmUpdate);
        updateDialog.isCloseOnOutsideClick();
        updateDialog.add(optionsDialogLayout);

        grid.setColumns("id", "brand", "model", "colour", "engineType", "engineCapacity", "productionYear", "costPerDay");
        grid.addComponentColumn(carDto -> createUpdateButton(carDto));
        grid.addComponentColumn(carDto -> createDeleteButton(carDto));

        add(addNewCar, grid, newCarDialog);
    }

    public void refreshForAdmin() {
        addNewCar.setEnabled(true);
        List<CarDto> cars = carClient.getCars();
        grid.setItems(cars);
    }

    public void refreshForUser() {
        addNewCar.setEnabled(false);
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
        return updateButton;
    }

    private Button createDeleteButton(CarDto carDto) {
        Button deleteButton = new Button("Delete");
        deleteButton.addClickListener(e -> {
            carClient.deleteCar(carDto.getId());
            refreshForAdmin();
        });
        return deleteButton;
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
}
