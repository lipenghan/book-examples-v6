package com.vaadin.book.examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.book.examples.addons.CalendarExample;
import com.vaadin.book.examples.addons.jpacontainer.JPAContainerExample;
import com.vaadin.book.examples.addons.jpacontainer.JPAFieldFactoryExample;
import com.vaadin.book.examples.addons.jpacontainer.JPAFilteringExample;
import com.vaadin.book.examples.addons.jpacontainer.JPAHierarhicalExample;
import com.vaadin.book.examples.advanced.ApplicationWindowExample;
import com.vaadin.book.examples.advanced.BrowserInfoExample;
import com.vaadin.book.examples.advanced.EmbeddingExample;
import com.vaadin.book.examples.advanced.GlobalAccessExample;
import com.vaadin.book.examples.advanced.I18NExample;
import com.vaadin.book.examples.advanced.JSAPIExample;
import com.vaadin.book.examples.advanced.LoggingExample;
import com.vaadin.book.examples.advanced.PrintingExample;
import com.vaadin.book.examples.advanced.ServletRequestListenerExample;
import com.vaadin.book.examples.advanced.ShortcutExample;
import com.vaadin.book.examples.advanced.UriFragmentExample;
import com.vaadin.book.examples.advanced.UriHandlerExample;
import com.vaadin.book.examples.advanced.dd.ComponentDnDExample;
import com.vaadin.book.examples.advanced.dd.DiagramDnDExample;
import com.vaadin.book.examples.advanced.dd.DragNDropTreeExample;
import com.vaadin.book.examples.advanced.dd.TreeAndTableExample;
import com.vaadin.book.examples.application.ErrorIndicatorExample;
import com.vaadin.book.examples.application.EventListenerExample;
import com.vaadin.book.examples.application.LayoutClickListenerExample;
import com.vaadin.book.examples.application.NotificationExample;
import com.vaadin.book.examples.application.ResourceExample;
import com.vaadin.book.examples.application.SubWindowExample;
import com.vaadin.book.examples.component.ButtonExample;
import com.vaadin.book.examples.component.CheckBoxExample;
import com.vaadin.book.examples.component.ComboBoxExample;
import com.vaadin.book.examples.component.CustomComponentExample;
import com.vaadin.book.examples.component.DateFieldExample;
import com.vaadin.book.examples.component.EmbeddedExample;
import com.vaadin.book.examples.component.FormExample;
import com.vaadin.book.examples.component.GeneratedColumnExample;
import com.vaadin.book.examples.component.LabelExample;
import com.vaadin.book.examples.component.LinkExample;
import com.vaadin.book.examples.component.ListSelectExample;
import com.vaadin.book.examples.component.LoginFormExample;
import com.vaadin.book.examples.component.MenuBarExample;
import com.vaadin.book.examples.component.NativeSelectExample;
import com.vaadin.book.examples.component.OptionGroupExample;
import com.vaadin.book.examples.component.PasswordFieldExample;
import com.vaadin.book.examples.component.ProgressIndicatorExample;
import com.vaadin.book.examples.component.RichTextAreaExample;
import com.vaadin.book.examples.component.SelectExample;
import com.vaadin.book.examples.component.SliderExample;
import com.vaadin.book.examples.component.TableExample;
import com.vaadin.book.examples.component.TextAreaExample;
import com.vaadin.book.examples.component.TextChangeEventsExample;
import com.vaadin.book.examples.component.TextFieldExample;
import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.book.examples.component.TreeTableExample;
import com.vaadin.book.examples.component.TwinColSelectExample;
import com.vaadin.book.examples.component.UploadExample;
import com.vaadin.book.examples.component.properties.CaptionExample;
import com.vaadin.book.examples.component.properties.DescriptionExample;
import com.vaadin.book.examples.component.properties.EnabledExample;
import com.vaadin.book.examples.component.properties.FocusExample;
import com.vaadin.book.examples.component.properties.IconExample;
import com.vaadin.book.examples.component.properties.ListenerExample;
import com.vaadin.book.examples.component.properties.LocaleExample;
import com.vaadin.book.examples.component.properties.ReadOnlyExample;
import com.vaadin.book.examples.component.properties.RequiredExample;
import com.vaadin.book.examples.component.properties.StyleNameExample;
import com.vaadin.book.examples.component.properties.ValidationExample;
import com.vaadin.book.examples.component.properties.VisibleExample;
import com.vaadin.book.examples.datamodel.BeanContainerExample;
import com.vaadin.book.examples.datamodel.BeanItemContainerExample;
import com.vaadin.book.examples.datamodel.ContainerFilterExample;
import com.vaadin.book.examples.datamodel.FilesystemContainerExample;
import com.vaadin.book.examples.datamodel.HierarchicalExample;
import com.vaadin.book.examples.datamodel.ItemExample;
import com.vaadin.book.examples.datamodel.PropertyExample;
import com.vaadin.book.examples.intro.HelloWorldExample;
import com.vaadin.book.examples.layout.AbsoluteLayoutExample;
import com.vaadin.book.examples.layout.AlignmentExample;
import com.vaadin.book.examples.layout.CssLayoutExample;
import com.vaadin.book.examples.layout.CustomLayoutExample;
import com.vaadin.book.examples.layout.ExpandRatioExample;
import com.vaadin.book.examples.layout.GridLayoutExample;
import com.vaadin.book.examples.layout.LayoutExample;
import com.vaadin.book.examples.layout.LayoutFeaturesExample;
import com.vaadin.book.examples.layout.MarginExample;
import com.vaadin.book.examples.layout.OrderedLayoutExample;
import com.vaadin.book.examples.layout.PanelExample;
import com.vaadin.book.examples.layout.PopupViewExample;
import com.vaadin.book.examples.layout.SpacingExample;
import com.vaadin.book.examples.layout.SplitPanelExample;
import com.vaadin.book.examples.layout.TabSheetExample;
import com.vaadin.book.examples.misc.ExceptionExamples;
import com.vaadin.book.examples.misc.I18nPrototype;
import com.vaadin.book.examples.misc.SerializationExample;
import com.vaadin.book.examples.themes.BuiltInThemeExample;
import com.vaadin.book.examples.themes.ThemeTricksExample;

public class BookExampleLibrary {
    private static BookExampleLibrary instance = null;
    
