package com.vaadin.book.applications;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Map;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.ParameterHandler;
import com.vaadin.terminal.URIHandler;
import com.vaadin.ui.*;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;

// BEGIN-EXAMPLE: advanced.urifragmentutility.indexing
public class IndexingExampleApplication extends Application {
    private static final long serialVersionUID = -128617724108192945L;

    String fragment = null; 

    @Override
	public void init() {
        final Window main = new Window("Indexing Example");
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
            private static final long serialVersionUID = -2599930524184798192L;

            public void valueChange(ValueChangeEvent event) {
                String itemid = (String) event.getProperty().getValue();
                
                // Set the fragment with the exclamation mark, which is
                // understood by the indexing engine
                urifu.setFragment("!" + itemid);
            }
        });

        // When the URI fragment is given, use it to set menu selection 
        urifu.addListener(new FragmentChangedListener() {
            private static final long serialVersionUID = 5070399971246531478L;

            public void fragmentChanged(FragmentChangedEvent source) {
                String fragment =
                          source.getUriFragmentUtility().getFragment();
                if (fragment != null) {
                    // Skip the exclamation mark
                    if (fragment.startsWith("!"))
                        fragment = fragment.substring(1);
                    
                    // Set the menu selection
                    menu.select(fragment);
                    
                    // Display some content related to the item
                    main.addComponent(new Label(getContent(fragment)));
                }
            }
        });
        
        // Store possible parameters here
        main.addParameterHandler(new ParameterHandler() {
            private static final long serialVersionUID = 35457670155575139L;

            public void handleParameters(Map<String, String[]> parameters) {
                // If the special escape paremeter is included, store it
                if (parameters.containsKey("_escaped_fragment_"))
                    fragment = parameters.get("_escaped_fragment_")[0];
                else
                    fragment = null;
            }
        });
        
        // Handle the parameters here
        main.addURIHandler(new URIHandler() {
            private static final long serialVersionUID = -380239558601508174L;

            public DownloadStream handleURI(URL context, String relativeUri) {
                if (fragment != null) {
                    // Got the fragment earlier, provide some HTML content
                    // for the indexing engine
                    String content = getContent(fragment);
                    ByteArrayInputStream istream = new ByteArrayInputStream(
                        ("<html><body><p>" + content +
                        "</p></body></html>").getBytes());
                    return new DownloadStream(istream, "text/html", null);
                }
                return null;
            }
        });
    }

    // Provides some textual content for both the Vaadin Ajax application
    // and the HTML page for indexing engines.
    String getContent(String fragment) {
        return "Nice little content for #!" + fragment;
    }
}
// END-EXAMPLE: advanced.urifragmentutility.indexing
