package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class UriHandlerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("staticlogin".equals(context))
            staticlogin(layout);
        else
            layout.addComponent(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    
    public final static String staticloginDescription =
        "<h1>External Login Page</h1>"+
        "<p>Having the login page in a Vaadin application causes some problems. " +
        "Password memory feature in browsers does not work with regular Vaadin " +
        "components. This is a common problem in Ajax applications. " +
        "The <b>LoginForm</b> component solves this by using an iframe, " +
        "but this is also awkward, for example because the size of the " +
        "frame is fixed. Moreover, the " +
        "session has to be kept open as expiration of the session in the login " +
        "page can be rather odd and even frustrating.</p>" +
        "<p>This example shows how to</p>" +
        "<ul>" +
        "  <li>handle authentication in a request listener and</li>" +
        "  <li>give possible feedback from failed login in a <b>URIHandler</b>.</li>" +
        "</ul>" +
        "<p>This way, the application is not initialized before the login " +
        "actually succeeds.</p>" +
        "<p>Please see also the source code of the " +
        "<tt><a href='/book-examples/static-login.html'>static-login.html</a></tt> page in your browser (<b>Ctrl+U</b>).</p>";
    
    void staticlogin(VerticalLayout layout) {
        // EXAMPLE-REF: advanced.urihandler.staticlogin com.vaadin.book.applications.StaticLoginApplication advanced.urihandler.staticlogin
        // BEGIN-EXAMPLE: advanced.urihandler.staticlogin
        // A button to open a new window
        Link openNew = new Link("Open Demo Application New Window",
                new ExternalResource("/book-examples/static-login.html"),
                "_blank", 500, 350, Window.BORDER_DEFAULT);
        layout.addComponent(openNew);
        // END-EXAMPLE: advanced.urihandler.staticlogin
    }
}
