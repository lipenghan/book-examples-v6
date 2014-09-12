package com.vaadin.book.examples.advanced;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;

public class UriFragmentExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3150712558665196340L;
    
    public void init(final String context) {
        final VerticalLayout layout = new VerticalLayout();
        
        final CheckBox enableBorders = new CheckBox("Enable Window Menus and Toolbars");
        layout.addComponent(enableBorders);
        
        Button open = new Button("Click to Open Window");
        open.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -8054767660830579600L;

            public void buttonClick(ClickEvent event) {
                int windows = 1;
                String windowcaption = "URI Fragment Example";
                if ("indexing".equals(context)) {
                    windows = 2;
                    windowcaption = "Indexing Example";
                }
                
                for (int i=0; i<windows; i++) {
                    final Window window = new Window(windowcaption);
    
                    // Add the window to the application
                    getApplication().addWindow(window);
                    
                    int borders = Window.BORDER_NONE;
                    int width   = 800;
                    if (enableBorders.booleanValue()) {
                        borders = Window.BORDER_DEFAULT;
                        width = 1024;
                    }
                    
                    String url = "/book-examples/" + context;
                    if ("indexing".equals(context))
                        if (i == 1)
                            url += "#!mars";
                        else
                            url += "?_escaped_fragment_=mars";
                    
                    // Get the URL for the window, and open that in a new
                    // browser window, in this case in a small window.
                    getWindow().open(new ExternalResource(url), // URL
                        "_blank", // window name
                        width, // width
                        200, // weight
                        borders // decorations
                        );
    
                     // It's a good idea to remove the window when it's closed (also
                     // when the browser window 'x' is used), unless you explicitly
                     // want the window to persist (if it's not removed from the
                     // application, it can still be retrieved from it's URL.
                     window.addListener(new Window.CloseListener() {
                        private static final long serialVersionUID = -6387900851552513107L;
    
                         public void windowClose(CloseEvent e) {
                             // remove from application
                             getApplication().removeWindow(window);
                         }
                     });
                }
                layout.addComponent(new Label("See the window that opens (allow popups if necessary)"));
            }
        });
        layout.addComponent(open);
        
        setCompositionRoot(layout);
    }
}
