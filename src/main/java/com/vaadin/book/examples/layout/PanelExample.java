package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class PanelExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 3321998143334619838L;

    public void init(String context) {
        if ("basic".equals(context))
            basic();
        else if ("scroll".equals(context))
            scroll();
        else if ("scrollbars".equals(context))
            scrollbars();
        else
            setCompositionRoot(new Label("Error"));
    }
    
    void basic() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.panel.basic
        final Panel panel = new Panel("Panel");
        layout.addComponent(panel);
        
        panel.addComponent(new Label("Here is some content"));
        // END-EXAMPLE: layout.panel.basic
        
        setCompositionRoot(layout);
    }
    
    void scroll() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: layout.panel.scroll
        final Panel panel = new Panel("Scrolling Panel");
        panel.setHeight("300px");
        panel.setWidth("400px");
        panel.getContent().setHeight("1000px");
        panel.setScrollable(true);
        
        layout.addComponent(panel);

        HorizontalLayout scrollButtons = new HorizontalLayout();
        layout.addComponent(scrollButtons);
        
        Button scrollUp = new Button("Scroll Up");
        scrollUp.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 8557421620094669457L;

            public void buttonClick(ClickEvent event) {
                int scrollPos = panel.getScrollTop() - 250;
                if (scrollPos < 0)
                    scrollPos = 0;
                panel.setScrollTop(scrollPos);
            }
        });
        scrollButtons.addComponent(scrollUp);
        
        Button scrollDown = new Button("Scroll Down");
        scrollDown.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 8557421620094669457L;

            public void buttonClick(ClickEvent event) {
                int scrollPos = panel.getScrollTop();
                if (scrollPos > 1000)
                    scrollPos = 1000;
                panel.setScrollTop(scrollPos + 250);
            }
        });
        scrollButtons.addComponent(scrollDown);
        
        // END-EXAMPLE: layout.panel.scroll
        
        setCompositionRoot(layout);
    }

    void scrollbars() {
        VerticalLayout layout = new VerticalLayout();
        
        // BEGIN-EXAMPLE: layout.panel.scrollbars
        
        // END-EXAMPLE: layout.panel.scrollbars
        
        setCompositionRoot(layout);
    }    
    
    void styling() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.panel.basic
        final Panel panel = new Panel("Panel");
        panel.addStyleName(Reindeer.PANEL_LIGHT);
        layout.addComponent(panel);
        
        panel.addComponent(new Label("Here is some content"));
        // END-EXAMPLE: layout.panel.basic
        
        
        setCompositionRoot(layout);
    }
}
