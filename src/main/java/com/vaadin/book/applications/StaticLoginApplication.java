package com.vaadin.book.applications;

// BEGIN-EXAMPLE: advanced.applicationwindow.dynamic
import java.io.ByteArrayInputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.URIHandler;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

// BEGIN-EXAMPLE: advanced.urihandler.staticlogin
public class StaticLoginApplication extends Application
    implements HttpServletRequestListener, URIHandler {
    private static final long serialVersionUID = 8754563610384903614L;

    String     user        = null;
    boolean    loginFailed = false;

    public void init() {
        // The main window may not even be shown if
        // the login fails, but it has to be created
        // and set nevertheless.
        Window main = new Window("Main Hello window");
        setMainWindow(main);

        // Handle failed logins in an URI handler
        main.addURIHandler(this);
        
        // Init() is called even if the login failed, but there
        // is no need to continue in that case. The main window
        // and URI handler must be set before returning.
        if (loginFailed)
            return;

        // Successful login into the Vaadin application
        main.addComponent(new Label("Logged in as " + user));
        
        // Logout
        Button logout = new Button("Logout");
        logout.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 2168935782137403200L;

            public void buttonClick(ClickEvent event) {
                close(); // Close the application
            }
        });
        main.addComponent(logout);

        // After closing, redirect browser back to login
        setLogoutURL("/book-examples/static-login.html");
    }

    public void onRequestStart(HttpServletRequest request,
            HttpServletResponse response) {
        String requestUser = request.getParameter("username"); 
        if (requestUser != null) {
            user = requestUser;
            
            // Authenticate
            loginFailed = !"example".equals(requestUser);
        }
    }

    public void onRequestEnd(HttpServletRequest request,
            HttpServletResponse response) {
    }

    public DownloadStream handleURI(URL context,
                                    String relativeUri) {
        if (loginFailed) {
            loginFailed = false;
            String error = "<h1>Invalid username '" + user + 
            "'</h1><p>Login failed - this is dynamic HTML</p>"+
            "<p>Go <a href='/book-examples/static-login.html'>"+
            "back to the login page</a></p>";
            
            // Output the error as HTML
            ByteArrayInputStream istream =
                new ByteArrayInputStream(error.getBytes());
            return new DownloadStream(istream, "text/html",
                                      null);
        } else
            return null; // Show the main window normally
    }
}
// END-EXAMPLE: advanced.urihandler.staticlogin
