package com.vaadin.book.examples.application;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ErrorIndicatorExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292555534521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("setting".equals(context))
            setting(layout);
        else if ("clearing".equals(context))
            clearing(layout);
        else if ("form".equals(context))
            form(layout);
        setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.errors.error-indicator.basic
        // Have a component that fires click events
        final Button button = new Button("Click Me!");
        
        // Handle the events with an anonymous class
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                // This causes a null-pointer exception
                ((String)null).length();
            }
        });
        // END-EXAMPLE: application.errors.error-indicator.basic

        layout.addComponent(button);
    }
    
    void setting(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.errors.error-indicator.setting
        // Have a component that fires click events
        final Button button = new Button("Click Me!");
        
        // Handle the events with an anonymous class
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                button.setComponentError(new UserError("Bad click"));
            }
        });
        // END-EXAMPLE: application.errors.error-indicator.setting

        layout.addComponent(button);
    }
    
    void clearing(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.errors.error-indicator.clearing
        // Have a component that fires click events
        final Button button = new Button("Click Me!");
        
        // Handle the events with an anonymous class
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -8726842993975741837L;

            public void buttonClick(ClickEvent event) {
                // This causes a null-pointer exception
                ((String)null).length();
            }
        });

        // Another button to clear the error
        final Button clear = new Button("Clear Error");
        clear.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -1157669531115265314L;

            public void buttonClick(ClickEvent event) {
                button.setComponentError(null); // Clear error
            }
        });
        // END-EXAMPLE: application.errors.error-indicator.clearing

        layout.addComponent(button);
        layout.addComponent(clear);
    }

    void form(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.errors.error-indicator.form
        final Form form = new Form();
        form.addField("name", new TextField("Name"));
        
        // Have a component that fires click events
        final Button button = new Button("Click Me!");
        form.getFooter().addComponent(button);
        
        // When clicked, set the form error
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                form.setComponentError(new UserError("Bad click"));
            }
        });
        // END-EXAMPLE: application.errors.error-indicator.form

        layout.addComponent(form);
    }
    

}
