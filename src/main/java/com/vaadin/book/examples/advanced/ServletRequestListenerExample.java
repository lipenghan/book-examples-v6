package com.vaadin.book.examples.advanced;

import com.vaadin.book.BookExamplesApplication;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class ServletRequestListenerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 92746549209L;

    public void init(String context) {
        String application;
        if ("introduction".equals(context))
            application = "servletrequestexample";
        else 
            application = "cookies";

        // EXAMPLE-REF: advanced.servletrequestlistener.cookies com.vaadin.book.applications.CookieExampleApplication advanced.servletrequestlistener.cookies 

        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: advanced.servletrequestlistener.introduction
        // EXAMPLE-REF: com.vaadin.book.applications.HttpServletRequestApplication 
        // EXAMPLE-REF: com.vaadin.book.BookExamplesApplication 
        Panel panel = new Panel("Embedded application:");

        // Embed the actual Servlet request demo application
        // in this frame.
        final Embedded embedded = new Embedded();
        embedded.setWidth("500px");
        embedded.setHeight("200px");
        embedded.setSource(new ExternalResource(
            "/book-examples/" + application + "?restartApplication"));
        embedded.setType(Embedded.TYPE_BROWSER);
        panel.addComponent(embedded);

        // Demonstrate communication between two Vaadin applications.
        Button action = new Button("Action in application 1");
        action.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 3275155912125288223L;

            int clickCount = 0; // Some data to send
            
            public void buttonClick(ClickEvent event) {
                // The communicated data is stored in the application
                // object where the other application can receive it
                // more easily.
                ((BookExamplesApplication) getApplication()).
                        setClicks(++clickCount);
            }
        });
        // END-EXAMPLE: advanced.servletrequestlistener.introduction
        ((VerticalLayout)panel.getContent()).setMargin(false);
        panel.setSizeUndefined();
        layout.addComponent(panel);
        layout.addComponent(action);
        
        setCompositionRoot(layout);
    }
    
    public static final String introductionDescription = 
        "<h1>Listening for Server Requests</h1>"+
        "<p>This example demonstrates multiple operations:</p>"+
        "<ul>"+
        " <li>How to handle request starts and ends with a <b>HttpServletRequestListener</b></li>"+
        " <li>How to communicate values from a request listener to the application</li>"+
        " <li>How to communicate between two Vaadin applications in the same session</li>"+
        " <li>How to communicate between two servlets in the same context using context attributes</li>"+
        "</ul>"+
        "<p>Notice that the communications are not synchronous: the data is received in the other application on the next request.</p>";
}
