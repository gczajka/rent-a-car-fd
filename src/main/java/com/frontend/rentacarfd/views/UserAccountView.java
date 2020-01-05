package com.frontend.rentacarfd.views;

import com.frontend.rentacarfd.client.UserClient;
import com.frontend.rentacarfd.domain.UserDto;
import com.frontend.rentacarfd.views.utils.LabelFactory;
import com.frontend.rentacarfd.views.utils.LabelStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.frontend.rentacarfd.views.utils.StringStaticFinals.*;

@Component
public class UserAccountView extends VerticalLayout {
    private UserClient userClient;
    private TextField name = new TextField(NAME);
    private TextField surname = new TextField(SURNAME);
    private TextField email = new TextField(EMAIL);
    private TextField phoneNumber = new TextField(PHONENUM);
    private TextField password = new TextField(PASS);
    private Binder<UserDto> binder = new Binder<>();
    private UserDto loggedUserDto = new UserDto();

    private long userId;

    public UserAccountView(@Autowired UserClient userClient) {
        this.userClient = userClient;
        bindFields();
        Button updateUser = new Button("Update");
        binder.writeBeanIfValid(loggedUserDto);
        updateUser.addClickListener(e -> {
            binder.writeBeanIfValid(loggedUserDto);
            updateUser(loggedUserDto);
        });

        Button deleteUser = new Button("Delete");
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.add(updateUser, deleteUser);

        LabelFactory labelFactory = new LabelFactory();
        Label label = labelFactory.createLabel(LabelStyle.TAB_INFO, "You can modify your user data here. Please remember that in order to delete your account you need to end all your rentals first.");

        add(label, name, surname, email, phoneNumber, password, hLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, label, name, surname, email, phoneNumber, password, hLayout);

        deleteUser.addClickListener(e -> {
            if (userClient.doesUserHaveNoRents(loggedUserDto.getId())) {
                deleteUser(loggedUserDto);
                getUI().get().navigate("loginView");
            } else {
            }});
    }

    void refreshForUser(UserDto userDto) {
        loggedUserDto = userDto;
        userId = userDto.getId();
        binder.readBean(userDto);
    }

    private void updateUser(UserDto userDto) {
        userDto.setId(userId);
        userClient.updateUser(userDto);
    }

    private void deleteUser(UserDto userDto) {
        userClient.deleteUser(userDto.getId());
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
}
