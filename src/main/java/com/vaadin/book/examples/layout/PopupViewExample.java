package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.book.examples.component.TableExample;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PopupViewExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 9106115858126838561L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        addStyleName("expandratioexample");

        if ("basic".equals(context))
            ;//basic(layout);
        else if ("composition".equals(context))
            composition(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    public static String basicDescription =
        "";
    void basic(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: layout.popupview.basic
        // END-EXAMPLE: layout.popupview.basic
    }
    
    void composition(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.popupview.composition
        TextField tf = new TextField();
        layout.addComponent(tf);
        
        Table table = new Table(null, TableExample.generateContent());
        table.setSelectable(true);
        
        final PopupView popup = new PopupView("Small", table);
        layout.addComponent(popup);
        
        tf.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -7331971790077682727L;

            public void valueChange(ValueChangeEvent event) {
                popup.setPopupVisible(true);
            }
        });
        tf.setImmediate(true);
        // END-EXAMPLE: layout.popupview.composition
    }
}
