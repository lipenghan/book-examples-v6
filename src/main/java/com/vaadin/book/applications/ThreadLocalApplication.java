package com.vaadin.book.applications;

import com.vaadin.Application;
import com.vaadin.book.examples.advanced.AppData;
import com.vaadin.book.examples.advanced.MyAppCaptions;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

// BEGIN-EXAMPLE: advanced.global.threadlocal
/** Uses a ThreadLocal instance to give access to
 *  session-global data. */
public class ThreadLocalApplication extends Application {
    private static final long serialVersionUID = 511085335415683713L;

    /**
     * We can now nicely access the session-global data
     * in the constuctor of this class.
     */
    class MyComponent extends CustomComponent {
        private static final long serialVersionUID = -5903141473225975078L;

        public MyComponent() {
            VerticalLayout layout = new VerticalLayout();
            
            // Get stuff from the application data object            
            layout.addComponent(new Label("Hello, " +
                AppData.getUserData()));

            layout.addComponent(new Label("Your locale is " +
                AppData.getLocale().getDisplayLanguage()));
            
            layout.addComponent(new Button(
                AppData.getMessage(MyAppCaptions.CancelKey)));
            
            setCompositionRoot(layout);
        }
    }
    
    public void init() { 
        Window main = new Window("Hello window"); 
        setMainWindow(main);
        
        // Create the application data instance
        AppData sessionData = new AppData(this);
        
        // Register it as a listener in the application context
        getContext().addTransactionListener(sessionData);
        
        // Initialize the session-global data
        AppData.initLocale(getLocale(),
                           MyAppCaptions.class.getName());
        
        // Also set the user data model
        AppData.setUserData("Billy");
        
        // Now, we do not pass this application object
        // in the constructor, so it couldn't access the
        // app data otherwise.
        main.addComponent(new MyComponent());
    }
}
// END-EXAMPLE: advanced.global.threadlocal
