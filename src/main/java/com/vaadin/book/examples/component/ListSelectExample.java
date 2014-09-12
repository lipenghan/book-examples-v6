package com.vaadin.book.examples.component;

import java.util.Arrays;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

public class ListSelectExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("multiselect".equals(context))
            multiselect(layout);
        else if ("height".equals(context))
            height(layout);
        setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.listselect.basic
        // Create the selection component
        ListSelect select = new ListSelect("My Selection");
        
        // Add some items
        select.addItem("Mercury");
        select.addItem("Venus");
        select.addItem("Earth");
        select.addItem("Mars");
        select.addItem("Jupiter");
        select.addItem("Saturn");
        select.addItem("Neptune");
        select.addItem("Uranus");
        
        // Show 5 items and a scrollbar if there are more
        select.setRows(5);

        select.setNullSelectionAllowed(false);
        // END-EXAMPLE: component.select.listselect.basic
         
        layout.addComponent(select);
    }
    
    public static final String multiselectDescription =
        "<h1>Multiple Selection Mode</h1>"+
        "<p>Ctrl+click items to select multiple items.</p>";

    void multiselect(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.listselect.multiselect
        // Create the selection component
        final ListSelect select = new ListSelect("My Selection");
        
        // Add some items
        for (String planet: new String[]{"Mercury", "Venus",
                "Earth", "Mars", "Jupiter", "Saturn", "Uranus",
                "Neptune"})
            select.addItem(planet);
        
        // Multiple selection mode
        select.setMultiSelect(true);
        
        // Must select at least one item
        select.setNullSelectionAllowed(false);

        // Preselect some items 
        select.setValue(Arrays.asList("Venus"));
        layout.addComponent(new Label(
                select.getValue().toString()));

        // Show all items
        select.setRows(select.size());
        
        // Feedback on value changes
        select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 4777915807221505438L;

            public void valueChange(ValueChangeEvent event) {
                layout.addComponent(new Label(
                        select.getValue().toString()));
            }
        });
        select.setImmediate(true);
        // END-EXAMPLE: component.select.listselect.multiselect
         
        layout.addComponent(select);
        //final Label value = new Label(select.getValue().toString());
        //layout.addComponent(value);
        //layout.setSpacing(true);
    }

    void height(VerticalLayout layout) {
        ListSelect select = new ListSelect();
        select.setContainerDataSource(createSelectData());
    }
    
    Container createSelectData() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("caption", String.class, null);
        return container;
    }
}
