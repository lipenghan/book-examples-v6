package com.vaadin.book.examples.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.vaadin.Application;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.*;
import com.vaadin.ui.Window.Notification;

// Use Portlet 2.0 API
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener;

public class PortletModeExample extends Application
                                implements PortletListener {
    private static final long serialVersionUID = 6878322382574777744L;
    
    Window                 mainWindow;
    ObjectProperty<String> data; // Data to view and edit
    VerticalLayout         viewContent   = new VerticalLayout();
    VerticalLayout         editContent   = new VerticalLayout();
    VerticalLayout         helpContent   = new VerticalLayout();
    
    @Override
    public void init() {
        mainWindow = new Window("Myportlet Application");
        setMainWindow(mainWindow);

        // Data model
        data = new ObjectProperty<String>(
                "<h1>Heading</h1> <p>Some example content</p>");

        // Prepare views for the three modes (view, edit, help)
        // Prepare View mode content
        Label viewText = new Label(data, Label.CONTENT_XHTML);
        viewContent.addComponent(viewText);

        // Prepare Edit mode content
        RichTextArea editText = new RichTextArea();
        editText.setCaption("Edit the value:");
        editText.setPropertyDataSource(data);
        editContent.addComponent(editText);

        // Prepare Help mode content
        Label helpText = new Label("<h1>Help</h1>" +
                                   "<p>This helps you!</p>",
                                   Label.CONTENT_XHTML);
        helpContent.addComponent(helpText);

        // Start in the view mode
        mainWindow.setContent(viewContent);

        // Check that we are running as a portlet.
        if (getContext() instanceof PortletApplicationContext2) {
            PortletApplicationContext2 ctx =
                (PortletApplicationContext2) getContext();

            // Add a custom listener to handle action and
            // render requests.
            ctx.addPortletListener(this, this);
        } else {
            mainWindow.showNotification("Not running in portal",
                               Notification.TYPE_ERROR_MESSAGE);
        }
    }

    // Dummy implementations for the irrelevant request types
    public void handleActionRequest(ActionRequest request,
                                    ActionResponse response, Window window) {
    }
    public void handleEventRequest(EventRequest request,
                                   EventResponse response, Window window) {
    }
    public void handleRenderRequest(RenderRequest request,
                                    RenderResponse response, Window window) {
    }

    public void handleResourceRequest(ResourceRequest request,
                                      ResourceResponse response, Window window) {
        // Switch the view according to the portlet mode
        if (request.getPortletMode() == PortletMode.EDIT)
            mainWindow.setContent(editContent);
        else if (request.getPortletMode() == PortletMode.VIEW)
            mainWindow.setContent(viewContent);
        else if (request.getPortletMode() == PortletMode.HELP)
            mainWindow.setContent(helpContent);
    }
}
