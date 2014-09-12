package com.vaadin.book.applications;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.book.BookExamplesApplication;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;

// BEGIN-EXAMPLE: advanced.servletrequestlistener.introduction
public class HttpServletRequestApplication extends Application
       implements HttpServletRequestListener {
    private static final long serialVersionUID = -278347984723847L;

    int   requestCount  = 0;
    Label starts        = null;
    Label ends          = null;
    Label contextName   = null;
    Label vaadinClicks  = null;
    Label servletClicks = null;
    
    @Override
	public void init() {
        System.out.println("  Application.init() called.");
        
        Window main = new Window("Servlet Request Example");
        setMainWindow(main);
        setTheme("book-examples");
                
        // Does nothing but causes a request
        Button button = new Button ("Make a request");
        main.addComponent(button);
        
        // Gives some feedback about the requests
        main.addComponent(starts = new Label("Not started"));
        main.addComponent(ends   = new Label("Not ended"));
        main.addComponent(contextName = new Label());
        main.addComponent(vaadinClicks = new Label("No clicks yet"));
        main.addComponent(servletClicks = new Label("No servlet comm yet"));
    }

    public void onRequestStart(HttpServletRequest request,
                               HttpServletResponse response) {

        // The init() is called after the first request,
        // so the UI objects can be uninitialized.
        requestCount++;
        if (starts != null)
            starts.setValue("Start-of-Requests: " + requestCount);
        
        // Get some Servlet info
        if (contextName != null)
            contextName.setValue(request.getSession().
                    getServletContext().getServletContextName());

        // Get data from another Vaadin application
        if (vaadinClicks != null)
            for (Application a: getContext().getApplications())
                if (a instanceof BookExamplesApplication) {
                    BookExamplesApplication examples = 
                        (BookExamplesApplication) a;
                    vaadinClicks.setValue("Other app: " + examples.getClicks());
                }
        
        // Get data from Servlet context
        if (servletClicks != null) {
            Object attribute = request.getSession()
                    .getServletContext().getAttribute("otherclicks");
            String other = attribute != null? (String) attribute : "";
            servletClicks.setValue("Context attribute: " + other);
        }
        
        System.out.println("[Start of request");
        System.out.println(" Query string: " +
                           request.getQueryString());
        System.out.println(" Path: " +
                           request.getPathTranslated());
    }

    public void onRequestEnd(HttpServletRequest request,
                             HttpServletResponse response) {
        // Count end-of-requests
        ends.setValue("End-of-Requests: " + requestCount);

        // Set data in the Servlet context 
        request.getSession().getServletContext()
                .setAttribute("otherclicks",
                              "Hello from " + requestCount);       
        
        System.out.println(" End of request]");
    }
}
// END-EXAMPLE: advanced.servletrequestlistener.introduction
