package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ComboBoxExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("enumtype".equals(context))
            enumtype(layout);
        else if ("preselecting".equals(context))
            preselecting(layout);
        else if ("newitemsallowed".equals(context))
            newItemsAllowed(layout);
        else if ("nullselection".equals(context))
            nullSelection(layout);
        else if ("resetselection".equals(context))
            resetSelection(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.basic
        Form form = new Form();
        form.getLayout().setMargin(true);

        // Add some components
        form.setImmediate(true);

        ComboBox combobox = new ComboBox("Some caption");
        combobox.setInvalidAllowed(false);
        combobox.setNullSelectionAllowed(false);
        combobox.addItem("GBP");
        combobox.addItem("EUR");
        combobox.addItem("USD");
    
        form.addField("test",combobox);
        // END-EXAMPLE: component.select.combobox.basic
         
        layout.addComponent(form);
    }
    
    void preselecting(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.preselecting
        ComboBox combobox = new ComboBox("Some caption");
        
        // Add an item with a given ID
        combobox.addItem("GBP");
        
        // Select it using the item ID
        combobox.setValue("GBP");
        
        // Add some other items
        combobox.addItem("EUR");
        combobox.addItem("USD");
        // END-EXAMPLE: component.select.combobox.preselecting
         
        layout.addComponent(combobox);
    }

    // BEGIN-EXAMPLE: component.select.combobox.enumtype
    enum MyEnum {
        MERCURY("Mercury"),
        VENUS("Venus"),
        EARTH("Earth");
        
        private String name;
        private MyEnum (String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }

    void enumtype(VerticalLayout layout) {
        ComboBox combobox = new ComboBox("Some caption");
        combobox.setNullSelectionAllowed(false);
        
        combobox.addItem(MyEnum.MERCURY);
        combobox.addItem(MyEnum.VENUS);
        combobox.addItem(MyEnum.EARTH);
        
        // Preselect an item
        combobox.setValue(MyEnum.VENUS);

        layout.addComponent(combobox);
    }
    // END-EXAMPLE: component.select.combobox.enumtype

    void newItemsAllowed(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.newitemsallowed
        Form form = new Form();
        form.getLayout().setMargin(true);

        // Add some components
        form.setImmediate(true);

        ComboBox combobox = new ComboBox("Some caption");
        combobox.setInvalidAllowed(false);
        combobox.setNullSelectionAllowed(false);
        combobox.addItem("GBP");
        combobox.addItem("EUR");
        combobox.addItem("USD");
        
        // Allow adding new items
        combobox.setNewItemsAllowed(true);
    
        form.addField("test",combobox);
        // END-EXAMPLE: component.select.combobox.newitemsallowed
         
        layout.addComponent(form);
    }

    void nullSelection(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.nullselection
        ComboBox combobox = new ComboBox("My ComboBox");
        
        // Enable null selection
        combobox.setNullSelectionAllowed(true);
        
        // Add the item that marks 'null' value
        String nullitem = "-- none --";
        combobox.addItem(nullitem);
        
        // Designate it as the 'null' value marker
        combobox.setNullSelectionItemId(nullitem);
        
        // Add some other items
        for(int i=0; i<10; i++)
            combobox.addItem("Item " + i);
        // END-EXAMPLE: component.select.combobox.nullselection
        
        layout.addComponent(combobox);
        layout.setHeight("200px");
    }

    void resetSelection(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.combobox.resetselection
        // FORUM: http://vaadin.com/forum/-/message_boards/message/206665
        final ComboBox combobox = new ComboBox("My ComboBox");
        combobox.setInputPrompt("Select a value");
        layout.addComponent(combobox);
        
        // Enable null selection
        combobox.setNullSelectionAllowed(false);
        
        // Add some items
        for(int i=0; i<10; i++)
            combobox.addItem("Item " + i);

        // Previously selected value
        final Label value = new Label("Please select a value from the box");
        layout.addComponent(value);
        
        combobox.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -5188369735622627751L;

            public void valueChange(ValueChangeEvent event) {
                if (combobox.getValue() != null) {
                    value.setValue("Selected: " + (String) combobox.getValue());
                
                    // Reset the ComboBox
                    combobox.setValue(null);
                    combobox.setInputPrompt("Select another value");
                }
            }
        });
        combobox.setImmediate(true);
        // END-EXAMPLE: component.select.combobox.resetselection
    }
}
