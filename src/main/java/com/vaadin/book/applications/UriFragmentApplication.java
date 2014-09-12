package com.vaadin.book.applications;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;

// BEGIN-EXAMPLE: advanced.urifragmentutility.uriexample
public class UriFragmentApplication extends Application {
    private static final long serialVersionUID = -128617724108192945L;

    @Override
	public void init() {
        Window main = new Window("URI Fragment Example");
        setMainWindow(main);
        setTheme("book-examples");

        // Create the URI fragment utility
        final UriFragmentUtility urifu = new UriFragmentUtility();
        main.addComponent(urifu);

        // Application state menu
        final ListSelect menu = new ListSelect("Select a URI Fragment");
        menu.addItem("mercury");
        menu.addItem("venus");
        menu.addItem("earth");
        menu.addItem("mars");
        menu.setRows(4);
        menu.setNullSelectionAllowed(false);
        menu.setImmediate(true);
        main.addComponent(menu);

        // Set the URI Fragment when menu selection changes
        menu.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 6380648224897936536L;

            public void valueChange(ValueChangeEvent event) {
                String itemid = (String) event.getProperty().getValue();
                urifu.setFragment(itemid);
            }
        });

        // When the URI fragment is given, use it to set menu selection 
        urifu.addListener(new FragmentChangedListener() {
            private static final long serialVersionUID = -6588416218607827834L;

            public void fragmentChanged(FragmentChangedEvent source) {
                String fragment =
                          source.getUriFragmentUtility().getFragment();
                if (fragment != null)
                    menu.setValue(fragment);
            }
        });        
    }
}
// END-EXAMPLE: advanced.urifragmentutility.uriexample
