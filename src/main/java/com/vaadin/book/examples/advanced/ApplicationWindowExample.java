package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ApplicationWindowExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("popup".equals(context))
            popup(layout);
        else if ("tab".equals(context))
            tab(layout);
        else if ("closing".equals(context))
            closing(layout);
        else if ("automatic".equals(context))
            automatic(layout);
        else if ("dynamic".equals(context))
            dynamic(layout);
        else
            layout.addComponent(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    
    void popup(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.applicationwindow.popup
        class MyPopup extends Window {
            private static final long serialVersionUID = -150820896413699296L;

            public MyPopup() {
                super("Popup Window");
                addComponent(new Label("I just popped up to say hi!"));
            }
        }
        
        Button button = new Button("Open Popup Window");
        button.addListener(new ClickListener() {
            private static final long serialVersionUID = -1716988000443865010L;

            @Override
            public void buttonClick(ClickEvent event) {
                MyPopup popup = new MyPopup();
                
                getApplication().addWindow(popup);

                // Open it as a popup window with no decorations
                getWindow().open(new ExternalResource(popup.getURL()),
                        "_blank", 300, 100,  // Width and height 
                        Window.BORDER_NONE); // No decorations
            }
        });
        // END-EXAMPLE: advanced.applicationwindow.popup
        layout.addComponent(button);
    }
    
    public final static String tabDescription =
            "<h1>Opening a New Tab</h1>" +
            "<p>Normally, the open() method in <b>Window</b> opens an entirely new browser window. This can happen " +
            "even when you have said in the browser settings (at least in Firefox) that new windows should be opened as tabs. " +
            "To work around the problem, you can use JavaScript to open the new tab.</p>" +
            "" +
            "<p>Note: the behaviour is a bug, which is reported in <a href='http://dev.vaadin.com/ticket/7842'>#7842</a>.</p>";
    
    void tab(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.applicationwindow.tab
        Button button = new Button("Open a New Tab");
        button.addListener(new ClickListener() {
            private static final long serialVersionUID = -1716988000443865010L;

            @Override
            public void buttonClick(ClickEvent event) {
                Window win = new Window("New Window");
                win.addComponent(new Label("Hello, I'm new here"));
                getApplication().addWindow(win);

                getWindow().executeJavaScript(
                    "window.open('" + win.getURL() + "', '_blank')");
            }
        });
        // END-EXAMPLE: advanced.applicationwindow.tab
        layout.addComponent(button);
    }

    void closing(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.applicationwindow.popup
        class MyPopup extends Window {
            private static final long serialVersionUID = -150820896413699296L;

            public MyPopup() {
                super("Popup Window");
                addComponent(new Label("I just popped up to say hi!"));
                
                final Window popup = this;
                addListener(new CloseListener() {
                    private static final long serialVersionUID = 541762446582830810L;

                    @Override
                    public void windowClose(CloseEvent e) {
                        layout.addComponent(new Label("Popup closed"));
                        getApplication().removeWindow(popup);
                    }
                });
            }
        }
        
        Button button = new Button("Open Popup Window");
        button.addListener(new ClickListener() {
            private static final long serialVersionUID = -1716988000443865010L;

            @Override
            public void buttonClick(ClickEvent event) {
                MyPopup popup = new MyPopup();
                popup.setName("mypopup");
                
                getApplication().addWindow(popup);

                // Open it as a popup window with no decorations
                getWindow().open(new ExternalResource(popup.getURL()),
                        "_blank", 300, 100,  // Width and height 
                        Window.BORDER_NONE); // No decorations
            }
        });
        
        // Poll to refresh the main window if the popup is closed
        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setIndeterminate(true);
        layout.addComponent(indicator);
        // END-EXAMPLE: advanced.applicationwindow.popup
        layout.addComponent(button);
    }
    
    public final static String automaticDescription =
        "<h1>Multiple Browser Windows for the Book Examples Application</h1>"+
        "<p>This simple code enables multiple application-level windows for the Book Examples application.</p>"+
        "<p><b>Note:</b> <i>The other window URLs <b>must not</b> have <tt>?restartApplication</tt> parameter.</i></p>";
    
    void automatic (VerticalLayout layout) {
        // EXAMPLE-REF: advanced.applicationwindow.automatic com.vaadin.book.BookExamplesApplication advanced.applicationwindow.automatic
        // BEGIN-EXAMPLE: advanced.applicationwindow.automatic
        layout.addComponent(new Label("The entire Book Examples application is the example. "+
                "Try opening other windows either by entering the URL or by clicking the link below."));
        
        // A button to open a new window
        Link openNew = new Link("Open New Window",
                new ExternalResource("/book-examples/book/"),
                "_blank", -1, -1, Window.BORDER_DEFAULT);
        layout.addComponent(openNew);
        // END-EXAMPLE: advanced.applicationwindow.automatic
        
        layout.setSpacing(true);
    }

    public final static String dynamicDescription =
        "<h1>Multiple Application Windows</h1>"+
        "<p>This simple code enables multiple application-level windows.</p>"+
        "<p><b>Note:</b> <i>The other window URLs <b>must not</b> have <tt>?restartApplication</tt> parameter.</i></p>";
    
    void dynamic (VerticalLayout layout) {
        // EXAMPLE-REF: advanced.applicationwindow.dynamic com.vaadin.book.applications.DynamicWindowApplication advanced.applicationwindow.dynamic
        // BEGIN-EXAMPLE: advanced.applicationwindow.dynamic
        // A button to open a new window
        Link openNew = new Link("Start the application",
                new ExternalResource("/book-examples/dynamicwindow/?restartApplication"),
                "_blank", 480, 100, Window.BORDER_DEFAULT);
        layout.addComponent(openNew);
        // END-EXAMPLE: advanced.applicationwindow.dynamic
        
        layout.setSpacing(true);
    }
}
