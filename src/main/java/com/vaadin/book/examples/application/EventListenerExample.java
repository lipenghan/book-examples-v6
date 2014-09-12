package com.vaadin.book.examples.application;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class EventListenerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292555534521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("classlistener".equals(context))
            classhandler();
        else if ("anonymous".equals(context))
            anonymous ();
        else if ("methodlistener".equals(context))
            methodlistener (layout);
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }

    // BEGIN-EXAMPLE: application.eventlistener.classlistener
    // Here we have a composite component that handles the
    // events of its sub-components (a single button).
    public class MyComposition extends CustomComponent
                               implements Button.ClickListener {
        private static final long serialVersionUID = 23482364238642324L;

        public MyComposition() {
            
            // Just a single component in this composition.
            Button button = new Button("Click Me!");

            // You could also give the listener (this) in the
            // Constructor.
            button.addListener(this);
            
            setCompositionRoot(button);
        }
        
        // The listener method implementation
        public void buttonClick(ClickEvent event) {
            getWindow().showNotification("Thank You!");
        }
    }
    // END-EXAMPLE: application.eventlistener.classlistener

    void classhandler() {
        setCompositionRoot(new MyComposition());
    }
    
    void anonymous() {
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: application.eventlistener.anonymous
        // Have a component that fires click events
        Button button = new Button("Click Me!");
        
        // Handle the events with an anonymous class
        button.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3138442059473438237L;

            public void buttonClick(ClickEvent event) {
                getWindow().showNotification("Thank You!");
            }
        });
        layout.addComponent(button);
        // END-EXAMPLE: application.eventlistener.anonymous

        setCompositionRoot(layout);
    }
    
    public static final String methodlistenerDescription = 
        "<h1>Using a Listener Method</h1>" +
        "<p>You can differentiate between different events of the same type by " +
        "using a listener method.</p>" +
        "<p>Some warnings:</p>" +
        "<ul>" +
        " <li>There is no compile-time syntax checking for the method names, " +
        "     so they are dangerous if you typo them or rename the methods later.</li>" +
        " <li>The listener methods and the class must be public " +
        "(local classes are therefore not allowed)</li>" +
        "</ul>";
    
    // BEGIN-EXAMPLE: application.eventlistener.methodlistener
    // Inner class is OK as long as it is public
    public class TheButtons extends CustomComponent {
        private static final long serialVersionUID = 3160722889741374224L;

        Button thebutton;
        Button secondbutton;

        public TheButtons() {
            thebutton = new Button ("Do not push this button");
            thebutton.addListener(Button.ClickEvent.class, this,
                                  "theButtonClick");
            
            secondbutton = new Button ("I am a button too");
            secondbutton.addListener(Button.ClickEvent.class, this,
                                     "secondButtonClick");

            Layout root = new HorizontalLayout(); 
            root.addComponent(thebutton);
            root.addComponent (secondbutton);
            setCompositionRoot(root);
        }
        
        public void theButtonClick (Button.ClickEvent event) {
            thebutton.setCaption ("Do not push this button again");
        }

        public void secondButtonClick (Button.ClickEvent event) {
            secondbutton.setCaption ("I am not a number!");
        }
    }
        
    public void methodlistener(VerticalLayout layout) {
        TheButtons buttons = new TheButtons();
        layout.addComponent(buttons);
        // END-EXAMPLE: application.eventlistener.methodlistener
    }
}
