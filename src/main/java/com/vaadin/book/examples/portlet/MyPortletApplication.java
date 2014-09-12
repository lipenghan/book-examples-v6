package com.vaadin.book.examples.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Window.Notification;

public class MyPortletApplication extends Application
    implements PortletListener {
    private static final long serialVersionUID = 6878322382574777744L;
    
    Label label;

    @Override
	public void init() {
		Window mainWindow = new Window("Myportlet Application");
		setMainWindow(mainWindow);
		
		label = new Label("Initialized()");
		mainWindow.addComponent(label);

		// Check that we are running as a portlet.
		if (getContext() instanceof PortletApplicationContext2) {
		    PortletApplicationContext2 ctx =
		            (PortletApplicationContext2) getContext();

		    // Add a custom listener to handle action and
		    // render requests.
		    ctx.addPortletListener(this, this);
			label.setValue("Running in a portal");
		} else {
		    getMainWindow().showNotification(
		            "Not initialized in a Portal!",
		            Notification.TYPE_ERROR_MESSAGE);
			label.setValue("Not running in a portal, but elsewhere");
		}		
	}

    public void handleActionRequest(ActionRequest request,
                                    ActionResponse response, Window window) {
        if (label != null)
            label.setValue("ActionRequest. Portlet mode: " + request.getPortletMode());
        System.out.println("ActionRequest");
        System.out.println("Portlet mode: " + request.getPortletMode());
    }

    public void handleEventRequest(EventRequest request,
                                   EventResponse response, Window window) {
        if (label != null)
            label.setValue("EventRequest. Portlet mode: " + request.getPortletMode());
        System.out.println("EventRequest");
        System.out.println("Portlet mode: " + request.getPortletMode());
    }

    public void handleRenderRequest(RenderRequest request,
                                    RenderResponse response, Window window) {
        if (label != null)
            label.setValue("RenderRequest. Portlet mode: " + request.getPortletMode());
        System.out.println("RenderRequest");
        System.out.println("Portlet mode: " + request.getPortletMode());
    }

    public void handleResourceRequest(ResourceRequest request,
                                      ResourceResponse response, Window window) {
        if (label != null)
            label.setValue("ResourceRequest. Portlet mode: "+ request.getPortletMode());
        System.out.println("ResourceRequest");
        System.out.println("Portlet mode: " + request.getPortletMode());
    }
}
