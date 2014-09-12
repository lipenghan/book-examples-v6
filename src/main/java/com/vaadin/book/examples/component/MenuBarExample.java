package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.MenuItem;

public class MenuBarExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        setCompositionRoot(layout);
    }

    public static String basicDescription =
        "<h1>Basic use of the <b>MenuBar</b> component</h1> "+
        "<p>Selection is handled by implementing the <b>Command</b> interface.</p>";
    
    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.menubar.basic
        MenuBar barmenu = new MenuBar();
        layout.addComponent(barmenu);
        
        // A feedback component
        final Label selection = new Label("-");
        layout.addComponent(selection);

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand = new MenuBar.Command() {
            private static final long serialVersionUID = 4483012525105015694L;

            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("Ordered a " +
                        selectedItem.getText() +
                        " from menu.");
            }  
        };
        
        // Put some items in the menu hierarchically
        MenuBar.MenuItem beverages =
            barmenu.addItem("Beverages", null, null);
        MenuBar.MenuItem hot_beverages =
            beverages.addItem("Hot", null, null);
        hot_beverages.addItem("Tea", null, mycommand);
        hot_beverages.addItem("Coffee", null, mycommand);
        MenuBar.MenuItem cold_beverages =
            beverages.addItem("Cold", null, null);
        cold_beverages.addItem("Milk", null, mycommand);
        cold_beverages.addItem("Weissbier", null, mycommand);
        
        // Another top-level item
        MenuBar.MenuItem snacks =
            barmenu.addItem("Snacks", null, null);
        snacks.addItem("Weisswurst", null, mycommand);
        snacks.addItem("Bratwurst", null, mycommand);
        snacks.addItem("Currywurst", null, mycommand);
        
        // Yet another top-level item
        MenuBar.MenuItem services =
            barmenu.addItem("Services", null, null);
        services.addItem("Car Service", null, mycommand);        
        // END-EXAMPLE: component.menubar.basic
    }
}
