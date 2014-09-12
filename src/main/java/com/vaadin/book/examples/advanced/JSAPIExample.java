package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class JSAPIExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3205020480634478985L;
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("status".equals(context))
            status(layout);
        else if ("jsreception".equals(context))
            jsreception(layout);
                
        setCompositionRoot(layout);
    }
    
    public static final String statusDescription =
        "<h1>Setting Status Message</h1>"+
        "<p>Enter a message and observe the status bar of the browser.</p>"+
        "<p><b>Note</b>: Modification of status messages must be enabled in the browser. "+
        "For example in Firefox they are not, but must be set in "+
        "<b>Preferences \u2192 Content \u2192 Enable JavaScript \u2192 Advanced \u2192 Change status bar text</b>.</p>";
    
    void status (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.jsapi.status
        // FORUM: http://vaadin.com/forum/-/message_boards/message/250304
        final TextField msgField = new TextField("Status Message");
        layout.addComponent(msgField);
        
        Button set = new Button("Set Status");
        set.addListener(new ClickListener() {
            private static final long serialVersionUID = 4941611508948832738L;

            public void buttonClick(ClickEvent event) {
                String message = (String) msgField.getValue();
                
                // Quote single quotes
                message = message.replace("'", "\\'");

                getWindow().executeJavaScript("window.status = '" +
                                              message + "';");
            }
        });
        layout.addComponent(set);
        // END-EXAMPLE: advanced.jsapi.status

        set.setClickShortcut(KeyCode.ENTER);
        set.addStyleName("primary");
    }

    public static final String jsreceptionDescription =
            "<h1>Receiving JavaScript Input from Client-Side</h1>"+
            "<p></p>";
        
    void jsreception (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.jsapi.jsreception
        // FORUM: https://vaadin.com/forum/-/message_boards/view_message/813087
        TextField value = new TextField("Value");
        value.setDebugId("jsvalue");
        value.addListener(new ValueChangeListener() {
            private static final long serialVersionUID = -374811385861034926L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                getWindow().showNotification("Value: " + event.getProperty().getValue());
            }
        });
        value.setImmediate(true);
        layout.addComponent(value);

        Button execute = new Button("Execute");
        execute.addListener(new ClickListener() {
            private static final long serialVersionUID = 4941611508948832738L;

            public void buttonClick(ClickEvent event) {
                getWindow().executeJavaScript(
                    "document.getElementById('jsvalue').focus(); " +
                    "document.getElementById('jsvalue').value = " +
                    "    new Date().toISOString(); " +
                    "document.getElementById('jsvalue').blur(); ");
            }
        });
        layout.addComponent(execute);
        // END-EXAMPLE: advanced.jsapi.jsreception
    }
}
