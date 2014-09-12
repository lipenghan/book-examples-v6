package com.vaadin.book.examples.datamodel;

import java.util.ArrayList;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PropertyExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("propertyviewer".equals(context))
            propertyViewer(layout);
        else if ("propertyeditor".equals(context))
            propertyEditor(layout);
        else if ("objectproperty".equals(context))
            objectProperty(layout);
        else if ("implementation".equals(context))
            implementation(layout);
        else if ("valuechangenotifier".equals(context))
            valuechangenotifier(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.properties.basic
        final TextField tf = new TextField("Name");
        
        // Set the value
        tf.setValue("The text field value");
        
        // When the field value is edited by the user
        tf.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 8056435580338577416L;

            public void valueChange(ValueChangeEvent event) {
                // Get the value and cast it to proper type
                String value = (String) tf.getValue();
                
                // Do something with it
                layout.addComponent(new Label(value));
            }
        });
        // END-EXAMPLE: datamodel.properties.basic

        tf.setImmediate(true);
        layout.addComponent(tf);
    }
    
    void propertyViewer(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.properties.propertyviewer
        // Have a data model
        // TODO Update Book to use generics
        ObjectProperty<String> property =
            new ObjectProperty<String>("Hello");
        
        // Have a component that implements Viewer
        Label viewer = new Label();
        
        // Bind it to the data
        viewer.setPropertyDataSource(property);
        // END-EXAMPLE: datamodel.properties.propertyviewer

        layout.addComponent(viewer);
    }

    void propertyEditor(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.properties.propertyeditor
        // Have a data model
        // TODO Update Book to use generics
        ObjectProperty<String> property =
            new ObjectProperty<String>("Hello");
        
        // Have a component that implements Viewer
        TextField editor = new TextField("Edit Greeting");
        
        // Bind it to the data
        editor.setPropertyDataSource(property);
        
        // As TextField implements Property, we can bind
        // a viewer directly to it
        Label viewer = new Label();
        viewer.setPropertyDataSource(editor);

        // The value shown in the viewer is updated immediately
        // after editing the value in the editor (once it
        // loses the focus)
        editor.setImmediate(true);
        // END-EXAMPLE: datamodel.properties.propertyeditor

        layout.addComponent(editor);
        layout.addComponent(viewer);
    }

    void objectProperty(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.properties.objectproperty
        // Have a component that implements Viewer interface
        final TextField tf = new TextField("Name");
        
        // Have a data model with some data
        String myObject = "Hello";
        
        // Wrap it in an ObjectProperty
        // TODO Update Book to use generics
        ObjectProperty<String> property =
            new ObjectProperty<String>(myObject);
        
        // Bind the property to the component
        tf.setPropertyDataSource(property);
        // END-EXAMPLE: datamodel.properties.objectproperty

        layout.addComponent(tf);
    }

    void implementation(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.properties.implementation
        class MyProperty implements Property {
            private static final long serialVersionUID = -812703845827228913L;
            
            Integer data     = 0;
            boolean readOnly = false;
            
            // Return the data type of the model
            public Class<?> getType() {
                return Integer.class;
            }

            public Object getValue() {
                return data;
            }
            
            // Override the default implementation in Object
            @Override
            public String toString() {
                return Integer.toHexString(data);
            }

            public boolean isReadOnly() {
                return readOnly;
            }

            public void setReadOnly(boolean newStatus) {
                readOnly = newStatus;
            }

            public void setValue(Object newValue)
                    throws ReadOnlyException, ConversionException {
                if (readOnly)
                    throw new ReadOnlyException();
                    
                // Already the same type as the internal representation
                if (newValue instanceof Integer)
                    data = (Integer) newValue;
                
                // Conversion from a string is required
                else if (newValue instanceof String)
                    try {
                        data = Integer.parseInt((String) newValue, 16);
                    } catch (NumberFormatException e) {
                        throw new ConversionException();
                    }
                else
                     // Don't know how to convert any other types
                    throw new ConversionException();

                // Reverse decode the hexadecimal value
            }
        }
        
        // Instantiate the property and set its data
        MyProperty property = new MyProperty();
        property.setValue(42);
        
        // Bind it to a component
        final TextField tf = new TextField("Name", property);
        // END-EXAMPLE: datamodel.properties.implementation

        // Disallow editing the property
        //property.setReadOnly(true);
        
        // Also show in a viewer
        Label viewer = new Label();
        viewer.setPropertyDataSource(tf);
        tf.setImmediate(true);
        
        layout.addComponent(tf);
        layout.addComponent(viewer);
    }

    void valuechangenotifier(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.properties.valuechangenotifier
        // Have a property that can notify of its value changes
        class MyProperty implements Property, Property.ValueChangeNotifier {
            private static final long serialVersionUID = -7470501631252810551L;

            protected String name = null;
            boolean readOnly = false;
            ArrayList<ValueChangeListener> listeners =
                new ArrayList<ValueChangeListener>();

            // This isn't actually used but the toString()
            @Override
            public Object getValue() {
                return name;
            }

            // This is used
            @Override
            public String toString() {
                return name;
            }

            @Override
            public void setValue(Object newValue) throws ReadOnlyException,
                    ConversionException {
                // It is our responsibility to check read-only status
                if (readOnly)
                    throw new ReadOnlyException();
                
                // Actually set the value
                name = newValue.toString();

                // Fire the value change event
                final Property prop = this;
                ValueChangeEvent event = new ValueChangeEvent() {
                    private static final long serialVersionUID = -96224444035688467L;

                    @Override
                    public Property getProperty() {
                        return prop;
                    }
                };
                for (ValueChangeListener listener: listeners)
                    listener.valueChange(event);
            }

            @Override
            public Class<?> getType() {
                return MyProperty.class;
            }

            @Override
            public boolean isReadOnly() {
                return readOnly;
            }

            @Override
            public void setReadOnly(boolean newStatus) {
                readOnly = newStatus;
            }

            @Override
            public void addListener(ValueChangeListener listener) {
                listeners.add(listener);
            }

            @Override
            public void removeListener(ValueChangeListener listener) {
                listeners.remove(listener);
            }
        }

        // Create such a bean and bind it.
        final MyProperty myproperty = new MyProperty();
        TextField tf = new TextField("Editor Field", myproperty);
        tf.setNullRepresentation("");
        tf.setImmediate(true);
        
        // Modify the property directly
        Button modifier = new Button("Modify the property");
        modifier.addListener(new ClickListener() {
            private static final long serialVersionUID = -4673210619365691560L;

            @Override
            public void buttonClick(ClickEvent event) {
                myproperty.setValue("A modified value");
            }
        });
        
        // A read-only form that shows the data model state
        TextField viewer = new TextField("Viewer", myproperty);
        viewer.setNullRepresentation("");
        viewer.setReadOnly(true);
        // END-EXAMPLE: datamodel.properties.valuechangenotifier

        
        GridLayout glayout = new GridLayout(2,2);
        glayout.setSpacing(true);
        glayout.addComponent(tf);
        glayout.addComponent(viewer);
        glayout.addComponent(modifier);
        layout.addComponent(glayout);
    }
}
