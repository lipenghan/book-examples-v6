package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class OrderedLayoutExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 5602882631420102318L;

    public void init(String context) {
        if ("basic".equals(context))
            basic();
        else if ("sizing-undefined-defining".equals(context))
            sizing_undefined_defining();
        else
            setCompositionRoot(new Label("Error"));
    }
    
    void basic() {
        // BEGIN-EXAMPLE: layout.orderedlayout.basic
        // BOOK: layout.components.orderedlayout
        VerticalLayout vertical = new VerticalLayout();
        vertical.addComponent(new TextField("Name"));
        vertical.addComponent(new TextField("Street address"));
        vertical.addComponent(new TextField("Postal code"));

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.addComponent(new TextField("Name"));
        horizontal.addComponent(new TextField("Street address"));
        horizontal.addComponent(new TextField("Postal code"));
        // END-EXAMPLE: layout.orderedlayout.basic
        
        VerticalLayout root = new VerticalLayout();
        root.addComponent(vertical);
        root.addComponent(horizontal);
        root.setSpacing(true);

        setCompositionRoot(root);
    }
    
    public static final String sizing_undefined_definingDescription =
        "<h1>Defining the Width of an VerticalLayout by Contained Component</h1>" +
        "<p>Normally, you wouldn't be able to put components with percentual size "+
        "in a layout with undefined size. In <b>VerticalLayout</b> and <b>HorizontalLayout</b>, "+
        "you can use a contained component to "+
        "define the size in the secondary direction of the layout, and then use "+
        "that size for relative sizing of other contained components.</p>"+
        "<p>Here, we define the size by a contained component with undefined size; "+
        "you could also use a component with fixed size.</p>";

    void sizing_undefined_defining () {
        // BEGIN-EXAMPLE: layout.orderedlayout.sizing.sizing-undefined-defining
        // BOOK: layout.components.orderedlayout
        // Vertical layout would normally have 100% width
        VerticalLayout vertical = new VerticalLayout();
        
        // Shrink to fit the width of contained components
        vertical.setWidth(Sizeable.SIZE_UNDEFINED, 0);
        
        // Label has normally 100% width, but we set it as
        // undefined so that it will take only the needed space
        Label label =
            new Label("\u2190 The VerticalLayout shrinks to fit "+
                      "the width of this Label \u2192");
        label.setWidth(Sizeable.SIZE_UNDEFINED, 0);
        vertical.addComponent(label);
        
        // Button has undefined width by default
        Button butt = new Button("\u2190 This Button takes 100% "+
                                 "of the width \u2192");
        butt.setWidth("100%");
        vertical.addComponent(butt);
        // END-EXAMPLE: layout.orderedlayout.sizing.sizing-undefined-defining
        
        vertical.addStyleName("bordered");
        vertical.setSpacing(true);
        setCaption("This is a VerticalLayout with two components");

        setCompositionRoot(vertical);
    }
}
