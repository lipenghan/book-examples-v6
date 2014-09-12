package com.vaadin.book.applications;

// BEGIN-EXAMPLE: advanced.applicationwindow.dynamic
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

public class DynamicWindowApplication extends com.vaadin.Application {
    private static final long serialVersionUID = 1340937891969118454L;

    public void init() {
        // Create just the main window initially 
        Window main = new Window("Main Hello window");
        main.addComponent(new Label("This is the main window."));
        initWindow(main);
        setMainWindow(main);
    }
    
    // Puts some content in the window
    void initWindow(final Window window) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        window.setContent(layout);
        
        // A button to open a new window
        Link openNew = new Link("Open New Window",
                new ExternalResource("/book-examples/dynamicwindow/"),
                "_blank", 480, 100, Window.BORDER_DEFAULT);
        window.addComponent(openNew);

        // Demonstrate closing pop-up windows
        Button close = new Button("Close This Window");
        close.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -86208708122352874L;

            public void buttonClick(ClickEvent event) {
                window.executeJavaScript("close();");
            }
        });
        window.addComponent(close);
    }

    int windowCount = 0;
    
    @Override
    public Window getWindow(String name) {
        // See if the window already exists in the application
        Window window = super.getWindow(name);
        
        // If a dynamically created window is requested, but
        // it does not exist yet, create it.
        if (window == null) {
            // Create the new window object
            window = new Window("Dynamic Window " +
                                ++windowCount);

            // As the window did not exist, the name parameter is
            // an automatically generated name for a new window.
            window.setName(name);

            // Add it to the application as a regular
            // application-level window. This must be done before
            // calling open, which requires that the window
            // is attached to the application.
            addWindow(window);

            // Open it with the proper URL that includes the
            // automatically generated window name
            window.open(new ExternalResource(window.getURL()));

            // Fill the window with stuff
            initWindow(window);
        }

        return window;
    }
}
// END-EXAMPLE: advanced.applicationwindow.dynamic
