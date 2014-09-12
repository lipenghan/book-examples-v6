package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class IconExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -2576571397568672542L;

    public void init (String context) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        
        // BEGIN-EXAMPLE: component.features.icon
        // Component with an icon from a custom theme
        TextField name = new TextField("Name");
        name.setIcon(new ThemeResource("icons/user.png"));
        layout.addComponent(name);
        
        // Component with an icon from another theme ('runo')
        Button ok = new Button("OK");
        ok.setIcon(new ThemeResource("../runo/icons/16/ok.png"));
        layout.addComponent(ok);
        // END-EXAMPLE: component.features.icon

        layout.setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
        setCompositionRoot(layout);
    }
}
