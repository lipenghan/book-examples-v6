package com.vaadin.book.examples.component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class CustomComponentExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -2893838661604268626L;
    
    public void init (String context) {
        if ("basic".equals(context))
            basic();
        else if ("joining".equals(context))
            joining();
        else if ("customfield".equals(context))
            customFieldExample();
        else
            setCompositionRoot(new Label("Invalid context"));
    }
    
    void basic() {
        VerticalLayout layout = new VerticalLayout();
        
        MyComposite mycomposite = new MyComposite("Hello");
        layout.addComponent(mycomposite);
        
        setCompositionRoot(layout);
    }
    
    // BEGIN-EXAMPLE: component.customcomponent.basic
    class MyComposite extends CustomComponent {
        private static final long serialVersionUID = 6404759983019488775L;
        
        public MyComposite(String message) {
            // A layout structure used for composition
            Panel panel = new Panel("My Custom Component");
            panel.setContent(new VerticalLayout());
            
            // Compose from multiple components
            Label label = new Label(message);
            label.setSizeUndefined(); // Shrink
            panel.addComponent(label);
            panel.addComponent(new Button("Ok"));

            // Set the size as undefined at all levels
            panel.getContent().setSizeUndefined();
            panel.setSizeUndefined();
            setSizeUndefined();

            // The composition root MUST be set
            setCompositionRoot(panel);
        }
    }
    // END-EXAMPLE: component.customcomponent.basic

    // BEGIN-EXAMPLE: component.customcomponent.joining
    /** A Button + Select compoment. */
    class SplitButton extends CustomComponent {
        private static final long serialVersionUID = -543689535974760204L;
        
        Button button;
        Select select;

        public SplitButton(String caption, Container dataSource) {
            addStyleName("splitbutton");
            
            // HorizontalLayout layout = new HorizontalLayout();
            CssLayout layout = new CssLayout();

            // Create the Button part on the left
            button = new Button(caption);
            layout.addComponent(button);
            
            // Create the Select part on the right
            select = new Select();
            select.setNullSelectionAllowed(false);
            select.setWidth("26px"); // Truncate to get only the button
            select.setContainerDataSource(dataSource);
            select.setImmediate(true); // Immediate by default
            layout.addComponent(select);
            
            setCompositionRoot(layout);
        }
        
        /* Forward various methods to the proper subcomponent. */
        public void addListener(Button.ClickListener listener) {
            button.addListener(listener);
        }
        
        public void addListener(Property.ValueChangeListener listener) {
            select.addListener(listener);
        }
        
        public void setIcon(Resource icon) {
            button.setIcon(icon);
        }

        public void setCaption(String caption) {
            button.setCaption(caption);
        }
        
        public void setContainerDataSource(Container newDataSource) {
            select.setContainerDataSource(newDataSource);
        }
    }

    void joining() {
        VerticalLayout layout = new VerticalLayout();
        
        // Items for the drop-down menu
        IndexedContainer container = new IndexedContainer();
        String items[] = new String[] {"Hard", "Harder", "Even harder"};
        for (String item: items)
            container.addItem(item);
        
        SplitButton splitbutton = new SplitButton("Kick me", container);
        
        // Handle clicks in the Button part
        splitbutton.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 7360678548310920066L;

            public void buttonClick(ClickEvent event) {
                getWindow().showNotification("Aaaaagh!");
            }
        });

        // Handle selections in the drop-down list
        splitbutton.addListener(new ValueChangeListener() {
            private static final long serialVersionUID = 458979472699458342L;

            public void valueChange(ValueChangeEvent event) {
                getWindow().showNotification((String) event.getProperty().getValue());
            }
        });
        
        layout.addComponent(splitbutton);
        // END-EXAMPLE: component.customcomponent.joining
        
        setCompositionRoot(layout);
    }

    // BEGIN-EXAMPLE: component.customcomponent.customfield
    class QuickCalendar extends CustomField {
        private static final long serialVersionUID = -1184991761711858405L;
        
        NativeSelect year; 
        NativeSelect month;
        InlineDateField df; 
        
        public QuickCalendar(Date date) {
            FormLayout layout = new FormLayout();
            
            // Year box
            year = new NativeSelect("Year");
            for (int i=1900; i<=2010; i++)
                year.addItem(new Integer(i));
            year.setNullSelectionAllowed(false);
            layout.addComponent(year);

            // Month box
            month = new NativeSelect("Month");
            for (int i=1; i<=12; i++)
                month.addItem(new Integer(i));
            month.setNullSelectionAllowed(false);
            layout.addComponent(month);
         
            // Calendar box
            df = new InlineDateField("Day");
            df.setValue(date);
            df.setResolution(DateField.RESOLUTION_DAY);
            df.addStyleName("no-year-or-month");
            df.setImmediate(true);
            layout.addComponent(df);

            // Date in text format
            final Label value = new Label();
            value.setCaption("Date");
            value.setPropertyDataSource(df);
            layout.addComponent(value);

            // Set the initial year and month
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            year.select(new Integer(cal.get(Calendar.YEAR)));
            month.select(new Integer(cal.get(Calendar.MONTH) + 1));
            
            year.addListener(new ValueChangeListener() {
                private static final long serialVersionUID = -524636182634311831L;

                public void valueChange(Property.ValueChangeEvent event) {
                    int newyear = (Integer) year.getValue();
                    Date current = (Date) df.getValue();
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(current);
                    cal.set(newyear,
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DATE));
                    df.setValue(cal.getTime());
                }
            });
            year.setImmediate(true);

            month.addListener(new ValueChangeListener() {
                private static final long serialVersionUID = -8778775984954765735L;

                public void valueChange(Property.ValueChangeEvent event) {
                    int newmonth = (Integer) month.getValue();
                    Date current = (Date) df.getValue();
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(current);
                    cal.set(cal.get(Calendar.YEAR),
                            newmonth - 1,
                            cal.get(Calendar.DATE));
                    df.setValue(cal.getTime());
                    
                    // TODO Changing to a month with less days than
                    // previous month can cause date to wrap to next
                    // month, which leads to invalid month selection.
                }
            });
            month.setImmediate(true);
            
            setCompositionRoot(layout);
        }

        @Override
        public Class<?> getType() {
            return Date.class;
        }
        
        @Override
        public void focus() {
            year.focus();
        }

    }
    
    void customFieldExample() {
        VerticalLayout layout = new VerticalLayout();
        
        QuickCalendar calendar = new QuickCalendar(new Date());
        calendar.focus();
        layout.addComponent(calendar);
        // END-EXAMPLE: component.customcomponent.customfield
        
        setCompositionRoot(layout);
    }
}
