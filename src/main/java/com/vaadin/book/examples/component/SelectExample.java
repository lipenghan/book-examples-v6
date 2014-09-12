package com.vaadin.book.examples.component;

import java.io.Serializable;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;

public class SelectExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("multiselect".equals(context))
            multiselect(layout);
        else if ("givenitemid".equals(context))
            givenItemId(layout);
        else if ("generateditemid".equals(context))
            generatedItemId(layout);
        else if ("explicitdefaultsid".equals(context))
            excplicitDefaultsId(layout);
        else if ("id".equals(context))
            captionModeId(layout);
        else if ("property".equals(context))
            propertyModeExample(layout);
        else if ("icons".equals(context))
            icons(layout);
        else if ("hierarchical".equals(context))
            hierarchical(layout);
        
        setCompositionRoot(layout);
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.select.basic
        // Create the selection component
        Select select = new Select("My Select");
        
        // Add some items
        select.addItem("Io");
        select.addItem("Europa");
        select.addItem("Ganymedes");
        select.addItem("Callisto");
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);
        // END-EXAMPLE: component.select.select.basic
         
        layout.addComponent(select);
    }
    
    void icons(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.select.icons
        // Create the selection component
        Select select = new Select("Target to Destroy");
        
        // Add some items
        String planets[] = {"Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Neptune", "Uranus"};
        for (String planet: planets) {
            select.addItem(planet);
            select.setItemIcon(planet,
                new ThemeResource("img/planets/"+planet+"_small.png"));
        }
        
        select.select("Earth");
        select.setNullSelectionAllowed(false);
        // END-EXAMPLE: component.select.select.icons
         
        layout.addComponent(select);
    }

    void multiselect(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.select.basic
        // Create the selection component
        OptionGroup group = new OptionGroup("My Select");
        
        // Add some items
        group.addItem("Io");
        group.addItem("Europa");
        group.addItem("Ganymedes");
        group.addItem("Callisto");
        
        // Enable the multiple selection mode
        group.setMultiSelect(true);
        
        // User may not select a "null" item
        group.setNullSelectionAllowed(false);
        // END-EXAMPLE: component.select.select.basic
         
        layout.addComponent(group);
    }

    void givenItemId(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.databinding.adding.givenitemid
        // Create a selection component
        ComboBox select = new ComboBox("My ComboBox");
        
        // Add items with given item IDs
        select.addItem("Mercury");
        select.addItem("Venus");
        select.addItem("Earth");
        
        // Select an item using the item ID
        select.setValue("Earth");
        // END-EXAMPLE: component.select.databinding.adding.givenitemid
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);
         
        layout.addComponent(select);
    }

    void generatedItemId(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.databinding.adding.generateditemid
        // Create a selection component
        Select select = new Select("My Select");
        
        // Add an item with a generated ID
        Object itemId = select.addItem();
        select.setItemCaption(itemId, "The Sun");
        
        // Select the item
        select.setValue(itemId);
        // END-EXAMPLE: component.select.databinding.adding.generateditemid
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);
         
        layout.addComponent(select);
    }

    void excplicitDefaultsId(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.databinding.captions.explicitdefaultsid
        // Create a selection component
        Select select = new Select("Moons of Mars");
        select.setItemCaptionMode(
                Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
        
        // Use the item ID also as the caption of this item
        select.addItem(new Integer(1));
        
        // Set item caption for this item explicitly
        select.addItem(2); // same as "new Integer(2)"
        select.setItemCaption(2, "Deimos");
        // END-EXAMPLE: component.select.databinding.captions.explicitdefaultsid
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);
         
        layout.addComponent(select);
    }

    void captionModeId(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.select.databinding.captions.id
        Select select = new Select("Inner Planets");
        select.setItemCaptionMode(Select.ITEM_CAPTION_MODE_ID);
        
        // A class that implements toString()
        class PlanetId extends Object implements Serializable {
            private static final long serialVersionUID = -7452707902301121901L;

            String planetName;
            PlanetId (String name) {
                planetName = name;
            }
            public String toString () {
                return "The Planet " + planetName;
            }
        }

        // Use such objects as item identifiers
        String planets[] = {"Mercury", "Venus", "Earth", "Mars"};
        for (int i=0; i<planets.length; i++)
            select.addItem(new PlanetId(planets[i]));
        // END-EXAMPLE: component.select.databinding.captions.id
        
        // User may not select a "null" item
        select.setNullSelectionAllowed(false);
         
        layout.addComponent(select);
    }

    // BEGIN-EXAMPLE: component.select.databinding.captions.property
    /** A bean with a "name" property. */
    public class Planet implements Serializable {
        private static final long serialVersionUID = 7725549394908524264L;

        int    id;
        String name;
        
        public Planet(int id, String name) {
            this.id   = id;
            this.name = name;
        }
        
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    void propertyModeExample(VerticalLayout layout) {
        // Have a bean container to put the beans in
        BeanItemContainer<Planet> container =
            new BeanItemContainer<Planet>(Planet.class);

        // Put some example data in it
        container.addItem(new Planet(1, "Mercury"));
        container.addItem(new Planet(2, "Venus"));
        container.addItem(new Planet(3, "Earth"));
        container.addItem(new Planet(4, "Mars"));

        // Create a selection component bound to the container
        final Select select = new Select("Inner Planets", container);

        // Set the caption mode to read the caption directly
        // from the 'name' property of the bean
        select.setItemCaptionMode(
                Select.ITEM_CAPTION_MODE_PROPERTY);
        select.setItemCaptionPropertyId("name");

        // Handle selects
        select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 4200598703422532755L;

            public void valueChange(ValueChangeEvent event) {
                // Get the selected item
                Object itemId = event.getProperty().getValue();
                BeanItem<?> item = (BeanItem<?>) select.getItem(itemId);
                
                // Get the actual bean and use the data
                Planet planet = (Planet) item.getBean();
                getWindow().showNotification("Clicked planet #" +
                        planet.getId());
            }
        });
        select.setImmediate(true);
        select.setNullSelectionAllowed(false);
        // END-EXAMPLE: component.select.databinding.captions.property
        
        layout.addComponent(select);
    }
    
    void hierarchical(VerticalLayout layout) {
        NativeSelect select = new NativeSelect("Hierarchical Select");
        select.setContainerDataSource(TreeExample.createTreeContent());
        
        layout.addComponent(select);
    }
}
