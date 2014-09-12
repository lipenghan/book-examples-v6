package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ValidationExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -1770451668233870037L;

	public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            ; // basic();
        else if ("customvalidator".equals(context))
            customvalidator (layout);
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
	}
	
    public static final String customvalidatorDescription =
        "<h1>Custom Field Validator</h1>\n" +
        "<p>You can make custom field validators by implementing the <b>Validator</b> interface.</p>" +
        "<p>Notice that validators are not run if the field value is empty. This causes the " +
        "NullValidator to not work when the null representation is set to \"\".</p>";
    
    void customvalidator(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.field.validation.customvalidator
        class MyValidator implements Validator {
            private static final long serialVersionUID = -8281962473854901819L;

            @Override
            public void validate(Object value) throws InvalidValueException {
                // Simply call the isValid(). It is possible to have
                // more complex logic here to also report the reason
                // of the failure in better detail.
                if (!isValid(value))
                    throw new InvalidValueException("You did not greet");
            }

            @Override
            public boolean isValid(Object value) {
                if (value instanceof String &&
                        ((String)value).equals("hello"))
                    return true;
                return false;
            }
        }
        
        final Form form = new Form();
        form.setFormFieldFactory(new FormFieldFactory() {
            private static final long serialVersionUID = -6689267221685345820L;

            @Override
            public Field createField(Item item, Object propertyId, Component uiContext) {
                if ("hello".equals(propertyId)) {
                    TextField field = new TextField("Say hello");
                    field.setNullRepresentation("");
                    
                    // Add the custom validator
                    field.addValidator(new MyValidator());
                    
                    // Add some built-in validators
                    field.addValidator(new StringLengthValidator(
                            "Not long enough or null", 3, 100, true));
                    field.addValidator(new NullValidator(
                            "Must not be null", false));
                    return field;
                }
                return null;
            }
        });
        form.addItemProperty("hello", new ObjectProperty<String>(""));
        
        Button validate = new Button("Validate");
        validate.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 6976755192979152002L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    form.setValidationVisible(true);
                    form.validate();
                    layout.addComponent(new Label("OK"));
                } catch (InvalidValueException e) {
                    layout.addComponent(new Label("Failed"));
                }
            }
        });
        form.getFooter().addComponent(validate);
        // END-EXAMPLE: component.field.validation.customvalidator
        
        layout.addComponent(form);
    }
}
