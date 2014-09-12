package com.vaadin.book.applications;

// BEGIN-EXAMPLE: testbench.application
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class ApplicationToBeTested extends Application {
    private static final long serialVersionUID = 511085335415683713L;

    public void init() { 
        final Window main = new Window("Test window");
        setMainWindow(main);
        
        // Create a button
        Button button = new Button("Push Me!");
        
        // Optional: give the button a unique debug ID
        button.setDebugId("main.button");
        
        // Set the tooltip
        button.setDescription("This is a tip");

        // Do something when the button is clicked
        button.addListener(new ClickListener() {
            private static final long serialVersionUID = -8358743723903182533L;

            @Override
            public void buttonClick(ClickEvent event) {
                // This label will not have a set debug ID
                main.addComponent(new Label("Thanks!"));
            }
        });
        main.addComponent(button);
    }
}
// END-EXAMPLE: testbench.application
