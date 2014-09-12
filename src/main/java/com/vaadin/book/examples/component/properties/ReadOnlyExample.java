package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ReadOnlyExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -1770451668233870037L;

	public void init (String context) {
        if ("simple".equals(context))
            simple();
        else if ("layouts".equals(context))
            layouts();
	    else if ("styling".equals(context))
	        styling();
	}
	
	void simple() {
		HorizontalLayout layout = new HorizontalLayout();

		// BEGIN-EXAMPLE: component.features.readonly.simple
		TextField readwrite = new TextField("Read-Write");
		readwrite.setValue("You can change this");
		readwrite.setReadOnly(false); // The default
		layout.addComponent(readwrite);
		
		TextField readonly = new TextField("Read-Only");
		readonly.setValue("You can't touch this!");
		readonly.setReadOnly(true);
		layout.addComponent(readonly);
		// END-EXAMPLE: component.features.readonly.simple
		
		readonly.setDescription("Tooltips work ok");
		
		setCompositionRoot(layout);
    }

    void layouts() {
        HorizontalLayout root = new HorizontalLayout();
        root.setSpacing(true);

        // BEGIN-EXAMPLE: component.features.readonly.layouts
        VerticalLayout layout = new VerticalLayout();
        layout.setCaption("VerticalLayout");
        layout.addComponent(new TextField("TextField"));
        layout.setReadOnly(true);
        
        Form form = new Form();
        form.setCaption("Form");
        form.addField("field", new TextField("Field"));
        form.setReadOnly(true);
        // END-EXAMPLE: component.features.readonly.layouts
        
        root.addComponent(layout);
        root.addComponent(form);
        
        setCompositionRoot(root);
    }

    void styling() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addStyleName("stylingexample");

        // BEGIN-EXAMPLE: component.features.readonly.styling
        TextField readonly = new TextField("Read-Only");
        readonly.setValue("Read-only value");
        readonly.setReadOnly(true);
        layout.addComponent(readonly);
        // END-EXAMPLE: component.features.readonly.styling
        
        setCompositionRoot(layout);
    }
}
