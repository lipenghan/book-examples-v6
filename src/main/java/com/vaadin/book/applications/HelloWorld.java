package com.vaadin.book.applications;

// BEGIN-EXAMPLE: intro.walkthrough.helloworld
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class HelloWorld extends com.vaadin.Application {
    private static final long serialVersionUID = 511085335415683713L;

    public void init() { 
        Window main = new Window("Hello window"); 
        main.addComponent(new Label("Hello World!"));
        setMainWindow(main);
    }
}
// END-EXAMPLE: intro.walkthrough.helloworld
