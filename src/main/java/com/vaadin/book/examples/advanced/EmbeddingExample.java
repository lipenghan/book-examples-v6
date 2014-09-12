package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EmbeddingExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 9754344337L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("div".equals(context))
            div(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    void div (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.embedding.div
        // A button to open the printer-friendly page.
        Button open = new Button("Open a Static HTML Page");

        open.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 6588417468637527327L;

            public void buttonClick(ClickEvent event) {
                // Open it as a popup window with no decorations
                getWindow().open(new ExternalResource(
                    "/book-examples/embedding-in-div.html"),
                        "_blank", 500, 400,  // Width and height 
                        Window.BORDER_NONE); // No decorations
            }
        });
        // END-EXAMPLE: advanced.embedding.div
        
        setCompositionRoot(open);
    }
}
