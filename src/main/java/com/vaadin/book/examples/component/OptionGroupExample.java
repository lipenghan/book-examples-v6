package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

public class OptionGroupExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("icons".equals(context))
            icons(layout);
        else if ("disabling".equals(context))
            disabling(layout);
        else if ("styling".equals(context))
            styling(layout);
        else
            layout.addComponent(new Label("Invalid context " + context));
        setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.optiongroup.basic
        OptionGroup group = new OptionGroup("My Group");
        group.addItem("One");
        group.addItem("Two");
        group.addItem("Three");
        // END-EXAMPLE: component.select.optiongroup.basic

        layout.addComponent(group);
    }

    void icons(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.optiongroup.icons
        OptionGroup group = new OptionGroup("My Icons");
        group.addItem("Russian");
        group.addItem("Greek");
        group.addItem("Cultural");
        
        // Set the icons
        // WARNING: This doesn't work. See ticket #5608.
        group.setItemIcon("Russian",  new ThemeResource("img/smiley2-20px.png"));
        group.setItemIcon("Greek",    new ThemeResource("img/smiley2-20px.png"));
        group.setItemIcon("Cultural", new ThemeResource("img/smiley2-20px.png"));
        // END-EXAMPLE: component.select.optiongroup.icons

        layout.addComponent(group);
    }

    void disabling(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.optiongroup.disabling
        // Have an option group
        OptionGroup group = new OptionGroup("My Disabled Group");
        group.addItem("One");
        group.addItem("Two");
        group.addItem("Three");

        // Disable one item
        group.setItemEnabled("Two", false);
        // END-EXAMPLE: component.select.optiongroup.disabling
        
        layout.addComponent(group);
    }

    void styling(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.optiongroup.styling
        OptionGroup group = new OptionGroup("Horizontal Group");
        group.addItem("One");
        group.addItem("Two");
        group.addItem("Three");
        
        // Lay the items out horizontally
        group.addStyleName("horizontal");

        // You can also say this if you like:
        group.setSizeUndefined();
        // END-EXAMPLE: component.select.optiongroup.styling
        
        layout.setSizeUndefined();
        layout.addComponent(group);
    }
    
    Container createSelectData() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("caption", String.class, null);
        return container;
    }
}
