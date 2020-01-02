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

    private Dialog dialog = new Dialog();
    private TextField brand = new TextField("brand");
    private TextField model = new TextField("model");
    private TextField colour = new TextField("colour");
    private TextField engineType = new TextField("engineType");
    private IntegerField engineCapacity = new IntegerField("engineCapacity");
    private IntegerField productionYear = new IntegerField("productionYear");
    private IntegerField costPerDay = new IntegerField("costPerDay");
    private Binder<CarDto> binder = new Binder<>();
    private CarDto carDto = new CarDto();


    public CarView(@Autowired CarClient carClient) {
        this.carClient = carClient;

        Button addNewCar = new Button("Add new car");
        VerticalLayout dialogLayout = new VerticalLayout();
        Button saveCar = new Button("Save car");
        bindFields();
        addNewCar.addClickListener(e -> dialog.open());
        saveCar.addClickListener(e -> {
            binder.writeBeanIfValid(carDto);
            saveCar(carDto);
        });

        dialogLayout.add(brand, model, colour, engineType, engineCapacity, productionYear, costPerDay, saveCar);

        dialog.isCloseOnOutsideClick();
        dialog.add(dialogLayout);

        grid.setColumns("id", "brand", "model", "colour", "engineType", "engineCapacity", "productionYear", "costPerDay");

        add(addNewCar, grid, dialog);
    }

    public void refresh() {
        List<CarDto> cars = carClient.getCars();
        grid.setItems(cars);
    }

    private void saveCar(CarDto carDto) {
        carClient.saveCar(carDto);
        refresh();
        dialog.close();
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

    private void bindFields() {
        binder.forField(brand)
                .bind(CarDto::getBrand, CarDto::setBrand);
        binder.forField(model)
                .bind(CarDto::getModel, CarDto::setModel);
        binder.forField(colour)
                .bind(CarDto::getColour, CarDto::setColour);
        binder.forField(engineType)
                .bind(CarDto::getEngineType, CarDto::setEngineType);
        binder.forField(engineCapacity)
                .bind(CarDto::getEngineCapacity, CarDto::setEngineCapacity);
        binder.forField(productionYear)
                .bind(CarDto::getProductionYear, CarDto::setProductionYear);
        binder.forField(costPerDay)
                .bind(CarDto::getCostPerDay, CarDto::setCostPerDay);
    }
}
