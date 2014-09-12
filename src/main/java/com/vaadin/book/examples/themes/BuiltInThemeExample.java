package com.vaadin.book.examples.themes;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.Runo;

public class BuiltInThemeExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 6580750126724601997L;
    
    String context;
    HorizontalLayout layout = new HorizontalLayout();

    public void init (String context) {
        this.context = context;
        setCompositionRoot(layout);
    }
    
    @Override
    public void attach() {
        if ("runo".equals(context))
            runo(layout);
    }
    
    void runo (Layout layout) {
        // BEGIN-EXAMPLE: themes.creating.default.runo
        getApplication().setTheme("runo");
        
        Panel panel = new Panel("Regular Panel in the Runo Theme");
        panel.addComponent(new Button("Regular Runo Button"));
        
        // A button with the "small" style
        Button smallButton = new Button("Small Runo Button");
        smallButton.addStyleName(Runo.BUTTON_SMALL);

        Panel lightPanel = new Panel("Light Panel");
        lightPanel.addStyleName(Runo.PANEL_LIGHT);
        lightPanel.addComponent(new Label("With addStyleName(\"light\")"));
        // END-EXAMPLE: themes.creating.default.runo

        layout.addComponent(panel);
        panel.addComponent(smallButton);
        layout.addComponent(lightPanel);
    }

    void reindeer (Layout layout) {
        // BEGIN-EXAMPLE: themes.creating.default.reindeer
        getApplication().setTheme("runo");
        
        Panel panel = new Panel("Regular Panel in the Runo Theme");
        panel.addComponent(new Button("Regular Runo Button"));
        
        // A button with the "small" style
        Button smallButton = new Button("Small Runo Button");
        smallButton.addStyleName(Reindeer.BUTTON_SMALL);
        
        Panel lightPanel = new Panel("Light Panel");
        lightPanel.addStyleName(Reindeer.PANEL_LIGHT);
        lightPanel.addComponent(new Label("With addStyleName(\"light\")"));
        // END-EXAMPLE: themes.creating.default.reindeer

        // com.vaadin.ui.themes.Reindeer

        layout.addComponent(panel);
        panel.addComponent(smallButton);
        layout.addComponent(lightPanel);
    }
}
