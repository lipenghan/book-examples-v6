package com.vaadin.book.examples.application;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class NotificationExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292555534521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("error".equals(context))
            error(layout);
        setCompositionRoot(layout);
    }

    void error(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.errors.notification.error
        // Have a component that fires click events
        final Button button = new Button("Be Bad");
        
        // Handle the events with an anonymous class
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                getWindow().showNotification("Error!",
                        "<br/>This is a serious error",
                        Notification.TYPE_ERROR_MESSAGE);
            }
        });
        // END-EXAMPLE: application.errors.notification.error

        layout.addComponent(button);
    }
}
