package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BufferingExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 4598073828719119575L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        TextField name = new TextField("Name");
        Button button = new Button("Commit", name, "commit");

        layout.addComponent(name);
        layout.addComponent(button);

        setCompositionRoot(layout);
    }
}
