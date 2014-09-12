package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

public class DescriptionExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -7205982527018773943L;

	public void init (String context) {
    	if ("plain".equals(context))
    		plain();
    	else if ("richtext".equals(context))
    		richtext();
    }
    
    void plain () {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        
        // BEGIN-EXAMPLE: component.features.description.plain
        Button button = new Button("A Button");
        button.setDescription("This is the tooltip");
        // END-EXAMPLE: component.features.description.plain

        layout.addComponent(button);
        setCompositionRoot(layout);
    }

    void richtext () {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setHeight("150px");
        
        // BEGIN-EXAMPLE: component.features.description.richtext
        Button button = new Button("A Button");
    	button.setDescription(
    		    "<h2><img src=\"VAADIN/themes/book-examples/icons/comment_yellow.gif\"/>"+
    		    "A Richtext Tooltip</h2>"+
    		    "<ul>"+
    		    "  <li>Use rich formatting with XHTML</li>"+
    		    "  <li>Include images from themes</li>"+
    		    "  <li>etc.</li>"+
    		    "</ul>");    	
        // END-EXAMPLE: component.features.description.richtext

        layout.addComponent(button);
        setCompositionRoot(layout);
    }
}
