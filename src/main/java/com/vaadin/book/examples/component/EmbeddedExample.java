package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class EmbeddedExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3644786684841597587L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("scrolling-panel".equals(context))
            scrollingpanel(layout);
        else if ("scrolling-css".equals(context))
            scrollingcss(layout);
        else if ("googlemaps".equals(context))
            googlemaps(layout);
        setCompositionRoot(layout);
    }
    
    public void basic(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.basic
        // Serve the image from the theme
        Resource rsrc = new ThemeResource("img/smiley2-20px.png");
        
        // Display the image without caption
        Embedded image = new Embedded(null, rsrc);
        layout.addComponent(image);
        // END-EXAMPLE: component.embedded.basic
    }

    public void scrollingpanel(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.scrolling-panel
        // Serve the image from the theme
        Resource rsrc = new ThemeResource("img/embedded-journalist.jpg");
        
        // Display the image without caption
        Embedded image = new Embedded(null, rsrc);
        image.setSizeUndefined(); // Actually the default

        // The panel will give it scrollbars. The root layout
        // (VerticalLayout) must have undefined width to make the
        // horizontal scroll bar appear.
        Panel panel = new Panel("Embedding");
        panel.setWidth("300px");
        panel.setHeight("200px");
        panel.getContent().setSizeUndefined();
        panel.addComponent(image);

        layout.addComponent(panel);
        // END-EXAMPLE: component.embedded.scrolling-panel
    }
    
    public void googlemaps(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.googlemaps
        Embedded browser = new Embedded(null, new ExternalResource("http://maps.google.com/"));
        browser.setType(Embedded.TYPE_BROWSER);
        browser.setWidth("600px");
        browser.setHeight("400px");
        layout.addComponent(browser);
        // END-EXAMPLE: component.embedded.googlemaps
    }

    public void scrollingcss(VerticalLayout layout) { 
        // BEGIN-EXAMPLE: component.embedded.scrolling-css
        // Serve the image from the theme
        Resource rsrc = new ThemeResource("img/embedded-journalist.jpg");
        
        // Display the image without caption
        Embedded image = new Embedded("Embedding", rsrc);
        image.setWidth("400px");
        image.setHeight("300px");
        image.addStyleName("scrollable");

        layout.addComponent(image);
        // END-EXAMPLE: component.embedded.scrolling-css
    }
}
