package com.vaadin.book.examples.themes;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ThemeTricksExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 6580750126724601997L;
    
    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("pointertypes".equals(context))
            pointerTypes(layout);
        else
            layout.addComponent(new Label("Invalid context "+ context));
            
        setCompositionRoot(layout);
    }
    
    void pointerTypes(VerticalLayout layout) {
        // BEGIN-EXAMPLE: themes.misc.pointertypes
        final ComboBox select = new ComboBox("Pointer Type");
        select.setInputPrompt("Select a Type");

        // List all the pointer types in CSS
        String pointers[] = {"auto", "crosshair", "default",
                "help", "move", "pointer", "progress",
                "text", "wait", "inherit"};
        for (int i=0; i<pointers.length; i++)
            select.addItem(pointers[i]);
        
        select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 1716727013301055957L;

            public void valueChange(ValueChangeEvent event) {
                // Set the style for the root component
                getWindow().setStyleName("select-" +
                        (String) select.getValue());
            }
        });
        select.setImmediate(true);
        // END-EXAMPLE: themes.misc.pointertypes
        select.setNullSelectionAllowed(false);

        layout.addComponent(select);
    }
}
