package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.client.UserClient;
import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.utils.LabelFactory;
import com.frontend.rentacarfd.views.utils.LabelStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import static com.frontend.rentacarfd.views.utils.StringStaticFinals.*;

@Component
public class UserView extends VerticalLayout {

    private Grid<UserDto> grid = new Grid<>(UserDto.class);
    private UserClient userClient;

    private Dialog dialog = new Dialog();
    private TextField name = new TextField(NAME);
    private TextField surname = new TextField(SURNAME);
    private TextField email = new TextField(EMAIL);
    private TextField phoneNumber = new TextField(PHONENUM);
    private TextField password = new TextField(PASS);
    private Binder<UserDto> binder = new Binder<>();
    private UserDto userDto = new UserDto();

    public UserView(@Autowired UserClient userClient) {
        this.userClient = userClient;

        Button addNewUser = new Button("Add new user");
        VerticalLayout dialogLayout = new VerticalLayout();
        Button saveUser = new Button("Save user");
        bindFields();
        addNewUser.addClickListener(e -> dialog.open());
        saveUser.addClickListener(e -> {
            if(areFieldsFilled()) {
                if(binder.writeBeanIfValid(userDto)) {
                saveUser(userDto);
                }
            }
        });

        dialogLayout.add(name, surname, email, phoneNumber, password, saveUser);

        dialog.isCloseOnOutsideClick();
        dialog.add(dialogLayout);

        grid.setColumns("id", NAME, SURNAME, EMAIL, PHONENUM, PASS);

        LabelFactory labelFactory = new LabelFactory();
        Label label = labelFactory.createLabel(LabelStyle.TAB_INFO, "Welcome Admin! Here you can add new users and monitor existing ones");

        add(addNewUser, label, grid, dialog);
    }

    void refresh() {
        List<UserDto> users = userClient.getUsers();
        grid.setItems(users);
    }

    private void saveUser(UserDto userDto) {
        if(!userClient.isUserRegistered(userDto.getEmail())) {
            userClient.registerUser(userDto);
            refresh();
            dialog.close();
            clearFields();
        }
    }

    private void clearFields() {
        name.clear();
        surname.clear();
        email.clear();
        phoneNumber.clear();
        password.clear();
    }

    private void bindFields() {
        binder.forField(name)
                .bind(UserDto::getName, UserDto::setName);
        binder.forField(surname)
                .bind(UserDto::getSurname, UserDto::setSurname);
        binder.forField(email)
                .bind(UserDto::getEmail, UserDto::setEmail);
        binder.forField(phoneNumber)
                .bind(UserDto::getPhoneNumber, UserDto::setPhoneNumber);
        binder.forField(password)
                .bind(UserDto::getPassword, UserDto::setPassword);
    }

    private boolean areFieldsFilled() {
        boolean state = false;
        if(!name.getValue().equals("") && !surname.getValue().equals("") && !email.getValue().equals("") && !phoneNumber.getValue().equals("") && !password.getValue().equals("")) {
            state = true;
        }
        return state;
    }
}
