package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

public class NativeSelectExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.nativeselect.basic
        // Create the selection component
        final NativeSelect select = new NativeSelect("Native Selection");
        
        // Add some items
        select.addItem("Mercury");
        select.addItem("Venus");
        select.addItem("Earth");
        select.addItem("Mars");
        select.addItem("Jupiter");
        select.addItem("Saturn");
        select.addItem("Neptune");
        select.addItem("Uranus");
        
        // Set the width in "columns" as in TextField
        select.setColumns(10);
        
        select.setNullSelectionAllowed(false);
        // END-EXAMPLE: component.select.nativeselect.basic
        
        select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 2754712574220857944L;

            public void valueChange(ValueChangeEvent event) {
                getWindow().showNotification((String) select.getValue());
            }
        });
        select.setImmediate(true);

        layout.setHeight("200px");
        layout.addComponent(select);
    }

    Container createSelectData() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("caption", String.class, null);
        return container;
    }
}
