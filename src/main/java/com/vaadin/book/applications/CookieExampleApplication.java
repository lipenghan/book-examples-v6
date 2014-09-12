package com.vaadin.book.applications;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

// BEGIN-EXAMPLE: advanced.servletrequestlistener.cookies
public class CookieExampleApplication extends Application
       implements HttpServletRequestListener {
    private static final long serialVersionUID = -39894850208484L;
    
    String              username;
    HttpServletResponse response;
    TextField           newuser = null;
    Button              login   = null;
    Button              restart;
    Button              logout;

    @Override
	public void init() {
        Window main = new Window("URI Fragment Example");
        setMainWindow(main);
        setTheme("book-examples");
        
        if (username != null) {
            final Label hello = new Label("Logged in automatically.<br/>" +
                                          "Hello, " + username,
                                          Label.CONTENT_XHTML);
            main.addComponent(hello);
        } else {
            HorizontalLayout loginrow = new HorizontalLayout();
            main.addComponent(loginrow);
            
            newuser = new TextField ("Give a user name");
            login = new Button("Login");
            login.addListener(new Button.ClickListener() {
                private static final long serialVersionUID = 937474389379L;
    
                public void buttonClick(ClickEvent event) {
                    Object value = newuser.getValue(); 
                    if (value != null &&
                        ! "".equals((String) value)) {
                        username = (String) value;

                        Cookie cookie = new Cookie("username",
                                                   username);
                        // Use a fixed path
                        cookie.setPath("/book-examples");
                        cookie.setMaxAge(3600); // One hour
                        response.addCookie(cookie);
                        System.out.println("Set cookie.");

                        newuser.setEnabled(false);
                        login.setEnabled(false);
                        restart.setEnabled(true);
                        logout.setEnabled(true);
                    }
                }
            });
            loginrow.addComponent(newuser);
            loginrow.addComponent(login);
            loginrow.setComponentAlignment(login, Alignment.BOTTOM_LEFT);
        }
        
        HorizontalLayout logoutrow = new HorizontalLayout();
        main.addComponent(logoutrow);

        // Button to close and restart the application.
        // As the page redirects to the same page, the
        // application is reloaded in the browser, thus
        // logging in again if the cookie is set.
        restart = new Button("Restart");
        restart.setEnabled(username != null);
        logoutrow.addComponent(restart);
        restart.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 4420489757575L;

            public void buttonClick(ClickEvent event) {
                restart.getApplication().close();
            }
        });
        
        // Button to remove the cookie (and logout)
        logout = new Button("Logout");
        logout.setEnabled(username != null);
        logoutrow.addComponent(logout);
        logout.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 76482364287462L;

            public void buttonClick(ClickEvent event) {
                // Delete the cookie
                Cookie cookie = new Cookie("username", username);
                cookie.setPath("/book-examples");
                cookie.setMaxAge(0); // Delete
                response.addCookie(cookie);
                System.out.println("Deleted cookie.");

                // Close and restart
                logout.getApplication().close();
            }
        });
    }

    public void onRequestStart(HttpServletRequest request,
                               HttpServletResponse response) {
        if (username == null) {
            Cookie[] cookies = request.getCookies();
            for (int i=0; i<cookies.length; i++) {
                if ("username".equals(cookies[i].getName()))
                    // Log the user in automatically
                    username = cookies[i].getValue();
            }
        }
            
        // Store the reference to the response object for
        // using it in event listeners
        this.response = response;
    }

    public void onRequestEnd(HttpServletRequest request,
                             HttpServletResponse response) {
        // No need to do anything here
    }
}
//END-EXAMPLE: advanced.servletrequestlistener.cookies
