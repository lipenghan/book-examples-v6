package com.vaadin.book.examples.application;

import java.util.Iterator;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class SubWindowExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    VerticalLayout layout = new VerticalLayout();
    String context;
    
    public void init(String context) {
        this.context = context;
        
        setCompositionRoot(layout);
    }
    
    @Override
    public void attach() {
        super.attach();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("inheritance".equals(context))
            inheritance(layout);
        else if ("close".equals(context))
            close(layout);
        else if ("scrolling".equals(context))
            scrolling(layout);
        else if ("noscroll".equals(context))
            noscroll(layout);
        else if ("styling".equals(context))
            styling(layout);
    }
    
    @Override
    public void detach() {
        super.detach();
        
        // Close any open child windows
        for (Iterator<Window> w = getWindow().getChildWindows().iterator(); w.hasNext();)
            getWindow().removeWindow(w.next());
    }

    void basic(VerticalLayout layout) {
        Window mainWindow = getWindow();

        // BEGIN-EXAMPLE: application.child-windows.basic
        // Create a sub-window
        Window subWindow = new Window("Sub-window");
        
        // Put some components in it
        subWindow.addComponent(new Label("Meatball sub"));
        subWindow.addComponent(new Button("Awlright"));
        
        // Add it to the main window of the application
        mainWindow.addWindow(subWindow);
        // END-EXAMPLE: application.child-windows.basic
    }
    
    void inheritance(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.child-windows.inheritance
        // Define a sub-window by inheritance
        class MySub extends Window {
            private static final long serialVersionUID = 1L;

            public MySub() {
                super("Subs on Sale"); // Set window caption
                
                // Some content for the window
                addComponent(new Label("Hello there!"));

                // Trivial logic for closing the sub-window
                Button close = new Button("Close");
                close.addListener(new ClickListener() {
                    private static final long serialVersionUID = 1L;

                    public void buttonClick(ClickEvent event) {
                        // Parent of a sub-window is the
                        // application-level window.
                        getWindow().getParent().removeWindow(getWindow());
                    }
                });
                addComponent(close);
            }
        }
        
        // Some UI logic to open the sub-window
        final Button open = new Button("Open Sub-Window");
        open.addListener(new ClickListener() {
            private static final long serialVersionUID = 5249151941312555613L;

            public void buttonClick(ClickEvent event) {
                MySub sub = new MySub();
                
                // Add it to the main window, or more exactly the
                // current application-level window.
                getWindow().addWindow(sub);
            }
        });
        // END-EXAMPLE: application.child-windows.inheritance
        
        layout.addComponent(open);
    }

    static public String closeDescription =
        "<h1>Closing Sub-Windows</h1>\n"+
        "<p>The user can close a sub-window by clicking the close button in the upper right corner. "+
        "You can close a sub-window programmatically by removing it from the main window with <tt>removeWindow()</tt>. "+
        "In both cases, a <b>CloseEvent</b> is fired, which you can handle with a <b>CloseListener</b>.</p>";

    void close(final VerticalLayout layout) {
        // The sub-window will be attached to the main window
        final Window mainWindow = getWindow();
        
        // BEGIN-EXAMPLE: application.child-windows.close
        // Create a sub-window and add it to the main window
        Window sub = new Window("Close Me");
        mainWindow.addWindow(sub);
        
        // Center the sub-window in the application-level window
        sub.center();

        // Handle closing of the window by user
        sub.addListener(new CloseListener() {
            private static final long serialVersionUID = -4381415904461841881L;

            public void windowClose(CloseEvent e) {
                layout.addComponent(new Label("Sub-window of "+
                        mainWindow.getCaption() + " was closed"));
            }
        });
        // END-EXAMPLE: application.child-windows.close
    }

    void scrolling(VerticalLayout layout) {
        Window mainWindow = getWindow();

        // BEGIN-EXAMPLE: application.child-windows.scrolling
        // Create a sub-window of a fixed width
        Window subWindow = new Window("Scrolling Sub");
        subWindow.setModal(true);
        subWindow.setWidth("300px");
        subWindow.setHeight("300px");
        
        // Put some large content in it
        GridLayout g = new GridLayout(10,10);
        g.setWidth("500px");
        g.setHeight("500px");
        for (int i=0; i<g.getRows()*g.getColumns(); i++)
            g.addComponent(new Label("" + (i+1)));
        
        // Set as root layout of the sub-window
        subWindow.setContent(g);
        
        // Add it to the main window of the application
        mainWindow.addWindow(subWindow);
        // END-EXAMPLE: application.child-windows.scrolling
    }
    
    void noscroll(VerticalLayout layout) {
        Window mainWindow = getWindow();

        // BEGIN-EXAMPLE: application.child-windows.noscroll
        // Create a sub-window of a fixed width
        //Window subWindow = new Window("Scrolling Sub");
        Window subWindow = new Window("Scrolling Sub");
        subWindow.setModal(true);
        subWindow.setWidth("300px");
        subWindow.setHeight("300px");
        
        // Put some large content in it - scroll bars should
        // now appear
        GridLayout g = new GridLayout(10,10);
        g.setWidth("400px");
        g.setHeight("400px");
        for (int i=0; i<g.getRows()*g.getColumns(); i++)
            g.addComponent(new Label("" + (i+1)));
        
        // Set as root layout of the sub-window
        subWindow.setContent(g);
        
        // Prevent scrolling
        subWindow.setScrollable(false);
        // subWindow.setResizable(false);
        
        // Add it to the main window of the application
        mainWindow.addWindow(subWindow);
        // END-EXAMPLE: application.child-windows.noscroll
    }
    
    void styling(final VerticalLayout layout) {
        // The sub-window will be attached to the main window
        final Window mainWindow = getWindow();
        
        // BEGIN-EXAMPLE: application.child-windows.styling
        // Create a sub-window and add it to the main window
        Window sub = new Window("Life beneath the waves");
        mainWindow.addWindow(sub);
        sub.center();

        // For themeing just this sub-window
        sub.addStyleName("yellowsub");
        // END-EXAMPLE: application.child-windows.styling
    }
}
