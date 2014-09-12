package com.vaadin.book.examples.component.properties;

import java.util.Locale;
import java.util.ResourceBundle;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.book.examples.advanced.MyAppCaptions;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;

public class LocaleExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = 331340085931876813L;
	String context;

	public void init (String context) {
	    this.context = context;

		if ("simple".equals(context))
			overview();
        else if ("get".equals(context))
            get ();
        else if ("getbetter".equals(context))
            getBetter ();
		else if ("selection".equals(context))
			selection ();
	}

	public void attach () {
        super.attach();
	    if ("getbetter".equals(context))
            getBetterAttach ();
	}

	void overview () {
		HorizontalLayout layout = new HorizontalLayout();

		// BEGIN-EXAMPLE: component.features.locale.simple
		// Component for which the locale is meaningful
		InlineDateField date = new InlineDateField("Datum");
		
		// German language specified with ISO 639-1 language
		// code and ISO 3166-1 alpha-2 country code. 
		date.setLocale(new Locale("de", "DE"));
		
		date.setResolution(DateField.RESOLUTION_DAY);
		layout.addComponent(date);
		// END-EXAMPLE: component.features.locale.simple
		
		setCompositionRoot(layout);
    }
    
    void get() {
        HorizontalLayout layout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: component.features.locale.get
        Button cancel = new Button() {
            private static final long serialVersionUID = -3895600218758055115L;

            @Override
            public void attach() {
                ResourceBundle bundle = ResourceBundle.getBundle(
                        MyAppCaptions.class.getName(), getLocale());
                setCaption(bundle.getString("CancelKey"));
            }
        };
        layout.addComponent(cancel);
        // END-EXAMPLE: component.features.locale.get

        setCompositionRoot(layout);
    }

    void getBetter() {
        // A dummy layout, will be replaced in attach()
        HorizontalLayout layout = new HorizontalLayout();
        setCompositionRoot(layout);
    }
    
    void getBetterAttach() {
        HorizontalLayout layout = new HorizontalLayout();

        // BEGIN-EXAMPLE: component.features.locale.getbetter
        // EXAMPLE-REF:   com.vaadin.book.examples.advanced.MyAppCaptions advanced.i18n.bundles
        // EXAMPLE-REF:   com.vaadin.book.examples.advanced.MyAppCaptions_fi advanced.i18n.bundles
        // Captions are stored in MyAppCaptions resource bundle
        // and the application object is known in this context.
        ResourceBundle bundle =
            ResourceBundle.getBundle(MyAppCaptions.class.getName(),
                                     getApplication().getLocale());
        
        // Get a localized resource from the bundle
        Button cancel =
            new Button(bundle.getString(MyAppCaptions.CancelKey));
        layout.addComponent(cancel);
        // END-EXAMPLE: component.features.locale.getbetter
        
        // Reset the composition root
        setCompositionRoot(layout);
    }
	
	void selection() {
        VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("verticalspacing10px");
        layout.setSpacing(true);

        // BEGIN-EXAMPLE: component.features.locale.selection
        // The locale in which we want to have the language
        // selection list
        Locale displayLocale = Locale.ENGLISH;
        
        // All known locales
		final Locale[] locales = Locale.getAvailableLocales();
		
        // Allow selecting a language. We are in a constructor of a
        // CustomComponent, so preselecting the current
        // language of the application can not be done before
        // this (and the selection) component are attached to
        // the application.
		final Select select = new Select("Select a language") {
            private static final long serialVersionUID = 456456456465L;
            
            @Override
            public void attach() {
                setValue(getLocale());
            }
		};
        for (int i=0; i<locales.length; i++) {
            select.addItem(locales[i]);
            select.setItemCaption(locales[i],
                                  locales[i].getDisplayName(displayLocale));
            
            // Automatically select the current locale
            if (locales[i].equals(getLocale()))
                select.setValue(locales[i]);
        }
        layout.addComponent(select);

        // Locale code of the selected locale
        final Label localeCode = new Label("");
        layout.addComponent(localeCode);

        // A date field which language the selection will change
        final InlineDateField date =
            new InlineDateField("Calendar in the selected language");
        date.setResolution(DateField.RESOLUTION_DAY);
        layout.addComponent(date);
        
        // Handle language selection
		select.addListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 3476538475L;

            public void valueChange(ValueChangeEvent event) {
                Locale locale = (Locale) select.getValue();
                date.setLocale(locale);
                localeCode.setValue("Locale code: " +
                                    locale.getLanguage() + "_" +
                                    locale.getCountry());
            }
        });
		select.setImmediate(true);
        // END-EXAMPLE: component.features.locale.selection

        setCompositionRoot(layout);
	}
 }
