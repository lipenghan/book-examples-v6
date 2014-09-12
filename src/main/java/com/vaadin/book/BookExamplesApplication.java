package com.vaadin.book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.book.examples.AbstractExampleItem;
import com.vaadin.book.examples.BookExample;
import com.vaadin.book.examples.BookExampleLibrary;
import com.vaadin.book.examples.CaptionedExampleItem;
import com.vaadin.book.examples.ExampleCtgr;
import com.vaadin.book.examples.RedirctItem;
import com.vaadin.book.examples.SourceFragment;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class BookExamplesApplication extends Application implements HttpServletRequestListener {
    private static final long serialVersionUID = 5548861727207728718L;

    @Override
	public void init() {
        final Window main = new Window("Book Examples");
        main.setImmediate(true);
        setMainWindow(main);
        
        setTheme("book-examples");
        
        // Set session timeout to 10 seconds
        //((WebApplicationContext)getContext()).getHttpSession().setMaxInactiveInterval(10);
        System.out.println("Session timeout: " +
                ((WebApplicationContext)getContext()).getHttpSession().getMaxInactiveInterval() +
                " seconds");
        
        init(main);
    }
    
    public void init(final Window main) {
        VerticalLayout mainLayout = new VerticalLayout();
        main.setContent(mainLayout);
        mainLayout.setSizeFull();
        
        HorizontalLayout titlebar = new HorizontalLayout();
        titlebar.addStyleName("titlebar");
        titlebar.setWidth("100%");
        Label title = new Label("Book of Vaadin Examples");
        title.addStyleName("title");
        titlebar.addComponent(title);
        titlebar.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);
        Embedded logo = new Embedded(null, new ThemeResource("img/vaadin-logo.png"));
        titlebar.addComponent(logo);
        titlebar.setComponentAlignment(logo, Alignment.MIDDLE_RIGHT);
        main.addComponent(titlebar);
       
        HorizontalLayout hor = new HorizontalLayout();
        hor.setSizeFull();
        main.addComponent(hor);
        mainLayout.setExpandRatio(hor, 1.0f);
    
        final Panel menupanel = new Panel("Examples");
        menupanel.addStyleName("menupanel");
        menupanel.setWidth(null);
        menupanel.setHeight("100%");
        menupanel.getContent().setWidth(null);
        //menupanel.getContent().setHeight("100%");
        hor.addComponent(menupanel);

        final Tree menu = new Tree();
        menu.setWidth(null);
        // menu.setHeight("100%");
        menu.setImmediate(true);
        menupanel.addComponent(menu);
    
        final Panel viewpanel = new Panel("Selected Example");
        viewpanel.addStyleName("viewpanel");
        viewpanel.setSizeFull();
        VerticalLayout viewlayout = new VerticalLayout();
        viewlayout.addStyleName("viewlayout");
        viewlayout.setSpacing(true);
        viewlayout.setMargin(true);
        viewpanel.setContent(viewlayout);

        hor.addComponent(viewpanel);
        hor.setExpandRatio(viewpanel, 1.0f);

        WebApplicationContext ctx = (WebApplicationContext) getContext();
        BookExampleLibrary library = BookExampleLibrary.getInstance(ctx.getBaseDirectory());

        AbstractExampleItem[] examples = library.getAllExamples();
        
        // Collect redirects here
        final HashMap<String,String> redirects = new HashMap<String,String>();
        
        // Collect examples here
        final HashMap<String,CaptionedExampleItem> exampleitems = new HashMap<String,CaptionedExampleItem>();
    
        // Build the menu and collect redirections
        for (int i=0; i<examples.length; i++)
            if (examples[i] instanceof BookExample || examples[i] instanceof ExampleCtgr) {
                CaptionedExampleItem example = (CaptionedExampleItem) examples[i];
                exampleitems.put(example.getExampleId(), example);
                
                String itemid = example.getExampleId();
                menu.addItem(itemid);
                menu.setItemCaption(itemid, example.getShortName());
    
                if (examples[i].getParentId() != null)
                    menu.setParent(itemid, examples[i].getParentId());
            } else if (examples[i] instanceof RedirctItem) {
                RedirctItem redirect = (RedirctItem) examples[i];
                redirects.put(redirect.getExampleId(), redirect.redirectid);
            }
        
        // Expand the menu
        for (int i=0; i<examples.length; i++) {
            if (examples[i].getParentId() == null)
                menu.expandItemsRecursively(examples[i].getExampleId());
            
            if (examples[i].isCollapsed())
                menu.collapseItem(examples[i].getExampleId());

            if (menu.getChildren(examples[i].getExampleId()) == null)
                menu.setChildrenAllowed(examples[i].getExampleId(), false);
        }
        
        // Set selected example as given in the URI fragment
        final UriFragmentUtility urifu = new UriFragmentUtility();
        urifu.addListener(new FragmentChangedListener() {
            private static final long serialVersionUID = -6588416218607827834L;

            public void fragmentChanged(FragmentChangedEvent source) {
                String fragment = source.getUriFragmentUtility().getFragment();
                if (fragment != null) {
                    // Handle redirection
                    while (redirects.containsKey(fragment))
                        fragment = redirects.get(fragment);
                    
                    menu.setValue(fragment);
                    
                    // Open the tree nodes leading to the example
                    for (Object parent = menu.getParent(fragment);
                         parent != null;
                         parent = menu.getParent(parent))
                        menu.expandItem(parent);
                }
            }
        });
        mainLayout.addComponent(urifu);

        // Handle menu selection
        menu.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 8236533959795019956L;
    
            public void valueChange(ValueChangeEvent event) {
                viewpanel.removeAllComponents();
                
                String selection = (String) event.getProperty().getValue();
                
                // Find the example
                CaptionedExampleItem exampleItem = exampleitems.get(selection);
                if (selection != null && exampleItem == null)
                    main.showNotification("Invalid item " + selection);
                else if (exampleItem != null) {
                    if (exampleItem.getClass().isAssignableFrom(ExampleCtgr.class)) {
                        if (menu.hasChildren(exampleItem.getExampleId())) {
                            menu.select((String) menu.getChildren(exampleItem.getExampleId()).toArray()[0]);
                        }
                    } else { // A leaf
                        BookExample example = (BookExample) exampleItem;

                        // Load unless already loaded
                        WebApplicationContext ctx = (WebApplicationContext) getContext();
                        example.loadExample(ctx.getBaseDirectory());

                        if (example.getDescription() != null) {
                            Label descLabel = new Label(example.getDescription(), Label.CONTENT_XHTML);
                            descLabel.addStyleName("example-description");
                            viewpanel.addComponent(descLabel);
                        }

                        // The actual example component
                        viewpanel.addComponent(example.createInstance());
                                                
                        // Java sources on the left, CSS on the right
                        HorizontalLayout horizontalOrder = new HorizontalLayout();
                        horizontalOrder.addStyleName("sourcecontainer");
                        horizontalOrder.setSpacing(true);
                        horizontalOrder.setMargin(true);

                        Panel bookRefs   = null;
                        Panel forumLinks = null;
                        Panel kbRefs    = null;

                        List<SourceFragment> fragments = example.getSourceFragments(); 
                        if (fragments != null) {
                            // Java Sources are laid out vertically
                            VerticalLayout verticalListings = new VerticalLayout();
                            verticalListings.setSizeUndefined();
                            verticalListings.setSpacing(true);
                            horizontalOrder.addComponent(verticalListings);
                            
                            // Find the widest source fragment
                            int widestIndex = 0;
                            int widestWidth = 0;
                            for (int fragmentNum = 0; fragmentNum < fragments.size(); fragmentNum++)
                                if (fragments.get(fragmentNum).getSrcWidth() > widestWidth) {
                                    widestIndex = fragmentNum;
                                    widestWidth = fragments.get(fragmentNum).getSrcWidth(); 
                                }
                            System.out.println("Widest listing: " + widestIndex + " which is " + widestWidth);
                            
                            for (int fragmentNum = 0; fragmentNum < fragments.size(); fragmentNum++) {
                                SourceFragment fragment = fragments.get(fragmentNum);

                                // Have caption only in the beginning of the listings
                                String listingCaption = fragmentNum == 0? "Source Code" : "";
                                
                                String srcurl = "http://dev.vaadin.com/browser/doc/book-examples/trunk/src" + fragment.getSrcName();
                                SourceListing listing = new SourceListing(listingCaption, srcurl, fragment);
                                verticalListings.addComponent(listing);

                                // Use the width of the widest listing for all listings
                                if (fragmentNum == widestIndex)
                                    listing.setWidth(Sizeable.SIZE_UNDEFINED, 0);
                                else
                                    listing.setWidth("100%");
                                
                                if (!fragment.getBookRefs().isEmpty()) {
                                    bookRefs = new Panel("Book References");
                                    bookRefs.setSizeUndefined();
                                    for (Iterator<String> iter = fragment.getBookRefs().iterator(); iter.hasNext();) {
                                        String ref = iter.next();
                                        int hashPos = ref.indexOf('#');
                                        String refFragment = "";
                                        if (hashPos != -1) {
                                            refFragment = "#" + ref.replace('#', '.');
                                            ref = ref.substring(0, hashPos);
                                        }
                                        String bookUrl = "http://vaadin.com/book/-/page/" + ref + ".html" + refFragment; 
                                        Link link = new Link(bookUrl, new ExternalResource(bookUrl));
                                        link.setTargetName("_new");
                                        bookRefs.addComponent(link);
                                    }
                                }

                                if (!fragment.getForumLinks().isEmpty()) {
                                    forumLinks = new Panel("Forum Messages");
                                    forumLinks.setSizeUndefined();
                                    for (Iterator<String> iter = fragment.getForumLinks().iterator(); iter.hasNext();) {
                                        String url = iter.next();
                                        Link link = new Link(url, new ExternalResource(url));
                                        link.setTargetName("_new");
                                        forumLinks.addComponent(link);
                                    }
                                }

                                if (!fragment.getKbRefs().isEmpty()) {
                                    kbRefs = new Panel("Pro Account Knowledge Base Articles");
                                    kbRefs.setSizeUndefined();
                                    for (Iterator<SourceFragment.Ref> iter = fragment.getKbRefs().iterator(); iter.hasNext();) {
                                        SourceFragment.Ref ref = iter.next();
                                        String url = "http://vaadin.com/knowledge-base#" + ref.ref;
                                        Link link = new Link(ref.caption, new ExternalResource(url));
                                        link.setTargetName("_new");
                                        forumLinks.addComponent(link);
                                    }
                                }
                            }
                        }

                        // Show associated CSS
                        if (example.getCssFragments() != null && example.getCssFragments().size() > 0) {
                            SourceFragment csscode = example.getCssFragments().get(0);
                            String srcurl = "http://dev.vaadin.com/browser/doc/book-examples/trunk/WebContent/VAADIN/themes/book-examples/styles.css";
                            horizontalOrder.addComponent(new SourceListing("CSS Code", srcurl, csscode));
                        }
                        
                        if (horizontalOrder.getComponentIterator().hasNext())
                            viewpanel.addComponent(horizontalOrder);
                        if (bookRefs != null)
                            viewpanel.addComponent(bookRefs);
                        if (forumLinks != null)
                            viewpanel.addComponent(forumLinks);
                        if (kbRefs != null)
                            viewpanel.addComponent(kbRefs);
                        
                        urifu.setFragment(example.getExampleId());
                    }
                }
            }
        });
        
        Tree.ItemStyleGenerator itemStyleGenerator = new Tree.ItemStyleGenerator() {
			private static final long serialVersionUID = -3231268865512947125L;

			public String getStyle(Object itemId) {
				// Chapter title items do not contain a period
				if (!((String)itemId).contains("."))
					return "chaptertitle";
				return null;
			}
		}; 
        menu.setItemStyleGenerator(itemStyleGenerator);
    }
    
    /** Source code listing. */
    public class SourceListing extends CustomComponent {
        private static final long serialVersionUID = -1864980807288021761L;

        VerticalLayout layout = new VerticalLayout();
        Label srcview; 
        
        /**
         * @param caption caption for the source listing box
         * @param srcCode the source code
         */
        public SourceListing(String caption, String url, final SourceFragment fragment) {
            setSizeUndefined(); // Layout size is also set with custom setWidth()

            // Source caption
            HorizontalLayout titlebar = new HorizontalLayout();
            titlebar.setWidth("100%");
            Label captionLabel = new Label(caption);
            captionLabel.addStyleName("sourcecaption");
            captionLabel.setSizeUndefined();
            titlebar.addComponent(captionLabel);
            titlebar.setComponentAlignment(captionLabel, Alignment.BOTTOM_LEFT);
            
            // Link to source repository
            String filename = url.substring(url.lastIndexOf('/') + 1);
            if (fragment.getFragmentPos() > 0)
                url = url + "#L" + fragment.getFragmentPos();
            Link srcLink = new Link(filename, new ExternalResource(url));
            srcLink.setTargetName("_new");
            srcLink.setDescription("Click link to repository open source file in new window");
            titlebar.addComponent(srcLink);
            titlebar.setComponentAlignment(srcLink, Alignment.BOTTOM_RIGHT);
            
            layout.addComponent(titlebar);
            
            // The actual source code listing
            srcview = new Label(fragment.getSrcCode(), Label.CONTENT_PREFORMATTED);
            srcview.addStyleName("sourcecode");
            srcview.setWidth("-1");
            layout.addComponent(srcview);

            final NativeSelect mode = new NativeSelect();
            mode.addItem("Plain");
            mode.addItem("DocBook");
            mode.addItem("JavaDoc");
            mode.addItem("MarkDown");
            mode.setValue("Plain");
            mode.setNullSelectionAllowed(false);
            mode.setMultiSelect(false);
            
            layout.addComponent(mode);
            layout.setComponentAlignment(mode, Alignment.MIDDLE_RIGHT);

            mode.addListener(new Property.ValueChangeListener() {
                private static final long serialVersionUID = 2161991423208388790L;

                public void valueChange(ValueChangeEvent event) {
                    String selected = (String)mode.getValue();
                    
                    if ("Plain".equals(selected)) {
                        srcview.setValue(fragment.getSrcCode());
                    } else if ("DocBook".equals(selected)) {
                        String trimmed = fragment.getSrcCode().trim();
                        String dbcode = "<programlisting><?pocket-size 65% ?><![CDATA[" +
                                        trimmed + "]]></programlisting>\n";
                        srcview.setValue(dbcode);
                    } else if ("JavaDoc".equals(selected)) {
                        String trimmed = "     * " + fragment.getSrcCode().trim().replace("\n", "\n     * ");
                        String dbcode = "     * <pre>\n" +
                                        trimmed + "\n     * </pre>\n";
                        srcview.setValue(dbcode);
                    } else if ("MarkDown".equals(selected)) {
                        String trimmed = "    " + fragment.getSrcCode().trim().replace("\n", "\n    ");
                        srcview.setValue(trimmed);
                    }
                }
            });
            mode.setImmediate(true);
            
            setCompositionRoot(layout);
        }
        
        /** Set width for both the component and its root layout. */
        @Override
        public void setWidth(String width) {
            super.setWidth(width);
            if (layout != null)
                layout.setWidth(width);
            if (srcview != null)
                srcview.setWidth(width);
        }
        
        /** Set width for both the component and its root layout. */
        @Override
        public void setWidth(float width, int unit) {
            super.setWidth(width, unit);
            if (layout != null)
                layout.setWidth(width, unit);
            if (srcview != null)
                srcview.setWidth(width, unit);
        }
    }
    
    // BEGIN-EXAMPLE: advanced.applicationwindow.automatic
    int windowCount = 1;
    
    @Override
    public Window getWindow(String name) {
        // See if the window already exists in the application
        Window window = super.getWindow(name);
        
        // If a dynamically created window is requested, but
        // it does not exist yet, create it.
        if (window == null) {
            // Create the new window object
            window = new Window("Book Examples Window " +
                                ++windowCount);

            // As the window did not exist, the name parameter is
            // an automatically generated name for a new window.
            window.setName(name);

            // Add it to the application as a regular
            // application-level window. This must be done before
            // calling open, which requires that the window
            // is attached to the application.
            addWindow(window);

            // Open it with the proper URL that includes the
            // automatically generated window name
            window.open(new ExternalResource(window.getURL()));

            // Fill the window with stuff
            init(window);
        }

        return window;
    }
    // END-EXAMPLE: advanced.applicationwindow.automatic

    // BEGIN-EXAMPLE: advanced.servletrequestlistener.introduction
    // In the sending application class we define:
    int clicks = 0;

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getClicks() {
        return clicks;
    }
    // END-EXAMPLE: advanced.servletrequestlistener.introduction

    // Override the default implementation
    public static SystemMessages getSystemMessages() {
        CustomizedSystemMessages messages = new CustomizedSystemMessages();
        messages.setCommunicationErrorCaption("Comm Err");
        messages.setCommunicationErrorMessage("This is really bad.");
        messages.setCommunicationErrorNotificationEnabled(false);
        messages.setCommunicationErrorURL("http://vaadin.com");
        return messages;
    }

    public interface MyHttpListener extends Serializable {
        void onRequestStart(HttpServletRequest request,
                            HttpServletResponse response);
    }
    
    ArrayList<MyHttpListener> myHttpListeners = new ArrayList<MyHttpListener>();
    
    public void addMyHttpListener(MyHttpListener listener) {
        myHttpListeners.add(listener);
    }

    public void removeMyHttpListener(MyHttpListener listener) {
        myHttpListeners.remove(listener);
    }
    
    public void removeAllMyHttpListeners() {
        myHttpListeners.removeAll(myHttpListeners);
    }
    
    @Override
    public void onRequestStart(HttpServletRequest request,
                               HttpServletResponse response) {
        for (MyHttpListener l: myHttpListeners)
            l.onRequestStart(request, response);
    }

    @Override
    public void onRequestEnd(HttpServletRequest request,
                             HttpServletResponse response) {
    }
}
