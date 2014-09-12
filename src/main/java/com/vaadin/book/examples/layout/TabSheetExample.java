package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class TabSheetExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 3321998143334619838L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("tabchange".equals(context))
            tabchange(layout);
        else if ("styling".equals(context))
            styling(layout);
        else
            setCompositionRoot(new Label("Error"));
        
        setCompositionRoot(layout);
    }
    
    void basic(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.basic
        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);

        // Create the first tab
        VerticalLayout tab1 = new VerticalLayout();
        tab1.addComponent(new Embedded(null,
                new ThemeResource("img/planets/Mercury.jpg")));
        tabsheet.addTab(tab1, "Mercury",
                new ThemeResource("img/planets/Mercury_symbol.png"));

        // Second tab gets its caption from component caption
        VerticalLayout tab2 = new VerticalLayout();
        tab2.addComponent(new Embedded(null,
                new ThemeResource("img/planets/Venus.jpg")));
        tab2.setCaption("Venus");
        tabsheet.addTab(tab2).setIcon(
                new ThemeResource("img/planets/Venus_symbol.png"));
        // END-EXAMPLE: layout.tabsheet.basic
    }
    
    void extended(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.extended
        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);

        // The other tabs
        String planets[] = {"Earth", "Mars", "Jupiter",
                            "Saturn", "Neptune"};
        for (String planet: planets) {
            VerticalLayout tab = new VerticalLayout();
            Embedded content = new Embedded(null,
                    new ThemeResource("img/planets/" +
                            planet + ".jpg"));
            content.setHeight("240px");
            tab.addComponent(content);
            tab.setCaption(planet);
            tabsheet.addTab(tab).setIcon(
                    new ThemeResource("img/planets/" +
                                      planet+"_symbol.png"));
        }
        // END-EXAMPLE: layout.tabsheet.extended
    }

    void tabchange(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.tabchange
        final TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);

        // The other tabs
        String planets[] = {"Mercury", "Venus", "Earth", "Mars",
                            "Jupiter", "Saturn", "Neptune"};
        for (String planet: planets) {
            VerticalLayout tab = new VerticalLayout();
            Embedded content = new Embedded(null,
                    new ThemeResource("img/planets/" +
                            planet + ".jpg"));
            content.setHeight("240px");
            tab.addComponent(content);
            
            tab.addComponent(new CheckBox("OK to go ahead"));
            
            tab.setCaption(planet);
            tabsheet.addTab(tab).setIcon(
                    new ThemeResource("img/planets/" +
                                      planet+"_symbol.png"));
        }

        // Handling tab changes
        tabsheet.addListener(new TabSheet.SelectedTabChangeListener() {
            private static final long serialVersionUID = -2358653511430014752L;
            
            Component selected     = tabsheet.getSelectedTab();
            boolean   preventEvent = false;

            public void selectedTabChange(SelectedTabChangeEvent event) {
                if (preventEvent) {
                    preventEvent = false;
                    return;
                }
                // Check the previous tab
                VerticalLayout tab = (VerticalLayout) selected;
                CheckBox mayChange = (CheckBox) tab.getComponent(1);
                if (!mayChange.booleanValue()) {
                    // Revert the tab change
                    preventEvent = true; // Prevent secondary change event
                    tabsheet.setSelectedTab(selected);
                    getWindow().showNotification("Must check!");
                } else {
                    selected = tabsheet.getSelectedTab();
                    getWindow().showNotification("Changed!");
                }
            }
        });
        // END-EXAMPLE: layout.tabsheet.tabchange
    }

    void styling(VerticalLayout layout) {
        layout.setWidth("300px");

        // BEGIN-EXAMPLE: layout.tabsheet.styling
        TabSheet tabsheet = new TabSheet();
        tabsheet.addStyleName(Reindeer.PANEL_LIGHT);
        layout.addComponent(tabsheet);
        
        tabsheet.addComponent(new Label("Here is some content"));
        // END-EXAMPLE: layout.tabsheet.styling
    }
}
