package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class FocusExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -83453485734975384L;

	public void init (String context) {
	    if ("focus".equals(context))
	        focusMethod();
	    else if ("tabindex".equals(context))
            focusMethod();
        else if ("focusevent".equals(context))
            focusEvent();
	}
	
	void focusMethod() {
		HorizontalLayout layout = new HorizontalLayout();

		// BEGIN-EXAMPLE: component.features.focusable.focus
		Form loginBox = new Form();
		loginBox.setCaption("Login");
		layout.addComponent(loginBox);

		// Create the first field which will be focused
		TextField username = new TextField("User name");
		loginBox.addField("username", username);
		
		// Set focus to the user name
		username.focus();
		
        TextField password = new TextField("Password");
        loginBox.addField("password", password);
		
		Button login = new Button("Login");
		loginBox.getFooter().addComponent(login);
		// END-EXAMPLE: component.features.focusable.focus

        // BEGIN-EXAMPLE: component.features.focusable.tabindex
		// An additional component which natural focus order would
		// be after the button.
        CheckBox remember = new CheckBox("Remember me");
        loginBox.getFooter().addComponent(remember);

        username.setTabIndex(1);
        password.setTabIndex(2);
        remember.setTabIndex(3); // Different than natural place 
        login.setTabIndex(4);
        // END-EXAMPLE: component.features.focusable.tabindex
        
        ((HorizontalLayout) loginBox.getFooter()).setComponentAlignment(remember, Alignment.MIDDLE_CENTER);
		
		setCompositionRoot(layout);
    }
	
	void focusEvent() {
        
        final HorizontalLayout layout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: component.features.focusable.focusevent
        // Have some data that is shared by the TextField and ComboBox
        final ObjectProperty<String> data =
            new ObjectProperty<String>("A choise");

        // Create a text field and bind it to the data
        final TextField textfield = new TextField("Edit This", data);
        textfield.setImmediate(true);
        layout.addComponent(textfield);

        // Create a replacement combo box; this could be created
        // later in the FocusListener just as well
        final ComboBox combobox = new ComboBox ("No, Edit This");
        combobox.addItem("A choise");
        combobox.addItem("My choise");
        combobox.addItem("Your choise");
        combobox.setPropertyDataSource(data);
        combobox.setImmediate(true);
        combobox.setNewItemsAllowed(true);

        // Change TextField to ComboBox when it gets focus
        textfield.addListener(new FocusListener() {
            private static final long serialVersionUID = 8721337946386845992L;

            public void focus(FocusEvent event) {
                layout.replaceComponent(textfield, combobox);
                combobox.focus();
            }
        });
	    
        // Change ComboBox back to TextField when it loses focus
        combobox.addListener(new BlurListener() {
            private static final long serialVersionUID = 7055180877355044203L;

            public void blur(BlurEvent event) {
                layout.replaceComponent(combobox, textfield);
            }
        });
        // END-EXAMPLE: component.features.focusable.focusevent
        
        setCompositionRoot(layout);
	}
}
