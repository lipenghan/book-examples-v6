package com.vaadin.book.examples.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.vaadin.Application;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

public class PortletCustomModeExample extends Application
    implements PortletListener {
    private static final long serialVersionUID = 6878322382574777744L;
    
    Window                 mainWindow;
    ObjectProperty<String> data;
    VerticalLayout         viewContent   = new VerticalLayout();
    VerticalLayout         editContent   = new VerticalLayout();
    VerticalLayout         helpContent   = new VerticalLayout();
    VerticalLayout         customContent = new VerticalLayout();
    PortletMode            customMode;
    
    @Override
	public void init() {
		mainWindow = new Window("Myportlet Application");
		setMainWindow(mainWindow);

		// Data model
		data = new ObjectProperty<String>(
		        "<h1>Heading</h1> <p>Some example content</p>");

		// View mode content
		Label viewText = new Label(data, Label.CONTENT_XHTML);
		viewContent.addComponent(viewText);
		
		// Edit mode content
		RichTextArea editText = new RichTextArea();
		editText.setCaption("Edit the value:");
		editText.setPropertyDataSource(data);
		editContent.addComponent(editText);

		// Help mode content
		Label helpText = new Label("<h1>Help</h1>" +
		                           "<p>This helps you.</p>",
		                           Label.CONTENT_XHTML);
		helpContent.addComponent(helpText);
		
		// Custom mode content
		Label customText = new Label("This is the custom mode");
		customContent.addComponent(customText);
		
		// Start in the view mode
		mainWindow.setContent(viewContent);
		
		// Define the custom mode and a button to switch to it
		customMode = new PortletMode("config");
		viewContent.addComponent(new Button("Custom Mode",
            new Button.ClickListener() {
                private static final long serialVersionUID = 7260317716523284153L;
                public void buttonClick(ClickEvent event) {
                    if (getContext() instanceof PortletApplicationContext2) {
                        PortletApplicationContext2 ctx = (PortletApplicationContext2) getContext();
                        try {
                            ctx.setPortletMode(mainWindow, customMode);
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (PortletModeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }));

		// Check that we are running as a portlet.
		if (getContext() instanceof PortletApplicationContext2) {
		    PortletApplicationContext2 ctx =
		            (PortletApplicationContext2) getContext();

		    // Add a custom listener to handle action and
		    // render requests.
		    ctx.addPortletListener(this, this);
		} else {
		}		
	}

    // Dummy implementations
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
        if (request.getPortletMode() == PortletMode.EDIT)
            mainWindow.setContent(editContent);
        else if (request.getPortletMode() == PortletMode.VIEW)
            mainWindow.setContent(viewContent);
        else if (request.getPortletMode() == PortletMode.HELP)
            mainWindow.setContent(helpContent);
        else if (request.getPortletMode() == customMode)
            mainWindow.setContent(customContent);
    }
}