    /**
     * Gets the Book example library singleton instance.
     * 
     * @return the singleton instance
     */
    synchronized public static BookExampleLibrary getInstance(File baseDirectory) {
        if (instance == null)
            instance = new BookExampleLibrary(baseDirectory);
        return instance;
    }

    /**
     * Returns all example items, including redirection items.
     * 
     * @return array of example objects
     */
    public AbstractExampleItem[] getAllExamples() {
        return examples;
    }

    /**
     * Returns only actual example objects, no redirection items.
     * 
     * @return list of BookExample objects
     */
    public List<BookExample> getExamples() {
        ArrayList<BookExample> exampleList = new ArrayList<BookExample>();
        for (int i=0; i<examples.length; i++)
            if (examples[i] instanceof BookExample)
            exampleList.add((BookExample) examples[i]);
        return exampleList;
    }

    /** Constructor. */
    private BookExampleLibrary(File baseDirectory) {
        System.out.println("book-examples INFO: Loading example data...");

        for (BookExample e: getExamples()) {
            e.loadExample(baseDirectory);
            // System.out.println("book-examples INFO: " + e.getExampleId());
        }

        System.out.println("book-examples INFO: Loaded " + getExamples().size() + " examples.");
    }

    final AbstractExampleItem examples[] = {
            new ExampleCtgr("intro", "Chapter 1. Inftroduction"),
            new ExampleCtgr("intro.walkthrough-", "1.2. Example Application Walkthrough"),
            new BookExample("intro.walkthrough.helloworld", "Hello World", HelloWorldExample.class),
            new ExampleCtgr("application", "Chapter 4. Writing a Web Application"),
            new ExampleCtgr("application.child-windows-", "4.3. Child Windows"),
            new BookExample("application.child-windows.basic", "Basic Use", SubWindowExample.class),
            new BookExample("application.child-windows.inheritance", "Inheriting Window", SubWindowExample.class),
            new BookExample("application.child-windows.close", "Closing a Child Window", SubWindowExample.class),
            new BookExample("application.child-windows.scrolling", "Scroll Bars", SubWindowExample.class),
            new BookExample("application.child-windows.noscroll", "Scroll Prevention", SubWindowExample.class),
            new BookExample("application.child-windows.styling", "Styling with CSS", SubWindowExample.class),
            new ExampleCtgr("application.eventlistener-", "4.4. Handling Events with Listeners"),
            new BookExample("application.eventlistener.classlistener", "Implementing a Listener", EventListenerExample.class),
            new BookExample("application.eventlistener.anonymous", "Anonymous Class", EventListenerExample.class),
            new BookExample("application.eventlistener.methodlistener", "Listener Method", EventListenerExample.class),
            new ExampleCtgr("application.eventlistener.clicklistener-", "Layout Click Listener"),
            new BookExample("application.eventlistener.clicklistener.basic", "Basic Use", LayoutClickListenerExample.class),
            new BookExample("application.eventlistener.clicklistener.doubleclick", "Double Click", LayoutClickListenerExample.class),
            new ExampleCtgr("application.resources-", "4.5. Referencing Resources"),
            new BookExample("application.resources.fileresource", "4.5.2. File Resources", ResourceExample.class),
            new ExampleCtgr("application.resources.classresource-", "4.5.3. Class Loader Resources"),
            new BookExample("application.resources.classresource.class-basic", "Basic Use", ResourceExample.class),
            new BookExample("application.resources.classresource.class-ref", "Getting the Reference", ResourceExample.class),
            new BookExample("application.resources.themeresource", "4.5.4. Theme Resources", ResourceExample.class),
            new ExampleCtgr("application.resources.externalresource-", "4.5.x. External Resources"),
            new BookExample("application.resources.externalresource.local", "Local Static Resources", ResourceExample.class),
            new BookExample("application.resources.externalresource.relative", "Relative External Resources", ResourceExample.class),
            new ExampleCtgr("application.errors-", "4.7. Handling Errors"),
            new ExampleCtgr("application.errors.error-indicator-", "4.7.1 Error Indicator and Message"),
            new BookExample("application.errors.error-indicator.basic", "Basic Use", ErrorIndicatorExample.class),
            new BookExample("application.errors.error-indicator.setting", "Setting", ErrorIndicatorExample.class),
            new BookExample("application.errors.error-indicator.clearing", "Clearing", ErrorIndicatorExample.class),
            new BookExample("application.errors.error-indicator.form", "Form Error", ErrorIndicatorExample.class),
            new ExampleCtgr("application.errors.notification-", "4.7.2 Notifications"),
            new BookExample("application.errors.notification.error", "Error Notification", NotificationExample.class),
            new ExampleCtgr("component.features-", "5.3. Common Component Features"),
            new ExampleCtgr("component", "Chapter 5. User Interface Components"),
            new ExampleCtgr("component.features-", "5.3. Common Component Features"),
            new ExampleCtgr("component.features.caption-", "5.3.1 Caption"),
            new BookExample("component.features.caption.overview", "Overview", CaptionExample.class),
            new BookExample("component.features.caption.layout", "Management by Layout", CaptionExample.class),
            new BookExample("component.features.caption.self", "Management by the Component", CaptionExample.class),
            new BookExample("component.features.caption.special", "Unicode Characters", CaptionExample.class),
            new BookExample("component.features.caption.styling", "Styling with CSS", CaptionExample.class),
            new ExampleCtgr("component.features.description-", "5.3.2 Description & Tooltips"),
            new BookExample("component.features.description.plain", "Plain Text", DescriptionExample.class),
            new BookExample("component.features.description.richtext", "Rich Text", DescriptionExample.class),
            new ExampleCtgr("component.features.enabled-", "5.3.3 Enabled/Disabled"),
            new BookExample("component.features.enabled.simple", "Basic use", EnabledExample.class),
            new BookExample("component.features.enabled.styling", "Styling with CSS", EnabledExample.class),
            new ExampleCtgr("component.features.focusable-", "5.3.x Focus and Tabulator Index"),
            new BookExample("component.features.focusable.focus", "Setting focus", FocusExample.class),
            new BookExample("component.features.focusable.tabindex", "Setting tab index", FocusExample.class),
            new BookExample("component.features.focusable.focusevent", "Focus and Blur Events", FocusExample.class),
            new BookExample("component.features.icon", "5.3.4 Icon", IconExample.class),
            new BookExample("component.features.listener", "5.3.x Component Listener Interface", ListenerExample.class),
            new ExampleCtgr("component.features.locale-", "5.3.5 Locale"),
            new BookExample("component.features.locale.simple", "Setting locale", LocaleExample.class),
            new BookExample("component.features.locale.get", "Internationalization I", LocaleExample.class),
            new BookExample("component.features.locale.getbetter", "Internationalization II", LocaleExample.class),
            new BookExample("component.features.locale.selection", "Language selection", LocaleExample.class),
            new ExampleCtgr("component.features.readonly-", "5.3.6 Read-Only"),
            new BookExample("component.features.readonly.simple", "Basic use", ReadOnlyExample.class),
            new BookExample("component.features.readonly.layouts", "Layouts", ReadOnlyExample.class),
            new BookExample("component.features.readonly.styling", "Styling with CSS", ReadOnlyExample.class),
            new ExampleCtgr("component.features.stylename-", "5.3.7 Style Name"),
            new BookExample("component.features.stylename.add", "Adding", StyleNameExample.class),
            new BookExample("component.features.stylename.set", "Setting I", StyleNameExample.class),
            new BookExample("component.features.stylename.set-changing", "Setting II", StyleNameExample.class),
            new ExampleCtgr("component.features.visible-", "5.3.8 Visible/Invisible"),
            new BookExample("component.features.visible.simple", "Basic use", VisibleExample.class),
            new BookExample("component.features.visible.inlayout", "Invisible in Layout", VisibleExample.class),
            new BookExample("component.features.visible.styling", "Styling with CSS", VisibleExample.class),
            new ExampleCtgr("component.field-", "5.x Field Component Features"),
            new ExampleCtgr("component.field.required-", "5.x.x Required"),
            new BookExample("component.field.required.basic", "Basic use", RequiredExample.class),
            new BookExample("component.field.required.beanfields", "Required Bean Fields", RequiredExample.class),
            new BookExample("component.field.required.caption", "With Caption", RequiredExample.class),
            new BookExample("component.field.required.nocaption", "Required but no Caption", RequiredExample.class),
            // new BookExample("component.field.required.styling", "Styling with CSS", RequiredExample.class),
            new ExampleCtgr("component.field.validation-", "5.x.x Validation"),
            new BookExample("component.field.validation.customvalidator", "Custom Validator", ValidationExample.class),
            new ExampleCtgr("component.label-", "5.4. Label"),
            new BookExample("component.label.basic", "Basic Use", LabelExample.class),
            new BookExample("component.label.content-modes-", "Content Modes", LabelExample.class),
            new BookExample("component.label.content-modes.javascript", "JavaScript in XHTML mode", LabelExample.class),
            new BookExample("component.label.wrap", "Line Wrap", LabelExample.class),
            new ExampleCtgr("component.label.spacing-", "Spacing with Label"),
            new BookExample("component.label.spacing.non-breaking", "Non-Breaking Space", LabelExample.class),
            new BookExample("component.label.spacing.preformatted", "Preformatted Text", LabelExample.class),
            new BookExample("component.label.spacing.adjustable", "Adjustable Spacing", LabelExample.class),
            new BookExample("component.label.spacing.expanding", "Expanding Spacer", LabelExample.class),
            new ExampleCtgr("component.label.styling-", "Styling with CSS"),
            new BookExample("component.label.styling.css", "Basic Styling", LabelExample.class),
            new BookExample("component.label.styling.predefinedstyles", "Predefined Styles", LabelExample.class),
            new BookExample("component.label.styling.switchbutton", "Switch Button", LabelExample.class),
            new ExampleCtgr("component.link-", "5.5. Link"),
            new BookExample("component.link.basic", "Basic Use", LinkExample.class),
            new BookExample("component.link.target", "Hyperlink Target", LinkExample.class),
            new BookExample("component.link.popup", "Open in Popup Window", LinkExample.class),
            new BookExample("component.link.css", "Styling with CSS", LinkExample.class),
            new ExampleCtgr("component.textfield-", "5.6. TextField"),
            new BookExample("component.textfield.basic", "Basic Use", TextFieldExample.class),
            new BookExample("component.textfield.inputhandling", "Handling Input", TextFieldExample.class),
            new BookExample("component.textfield.valuetype", "Value Type", TextFieldExample.class),
            new BookExample("component.textfield.databinding", "Binding to an Object", TextFieldExample.class),
            new BookExample("component.textfield.beanbinding", "Binding to a Bean Property", TextFieldExample.class),
            new BookExample("component.textfield.nullvaluerepresentation", "Null Value Representation", TextFieldExample.class),
            new BookExample("component.textfield.widthfitting", "Fitting Width", TextFieldExample.class),
            new ExampleCtgr("component.textfield.textchangeevents-", "Text Change Events"),
            new BookExample("component.textfield.textchangeevents.counter", "Counter", TextChangeEventsExample.class),
            new BookExample("component.textfield.textchangeevents.filtering", "Filtering", TextChangeEventsExample.class),
            new BookExample("component.textfield.selectall", "Selecting All Text", TextFieldExample.class),
            new BookExample("component.textfield.css", "CSS Styling", TextFieldExample.class),
            new ExampleCtgr("component.textarea-", "5.7. TextArea"),
            new BookExample("component.textarea.basic", "Basic Use", TextAreaExample.class),
            new BookExample("component.textarea.wordwrap", "Word Wrap", TextAreaExample.class),
            new BookExample("component.textarea.css", "CSS Styling", TextAreaExample.class),
            new ExampleCtgr("component.passwordfield-", "5.8. PasswordField"),
            new BookExample("component.passwordfield.basic", "Basic Use", PasswordFieldExample.class),
            new BookExample("component.passwordfield.css", "CSS Styling", PasswordFieldExample.class),
            new ExampleCtgr("component.richtextarea-", "5.x. RichTextArea"),
            new BookExample("component.richtextarea.basic", "Basic Use", RichTextAreaExample.class),
            new ExampleCtgr("component.datefield-", "5.8. Date and Time Input"),
            new BookExample("component.datefield.basic", "Basic Use of DateField", DateFieldExample.class),
            new ExampleCtgr("component.datefield.popupdatefield", "5.8.1 PopupDateField"),
            new BookExample("component.datefield.popupdatefield.popup-basic", "Basic Use", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.formatting", "Formatting", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.customerror", "Customizing the Error", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.parsing", "Parsing a Date", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.validation", "Validating a Date", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.inputprompt", "Input Prompt", DateFieldExample.class),
            new BookExample("component.datefield.inlinedatefield", "5.8.2 InlineDateField", DateFieldExample.class),
            new BookExample("component.datefield.resolution-day", "5.8.3 Day Resolution", DateFieldExample.class),
            new BookExample("component.datefield.weeknumbers", "5.8.x Week Numbers", DateFieldExample.class),
            new ExampleCtgr("component.button-", "5.9. Button"),
            new BookExample("component.button.basic", "Basic Use", ButtonExample.class),
            new BookExample("component.button.link", "Button Looks Like a Link", ButtonExample.class),
            new ExampleCtgr("component.checkbox-", "5.x. CheckBox"),
            new BookExample("component.checkbox.basic", "Basic Use", CheckBoxExample.class),
            new BookExample("component.checkbox.beanbinding", "Bean Binding", CheckBoxExample.class),
            new ExampleCtgr("component.select", "5.11. Selecting Items"),
            new ExampleCtgr("component.select.databinding-", "5.11.1 Binding to Data"),
            new ExampleCtgr("component.select.databinding.adding-", "Adding Items"),
            new BookExample("component.select.databinding.adding.givenitemid", "Given Item Id", SelectExample.class),
            new BookExample("component.select.databinding.adding.generateditemid", "Generated Item Id", SelectExample.class),
            new ExampleCtgr("component.select.databinding.captions-", "Caption Modes"),
            new BookExample("component.select.databinding.captions.explicitdefaultsid", "Explicit Defaults to ID", SelectExample.class),
            new BookExample("component.select.databinding.captions.id", "Non-String ID", SelectExample.class),
            new BookExample("component.select.databinding.captions.property", "Use a Property", SelectExample.class),
            new ExampleCtgr("component.select.select-", "5.11.2 Basic Select Component"),
            new BookExample("component.select.select.basic", "Basic Use", SelectExample.class),
            new BookExample("component.select.select.icons", "Item Icons", SelectExample.class),
            new BookExample("component.select.select.multiselect", "Multiple Selection", SelectExample.class),
            new ExampleCtgr("component.select.listselect-", "5.11.3 ListSelect"),
            new BookExample("component.select.listselect.basic", "Basic Use", ListSelectExample.class),
            new BookExample("component.select.listselect.multiselect", "Multiple Selection Mode", ListSelectExample.class),
            new ExampleCtgr("component.select.nativeselect-", "5.11.4 NativeSelect"),
            new BookExample("component.select.nativeselect.basic", "Basic Use", NativeSelectExample.class),
            new ExampleCtgr("component.select.combobox-", "5.11.5 ComboBox"),
            new BookExample("component.select.combobox.basic", "Basic Use", ComboBoxExample.class),
            new BookExample("component.select.combobox.enumtype", "Using an Enum Type", ComboBoxExample.class),
            new BookExample("component.select.combobox.preselecting", "Preselecting an Item", ComboBoxExample.class),
            new BookExample("component.select.combobox.newitemsallowed", "Adding New Items", ComboBoxExample.class),
            new BookExample("component.select.combobox.nullselection", "Null Selection", ComboBoxExample.class),
            new BookExample("component.select.combobox.resetselection", "Resetting Selection", ComboBoxExample.class),
            //new ExampleItem("component.select.hierarchical", "Hierarchical selection", SelectExample.class),
            new ExampleCtgr("component.select.optiongroup-", "5.11.6 OptionGroup"),
            new BookExample("component.select.optiongroup.basic", "Basic Use", OptionGroupExample.class),
            new BookExample("component.select.optiongroup.icons", "Icons", OptionGroupExample.class),
            new BookExample("component.select.optiongroup.disabling", "Disabling Items", OptionGroupExample.class),
            new BookExample("component.select.optiongroup.styling", "Styling", OptionGroupExample.class),
            new ExampleCtgr("component.select.twincolselect-", "5.11.7 TwinColSelect"),
            new BookExample("component.select.twincolselect.basic", "Basic Use", TwinColSelectExample.class),
            new BookExample("component.select.twincolselect.captions", "Column Captions", TwinColSelectExample.class),
            // new BookExample("component.select.twincolselect.icons", "Item Icons", TwinColSelectExample.class),
            new BookExample("component.select.twincolselect.css", "Styling with CSS", TwinColSelectExample.class),
            new ExampleCtgr("component.table-", "5.12. Table"),
            //new ExampleItem("component.table.overview-", "Overview"),
            //new ExampleItem("component.table.overview.layouts", "Layouts in Table", TableExample.class),
            new RedirctItem("component.table.overview.component", "component.table.components.components"),
            new BookExample("component.table.basic", "Basic Use", TableExample.class),
            new ExampleCtgr("component.table.selecting-", "5.12.1 Selecting Items"),
            new BookExample("component.table.selecting.single", "Single Selection Mode", TableExample.class),
            new BookExample("component.table.selecting.multi", "Multiple Selection Mode", TableExample.class),
            new ExampleCtgr("component.table.features-", "5.12.2 Table Features"),
            new BookExample("component.table.features.columnresize", "Resizing Columns", TableExample.class),
            new BookExample("component.table.features.columnreordering", "Reordering Columns", TableExample.class),
            new BookExample("component.table.features.columncollapsing", "Collapsing (Hiding) Columns", TableExample.class),
            new RedirctItem("component.table.features.varyingrows", "component.table.components.varyingrows"),
            new RedirctItem("component.table.features.cellrenderer", "component.table.components.cellrenderer"),
            new BookExample("component.table.features.scrolltoitem", "Scrolling to Specific Item", TableExample.class),
            new BookExample("component.table.features.removeallitems", "Removing All Items", TableExample.class),
            new ExampleCtgr("component.table.components-", "5.12.x Components in Table"),
            new BookExample("component.table.components.components", "Basic Use", TableExample.class),
            new BookExample("component.table.components.interactingcomponents", "Interaction within row", TableExample.class),
            new BookExample("component.table.components.beancomponents", "Bean Components", TableExample.class),
            new BookExample("component.table.components.varyingrows", "Varying Row Heights", TableExample.class),
            new BookExample("component.table.components.varyingheightlabels", "Multi-row Labels", TableExample.class),
            new BookExample("component.table.components.cellrenderer", "Pattern: Cell Renderer", TableExample.class),
            new ExampleCtgr("component.table.headersfooters-", "5.12.3 Column Headers and Footers"),
            new BookExample("component.table.headersfooters.headers", "Headers", TableExample.class),
            new BookExample("component.table.headersfooters.fakeheaders", "Fake Headers", TableExample.class),
            new BookExample("component.table.headersfooters.htmlheaders", "HTML in Headers", TableExample.class),
            new ExampleCtgr("component.table.headersfooters.footers-", "Footers"),
            new BookExample("component.table.headersfooters.footers.footer-basic", "Calculating Sum", TableExample.class),
            new BookExample("component.table.headersfooters.footers.footer-sum", "Interactive Calculation", TableExample.class),
            new BookExample("component.table.headersfooters.headerclick", "Handling Clicks on Headers and Footers", TableExample.class),
            new BookExample("component.table.rowheaders", "5.12.x Row Headers", TableExample.class),
            new BookExample("component.table.binding", "5.12.x Data Binding", TableExample.class),
            new BookExample("component.table.binding.editorform", "5.12.x Item Editor Form", TableExample.class),
            new BookExample("component.table.editable-", "5.12.x Editable Mode", TableExample.class),
            new BookExample("component.table.editable.editable", "Table in Editable Mode", TableExample.class),
            new BookExample("component.table.editable.buffering", "Buffering", TableExample.class),
            new BookExample("component.table.editable.spreadsheet", "Editing on Demand", TableExample.class),
            new BookExample("component.table.editable.editableheights", "Height of Editables", TableExample.class),
            //new ExampleItem("component.table.editable.combobox", "ComboBoxes in Table (test)", TableExample.class),
            new BookExample("component.table.ratios", "5.12.x Column Expand Ratios", TableExample.class),
            // new ExampleItem("component.table.filtering", "5.12.x Filtering", TableExample.class),
            new ExampleCtgr("component.table.generatedcolumns-", "5.12.x Generated Columns"),
            new BookExample("component.table.generatedcolumns.layoutclick", "Layout Click Problem", GeneratedColumnExample.class),
            new BookExample("component.table.generatedcolumns.extended", "Extended Example", GeneratedColumnExample.class),
            new ExampleCtgr("component.table.columnformatting-", "5.12.6 Formatting Table Columns"),
            new BookExample("component.table.columnformatting.columnformatting-simple", "Simple Case", TableExample.class),
            new BookExample("component.table.columnformatting.columnformatting-extended", "Extended Example", TableExample.class),
            // new ExampleItem("component.table.columnformatting.columnformatting-component", "Formatting Value as Component", TableExample.class),
            new ExampleCtgr("component.table.styling-", "5.12.7 Styling with CSS"),
            new BookExample("component.table.styling.cellstylegenerator", "Cell Style Generator", TableExample.class),
            new BookExample("component.table.styling.rowstyle", "Row Style", TableExample.class),
            new BookExample("component.table.styling.cssinjection", "CSS Injection", TableExample.class),
            // new ExampleItem("component.table.propertyformatter", "5.12.x Formatting Columns with PropertyFormatter", TableExample.class),
            new ExampleCtgr("component.tree-", "5.13. Tree"),
            new BookExample("component.tree.basic", "Basic Use", TreeExample.class),
            new BookExample("component.tree.expanding", "Expanding Nodes", TreeExample.class),
            new BookExample("component.tree.expandlistener", "Node Expand Events", TreeExample.class),
            new BookExample("component.tree.collapselistener", "Node Collapse Events", TreeExample.class),
            new BookExample("component.tree.disable", "Disabling items", TreeExample.class),
            new BookExample("component.tree.itemclicklistener", "Item Click Events", TreeExample.class),
            new BookExample("component.tree.itemstylegenerator", "Item Style Generator", TreeExample.class),
            new ExampleCtgr("component.treetable-", "5.xx. TreeTable"),
            new BookExample("component.treetable.basic", "Basic Use", TreeTableExample.class),
            new BookExample("component.treetable.components", "Components in Tree", TreeTableExample.class),
            new BookExample("component.treetable.additemafter", "Adding Item After Another", TreeTableExample.class),
            new BookExample("component.treetable.draganddrop", "Drag and Drop", TreeTableExample.class),
            new BookExample("component.treetable.itemstylegenerator", "Cell Style for Tree Column", TreeTableExample.class),
            new ExampleCtgr("component.menubar-", "5.14. MenuBar"),
            new BookExample("component.menubar.basic", "Basic Use", MenuBarExample.class),
            new RedirctItem("component.embedded", "component.embedded.basic"),
            new BookExample("component.embedded-", "5.15. Embedded", EmbeddedExample.class),
            new BookExample("component.embedded.basic", "Basic Use", EmbeddedExample.class),
            new BookExample("component.embedded.scrolling-panel", "Scrolling in Panel", EmbeddedExample.class),
            new BookExample("component.embedded.scrolling-css", "Scrolling with CSS", EmbeddedExample.class),
            new BookExample("component.embedded.googlemaps", "Google Maps", EmbeddedExample.class),
            new ExampleCtgr("component.upload-", "5.x. Upload"),
            new BookExample("component.upload.basic", "Basic Use", UploadExample.class),
            new ExampleCtgr("component.form-", "5.17. Form"),
            new BookExample("component.form.visibleproperties", "Visible Item Properties", FormExample.class),
            new BookExample("component.form.buffering", "Buffering", FormExample.class),
            new BookExample("component.form.select", "Field interaction", FormExample.class),
            new BookExample("component.form.attachfield", "The attachField() method I", FormExample.class),
            new BookExample("component.form.attachfield2", "The attachField() method II", FormExample.class),
            new BookExample("component.form.propertyfiltering", "Filtering Properties", FormExample.class),
            new RedirctItem("component.form.nested", "component.form.subform.nestedtable"),
            new BookExample("component.form.layout-", "Alternative Layouts for Form", FormExample.class),
            new BookExample("component.form.layout.customlayout", "CustomLayout", FormExample.class),
            new BookExample("component.form.layout.gridlayout", "GridLayout", FormExample.class),
            new ExampleCtgr("component.form.subform-", "Nested Beans and Subforms"),
            new BookExample("component.form.subform.boundselect", "Property Bound to a Select", FormExample.class),
            new BookExample("component.form.subform.nestedtable", "Property Bound to a Nested Table", FormExample.class),
            new BookExample("component.form.subform.nestedforms", "Subforms in a Nested Table", FormExample.class),
            new ExampleCtgr("component.form.styling-", "Styling with CSS"),
            new BookExample("component.form.styling.wrapcaptions", "Wrapping Captions", FormExample.class),
            new ExampleCtgr("component.progressindicator-", "5.18. ProgressIndicator"),
            new BookExample("component.progressindicator.simple", "Basic Use", ProgressIndicatorExample.class),
            new BookExample("component.progressindicator.counter", "Polling", ProgressIndicatorExample.class),
            new BookExample("component.progressindicator.thread", "Worker Thread", ProgressIndicatorExample.class),
            new BookExample("component.slider", "5.19. Slider", SliderExample.class),
            new ExampleCtgr("component.loginform-", "5.20. LoginForm"),
            new BookExample("component.loginform.basic", "Basic Use", LoginFormExample.class),
            new BookExample("component.loginform.tomcatconversion", "Encoding issue in Tomcat", LoginFormExample.class),
            new BookExample("component.loginform.customization", "5.20.1 Customization", LoginFormExample.class),
            new ExampleCtgr("component.customcomponent-", "5.21. CustomComponent"),
            new BookExample("component.customcomponent.basic", "Basic Use", CustomComponentExample.class),
            new BookExample("component.customcomponent.joining", "Joining Components", CustomComponentExample.class),
            new BookExample("component.customcomponent.customfield", "CustomField", CustomComponentExample.class),
            new ExampleCtgr("layout", "Chapter 6. Managing Layout"),
            new ExampleCtgr("layout.overview-", "6.1. Overview"),
            new BookExample("layout.overview.catfinder", "Cat Finder", LayoutExample.class),
            new BookExample("layout.overview.traversal", "Traversal", LayoutExample.class),
            new ExampleCtgr("layout.layoutfeatures-", "6.x. Layout Features"),
            new BookExample("layout.layoutfeatures.layoutclick", "6.x.x Layout Click Events", LayoutFeaturesExample.class),
            new ExampleCtgr("layout.window-", "6.x. Window"),
            new BookExample("layout.window.mainwindowsize", "Main Window Size", ApplicationWindowExample.class),
            new ExampleCtgr("layout.orderedlayout-", "6.3. VerticalLayout and HorizontalLayout"),
            new BookExample("layout.orderedlayout.basic", "Basic Use", OrderedLayoutExample.class),
            new ExampleCtgr("layout.orderedlayout.sizing", "Sizing Contained Components"),
            new BookExample("layout.orderedlayout.sizing.sizing-undefined-defining", "Defining with Contained Size", OrderedLayoutExample.class),
            new ExampleCtgr("layout.gridlayout-", "6.4. GridLayout"),
            new BookExample("layout.gridlayout.basic", "Basic", GridLayoutExample.class),
            new BookExample("layout.gridlayout.expandratio", "Expand ratio", GridLayoutExample.class),
            new ExampleCtgr("layout.panel-", "6.x. Panel"),
            new BookExample("layout.panel.basic", "Basic Use", PanelExample.class),
            new BookExample("layout.panel.scroll", "Scrolling", PanelExample.class),
            new ExampleCtgr("layout.absolutelayout-", "6.x. AbsoluteLayout"),
            new BookExample("layout.absolutelayout.basic", "Basic Use", AbsoluteLayoutExample.class),
            new BookExample("layout.absolutelayout.bottomright", "Bottom-Right Relative Coordinates", AbsoluteLayoutExample.class),
            new BookExample("layout.absolutelayout.area", "Area", AbsoluteLayoutExample.class),
            new BookExample("layout.absolutelayout.zindex", "Z-index", AbsoluteLayoutExample.class),
            new BookExample("layout.absolutelayout.proportional", "Proportional Coordinates", AbsoluteLayoutExample.class),
            new ExampleCtgr("layout.csslayout-", "6.x. CssLayout"),
            new BookExample("layout.csslayout.basic", "Basic Use", CssLayoutExample.class),
            new BookExample("layout.csslayout.styling", "Styling", CssLayoutExample.class),
            new BookExample("layout.csslayout.flow", "Flow Layout", CssLayoutExample.class),
            new ExampleCtgr("layout.splitpanel-", "6.x. SplitPanel"),
            new BookExample("layout.splitpanel.basic", "Basic Use", SplitPanelExample.class),
            new BookExample("layout.splitpanel.splitposition", "Split Position and Locking", SplitPanelExample.class),
            new BookExample("layout.splitpanel.small", "Small Style", SplitPanelExample.class),
            new BookExample("layout.splitpanel.styling", "Styling", SplitPanelExample.class),
            new ExampleCtgr("layout.tabsheet-", "6.x. TabSheet"),
            new BookExample("layout.tabsheet.basic", "Basic Use", TabSheetExample.class),
            new BookExample("layout.tabsheet.tabchange", "Handling Tab Changes", TabSheetExample.class),
            new BookExample("layout.tabsheet.styling", "Styling with CSS", TabSheetExample.class),
            new ExampleCtgr("layout.popupview-", "6.x. PopupView"),
            new BookExample("layout.popupview.composition", "Composition", PopupViewExample.class),
            new ExampleCtgr("layout.formatting-", "6.10. Layout Formatting"),
            new BookExample("layout.formatting.margin", "Margin", MarginExample.class),
            new ExampleCtgr("layout.formatting.spacing", "Spacing"),
            new BookExample("layout.formatting.spacing.vertical", "Vertical", SpacingExample.class),
            new BookExample("layout.formatting.spacing.horizontal", "Horizontal", SpacingExample.class),
            new BookExample("layout.formatting.spacing.grid", "Grid", SpacingExample.class),
            new ExampleCtgr("layout.formatting.alignment-", "Alignment"),
            new BookExample("layout.formatting.alignment.gridlayout", "In GridLayout", AlignmentExample.class),
            new BookExample("layout.formatting.alignment.verticallayout", "In VerticalLayout", AlignmentExample.class),
            new BookExample("layout.formatting.alignment.maxwidth", "Taking Maximum Width", AlignmentExample.class),
            new ExampleCtgr("layout.formatting.expandratio-", "Expand Ratios"),
            new BookExample("layout.formatting.expandratio.basic", "Basic Use", ExpandRatioExample.class),
            new BookExample("layout.formatting.expandratio.horizontal", "In a Horizontal Layout", ExpandRatioExample.class),
            new BookExample("layout.formatting.expandratio.summary", "Summary", ExpandRatioExample.class),
            new ExampleCtgr("layout.customlayout-", "6.13. CustomLayout"),
            new BookExample("layout.customlayout.basic", "Basic Use", CustomLayoutExample.class),
            new BookExample("layout.customlayout.stream", "Template from Stream", CustomLayoutExample.class),
            new BookExample("layout.customlayout.maxwidth", "Taking Maximum Width", CustomLayoutExample.class),
            new BookExample("layout.customlayout.styling", "Styling", CustomLayoutExample.class),
            new ExampleCtgr("themes", "Chapter 8. Themes"),
            new ExampleCtgr("themes.creating-", "8.3. Creating and Using Themes"),
            new ExampleCtgr("themes.creating.default-", "8.3.2. Built-in Themes"),
            new BookExample("themes.creating.default.runo", "Runo Theme", BuiltInThemeExample.class),
            new ExampleCtgr("themes.misc-", "8.x. Miscellaneous Problems"),
            new BookExample("themes.misc.pointertypes", "8.x.x. Pointer Types", ThemeTricksExample.class),
            new ExampleCtgr("datamodel", "Chapter 9. Binding Components to Data"),
            new ExampleCtgr("datamodel.overview", "9.1. Overview"),
            new ExampleCtgr("datamodel.properties-", "9.2. Properties"),
            new BookExample("datamodel.properties.basic", "Setting and Getting Property Values", PropertyExample.class),
            new BookExample("datamodel.properties.propertyviewer", "Property Viewer", PropertyExample.class),
            new BookExample("datamodel.properties.propertyeditor", "Property Editor", PropertyExample.class),
            new BookExample("datamodel.properties.objectproperty", "ObjectProperty Implementation", PropertyExample.class),
            new BookExample("datamodel.properties.implementation", "Implementing Property Interface", PropertyExample.class),
            new BookExample("datamodel.properties.valuechangenotifier", "Implementing Value Change Notifier", PropertyExample.class),
            new ExampleCtgr("datamodel.items-", "9.3. Holding Properties in Items"),
            new BookExample("datamodel.items.propertysetitem", "PropertysetItem Implementation", ItemExample.class),
            new BookExample("datamodel.items.beanitem", "BeanItem", ItemExample.class),
            new BookExample("datamodel.items.beanitem.beanitem-basic", "Basic Use", ItemExample.class),
            new BookExample("datamodel.items.beanitem.nestedbean", "Nested Beans", ItemExample.class),
            new BookExample("datamodel.items.beanitem.doublebinding", "Multiple Binding", ItemExample.class),
            new BookExample("datamodel.items.beanitem.valuechangenotifier", "Notifying Value Changes in Bean", ItemExample.class),
            new BookExample("datamodel.items.implementing", "Implementing the Item Interface", ItemExample.class),
            new ExampleCtgr("datamodel.container-", "9.4. Collecting Items in Containers"),
            new ExampleCtgr("datamodel.container.hierarchical-", "9.x.x Hierarchical"),
            new BookExample("datamodel.container.hierarchical.implementing", "Implementing Hierarchical", HierarchicalExample.class),
            new ExampleCtgr("datamodel.container.beancontainer-", "9.4.x BeanContainer"),
            new BookExample("datamodel.container.beancontainer.basic", "Basic Use", BeanContainerExample.class),
            new ExampleCtgr("datamodel.container.beanitemcontainer-", "9.4.x BeanItemContainer"),
            new BookExample("datamodel.container.beanitemcontainer.basic", "Basic Use", BeanItemContainerExample.class),
            new BookExample("datamodel.container.beanitemcontainer.dualbinding", "Binding to Table and Form", BeanItemContainerExample.class),
            new BookExample("datamodel.container.beanitemcontainer.nestedbean", "Nested Beans", BeanItemContainerExample.class),
            new BookExample("datamodel.container.beanitemcontainer.addall", "Add All From Collection", BeanItemContainerExample.class),
            new ExampleCtgr("datamodel.container.filesystemcontainer-", "9.4.x FileSystemContainer"),
            new BookExample("datamodel.container.filesystemcontainer.basic", "Basic Use", FilesystemContainerExample.class),
            new ExampleCtgr("datamodel.container.filter-", "9.4.x Filtering Containers"),
            new BookExample("datamodel.container.filter.basic", "Basic Use", ContainerFilterExample.class),
            new BookExample("datamodel.container.filter.like", "Like filter", ContainerFilterExample.class),
            new BookExample("datamodel.container.filter.tree", "Filtered Tree", ContainerFilterExample.class),
            new BookExample("datamodel.container.filter.custom", "Custom Filter", ContainerFilterExample.class),
            new ExampleCtgr("advanced", "Chapter 12. Advanced Web Application Topics"),
            new ExampleCtgr("advanced.applicationwindow-", "12.x. Application-level Windows"),
            new BookExample("advanced.applicationwindow.popup", "Popup Windows", ApplicationWindowExample.class),
            new BookExample("advanced.applicationwindow.tab", "Opening as Tab", ApplicationWindowExample.class),
            new BookExample("advanced.applicationwindow.closing", "Close Event", ApplicationWindowExample.class),
            new BookExample("advanced.applicationwindow.dynamic", "Dynamic Creation of Windows", ApplicationWindowExample.class),
            new BookExample("advanced.applicationwindow.automatic", "Multiple Book Example Windows", ApplicationWindowExample.class),
            new ExampleCtgr("advanced.embedding-", "12.3. Embedding Applications in Web Pages"),
            new BookExample("advanced.embedding.div", "Embedding in <div>", EmbeddingExample.class),
            new ExampleCtgr("advanced.shortcut-", "12.6. Shortcut Keys"),
            new BookExample("advanced.shortcut.defaultbutton", "Default Button", ShortcutExample.class),
            new BookExample("advanced.shortcut.focus", "Focus Shortcuts", ShortcutExample.class),
            new BookExample("advanced.shortcut.modifier", "Modifier Keys", ShortcutExample.class),
            new ExampleCtgr("advanced.printing-", "12.7. Printing"),
            new BookExample("advanced.printing.this", "Print This Page", PrintingExample.class),
            new BookExample("advanced.printing.open", "Open Page to Print", PrintingExample.class),
            new BookExample("advanced.printing.pdfgeneration", "PDF Generation", PrintingExample.class),
            new ExampleCtgr("advanced.urifragmentutility-", "11.11. UriFragmentUtility"),
            new BookExample("advanced.urifragmentutility.uriexample", "Basic Use", UriFragmentExample.class),
            new BookExample("advanced.urifragmentutility.indexing", "Seach Engine Indexing Support", UriFragmentExample.class),
            new ExampleCtgr("advanced.servletrequestlistener-", "11.12. Listening for Server Requests"),
            new BookExample("advanced.servletrequestlistener.introduction", "Introduction", ServletRequestListenerExample.class),
            new BookExample("advanced.servletrequestlistener.cookies", "11.12.2. Managing Cookies", ServletRequestListenerExample.class),
            new ExampleCtgr("advanced.dragndrop-", "11.13. Drag & Drop"),
            new ExampleCtgr("advanced.dragndrop.tree-", "11.13.2 Dropping Items On a Tree"),
            new BookExample("advanced.dragndrop.tree.treedropcriterion", "Tree Drop Criteria", DragNDropTreeExample.class),
            new ExampleCtgr("advanced.dragndrop.table-", "11.13.3 Dropping Items On a Table"),
            new BookExample("advanced.dragndrop.table.treeandtable", "Tree and Table", TreeAndTableExample.class),
            new ExampleCtgr("advanced.dragndrop.accept-", "11.13.4. Accepting Drops"),
            new BookExample("advanced.dragndrop.accept.serverside", "Server-Side Criteria", DragNDropTreeExample.class),
            new ExampleCtgr("advanced.dragndrop.component-", "11.13.6. Dropping on a Component"),
            new BookExample("advanced.dragndrop.component.basic", "Basic Use", ComponentDnDExample.class),
            new BookExample("advanced.dragndrop.component.absolute", "Absolute Drop Position", ComponentDnDExample.class),
            new BookExample("advanced.dragndrop.component.resize", "Resizing Components", ComponentDnDExample.class),
            new BookExample("advanced.dragndrop.component.diagram", "Moving Connected Components", DiagramDnDExample.class),
            new ExampleCtgr("advanced.urihandler-", "11.x. URI Handler"),
            new BookExample("advanced.urihandler.staticlogin", "Login from an External Page", UriHandlerExample.class),
            new ExampleCtgr("advanced.logging-", "11.x. Logging"),
            new BookExample("advanced.logging.basic", "Basic Use", LoggingExample.class),
            new ExampleCtgr("advanced.browserinfo-", "11.x. Browser Info"),
            new BookExample("advanced.browserinfo.basic", "Basic", BrowserInfoExample.class),
            new ExampleCtgr("advanced.i18n-", "11.x. Internationalization"),
            new BookExample("advanced.i18n.bundles", "Basic i18n Using Bundles", I18NExample.class),
            new BookExample("advanced.i18n.rtl", "Right-to-Left Languages", I18NExample.class),
            new ExampleCtgr("advanced.jsapi-", "11.x. JavaScript API"),
            new BookExample("advanced.jsapi.status", "Setting Status Message", JSAPIExample.class),
            new BookExample("advanced.jsapi.jsreception", "Receiving Input from JS", JSAPIExample.class),
            new ExampleCtgr("advanced.global-", "11.x Accessing Session Data Globally"),
            new BookExample("advanced.global.threadlocal", "ThreadLocal Pattern", GlobalAccessExample.class),
            new ExampleCtgr("calendar-", "Chapter 16. Vaadin Calendar Add-on"),
            new BookExample("calendar.basic", "Basic Use", CalendarExample.class),
            new BookExample("calendar.monthlyview", "Monthly View", CalendarExample.class),
            new BookExample("calendar.contextmenu", "Context Menu", CalendarExample.class),
            new BookExample("calendar.beanitemcontainer", "Binding to BeanItemContainer", CalendarExample.class),
            new BookExample("calendar.jpacontainer", "Binding to JPAContainer", CalendarExample.class),
            new ExampleCtgr("jpacontainer-", "Chapter 18. Vaadin JPAContainer Add-on"),
            new BookExample("jpacontainer.nonpersistent", "Non-persistent Binding", JPAContainerExample.class),
            new BookExample("jpacontainer.basic", "Basic Use", JPAContainerExample.class),
            new BookExample("jpacontainer.thehardway", "The Hard Way", JPAContainerExample.class),
            new BookExample("jpacontainer.nested", "Nested Properties", JPAContainerExample.class),
            new BookExample("jpacontainer.buffering", "Buffering", JPAContainerExample.class),
            new BookExample("jpacontainer.hierarchical", "Hierarchical Support", JPAHierarhicalExample.class),
            new BookExample("jpacontainer.hierarchical.basic", "Basic Use", JPAHierarhicalExample.class),
            new BookExample("jpacontainer.hierarchical.childrenallowed", "Disabling Node Expansion", JPAHierarhicalExample.class),
            new ExampleCtgr("jpacontainer.filtering", "18.x. Filtering"),
            new BookExample("jpacontainer.filtering.basic", "Basic Filtering", JPAFilteringExample.class),
            new BookExample("jpacontainer.filtering.entity", "Filtering by Entity", JPAFilteringExample.class),
            new ExampleCtgr("jpacontainer.criteria", "18.x. Querying with the Criteria API"),
            new BookExample("jpacontainer.criteria.querymodification", "Simple Criteria Query", JPAFilteringExample.class),
            new ExampleCtgr("jpacontainer.fieldfactory", "18.x. FieldFactory"),
            new BookExample("jpacontainer.fieldfactory.formonetoone", "Form with One-to-Many Relationship", JPAFieldFactoryExample.class),
            new BookExample("jpacontainer.fieldfactory.masterdetail", "Master-Detail Editor Manually", JPAFieldFactoryExample.class),
            new ExampleCtgr("misc-", "x. Miscellaneous Examples"),
            new ExampleCtgr("misc.exception-", "x.x Exceptions"),
            new BookExample("misc.exception.concurrentmodification", "Concurrent Modification", ExceptionExamples.class),
            new ExampleCtgr("misc.prototypes-", "x.x Prototypes"),
            new ExampleCtgr("misc.prototypes.i18n-", "Internationalization"),
            new BookExample("misc.prototypes.i18n.basic", "Basic Prototype", I18nPrototype.class),
            new ExampleCtgr("misc.serialization-", "x.x Serialization"),
            new BookExample("misc.serialization.basic", "Basic Serialization", SerializationExample.class),
            //new ExampleItem("misc.itemcontainer-", "x.x. ItemContainer"),
            //new ExampleItem("misc.itemcontainer.basic", "Basic Use", ItemContainerExample.class),
        };
}
