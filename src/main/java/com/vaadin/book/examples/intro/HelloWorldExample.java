package com.vaadin.book.examples.intro;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;

public class HelloWorldExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        Embedded embedded = new Embedded();
        embedded.setSource(new ExternalResource("/book-examples/helloworld?restartApplication"));
        embedded.setType(Embedded.TYPE_BROWSER);
        embedded.setWidth("200px");
        embedded.setHeight("100px");
        layout.addComponent(embedded);

        setCompositionRoot(layout);
    }
}
