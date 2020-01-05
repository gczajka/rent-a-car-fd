package com.frontend.rentacarfd.views.utils;

import com.vaadin.flow.component.html.Label;

public class LabelFactory {

    public final Label createLabel(LabelStyle style, String text) {
        switch (style) {
            case APP_TITLE:
                return createAppTitle(text);
            case SECTION_NAME:
                return createSectionName(text);
            case TAB_INFO:
                return createTabInfo(text);
            default:
                return null;
        }
    }

    private Label createAppTitle(String text) {
        Label label = new Label(text);
        label.getStyle().set("color", "#c9c9c9");
        label.getStyle().set("font-size", "100px");
        label.getStyle().set("text-align", "center");
        return label;
    }

    private Label createSectionName(String text) {
        Label label = new Label(text);
        label.getStyle().set("color", "#c9c9c9");
        label.getStyle().set("font-size", "30px");
        label.getStyle().set("text-align", "center");
        return label;
    }

    private Label createTabInfo(String text) {
        Label label = new Label(text);
        label.getStyle().set("color", "BLACK");
        label.getStyle().set("font-size", "15px");
        label.getStyle().set("text-align", "center");
//        label.getStyle().set("font-weight", "bold");
        return label;
    }
}
