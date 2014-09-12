package com.vaadin.book.examples.application.calc;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * A simple calculator using Vaadin.
 * 
 */
public class CalcApplication extends Application implements ClickListener {
    private static final long serialVersionUID = 6529452791144362380L;

    // All variables are automatically stored in the session.
    private double current = 0.0;
    private double stored = 0.0;
    private char lastOperationRequested = 'C';

    // User interface components
    private final Label display = new Label("0.0");

    /*
     * Application.init is called once for each application. Here it creates the
     * UI and connects it to the business logic.
     */
    @Override
    public void init() {
        // Create the main layout for our application (4 columns, 5 rows)
        final GridLayout layout = new GridLayout(4, 5);

        /*
         * Create the main window for the application using the main layout. The
         * main window is shown when the application is starts.
         */
        setMainWindow(new Window("Calculator Application", layout));

        // Create a result label that over all 4 columns in the first row
        layout.addComponent(display, 0, 0, 3, 0);

        // The operations for the calculator in the order they appear on the
        // screen (left to right, top to bottom)
        String[] operations = new String[] { "7", "8", "9", "/", "4", "5", "6",
                "*", "1", "2", "3", "-", "0", "=", "C", "+" };

        for (String caption : operations) {

            // Create a button and use this application for event handling
            Button button = new Button(caption);
            button.addListener(this);

            // Add the button to our main layout
            layout.addComponent(button);
        }
    }

    // Event handler for button clicks. Called for all the buttons in the
    // application.
    public void buttonClick(ClickEvent event) {

        // Get the button that was clicked
        Button button = event.getButton();

        // Get the requested operation from the button caption
        char requestedOperation = button.getCaption().charAt(0);

        // Calculate the new value
        double newValue = calculate(requestedOperation);

        // Update the result label with the new value
        display.setValue(newValue);

    }

    // Calculator "business logic" implemented here to keep the example minimal
    private double calculate(char requestedOperation) {
        if ('0' <= requestedOperation && requestedOperation <= '9') {
            current = current * 10
                    + Double.parseDouble("" + requestedOperation);
            return current;
        }
        switch (lastOperationRequested) {
        case '+':
            stored += current;
            break;
        case '-':
            stored -= current;
            break;
        case '/':
            stored /= current;
            break;
        case '*':
            stored *= current;
            break;
        case 'C':
            stored = current;
            break;
        }
        lastOperationRequested = requestedOperation;
        current = 0.0;
        if (requestedOperation == 'C') {
            stored = 0.0;
        }
        return stored;
    }

}
