package com.vaadin.book.examples.component;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

/**
 * This example demonstrates the use of generated columns in a table. Generated
 * columns can be used for formatting values or calculating them from other
 * columns (or properties of the items).
 * 
 * For the data model, we use POJOs bound to a custom Container with BeanItem
 * items.
 * 
 * @author magi
 */
public class GeneratedColumnExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -2916085990831946682L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("layoutclick".equals(context))
            layoutclick(layout);
        else if ("extended".equals(context))
            extended(layout);
        
        setCompositionRoot(layout);
    }
    
    public final static String layoutclickDescription =
        "<h1>Problem with Selection in a Generated Column with a Layout</h1>" +
        "<p>If you have certain layouts in a generated column, they " +
        "prevent selection in the table. You need to define a " +
        "<b>LayoutClickListener</b> for the layouts and, when clicked, " +
        "select the table row.</p>";
    
    void layoutclick(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.table.generatedcolumns.layoutclick
        // Have a table with one regular column
        final Table table = new Table();
        table.addContainerProperty("number", Integer.class, null);
        
        // Have some data in the table
        for (int i=1; i<=100; i++)
            table.addItem(new Object[]{i}, i);
        
        table.addGeneratedColumn("generated", new ColumnGenerator() {
            private static final long serialVersionUID = -8133822448459398254L;

            @Override
            public Component generateCell(Table source,
                    final Object itemId, Object columnId) {
                // Get the value in the first column
                int num = (Integer) source
                    .getContainerProperty(itemId, "number").getValue();

                // Create the component for the generated column
                VerticalLayout cellLayout = new VerticalLayout();
                cellLayout.addComponent(new Label("Row " + num));
                cellLayout.addComponent(new Label("Square " + num*num));
                cellLayout.addComponent(new Label("Cube " + num*num*num));

                // Forward clicks on the layout as selection
                // in the table
                cellLayout.addListener(new LayoutClickListener() {
                    private static final long serialVersionUID = 606542398049493724L;

                    @Override
                    public void layoutClick(LayoutClickEvent event) {
                        table.select(itemId);
                    }
                });
                return cellLayout;
            }
        });
        
        table.setSelectable(true);

        // TODO: Remove once fixed
        Button button = new Button("Select some item - this is a test for the flicker");
        button.addListener(new ClickListener() {
            private static final long serialVersionUID = -2548930324857857404L;
            int item = 1;
            @Override
            public void buttonClick(ClickEvent event) {
                table.select(item++);
            }
        });
        // END-EXAMPLE: component.table.generatedcolumns.layoutclick
        
        table.setPageLength(5);
        
        layout.addComponent(table);
        layout.addComponent(button);
    }
    
    // BEGIN-EXAMPLE: component.table.generatedcolumns.extended
    /**
     * The business model: fill-up at a gas station.
     */
    public class FillUp implements Serializable {
        private static final long serialVersionUID = -5909762375694974599L;

        Date date;
        double quantity;
        double total;

        public FillUp() {
        }

        public FillUp(int day, int month, int year, double quantity,
                double total) {
            date = new GregorianCalendar(year, month - 1, day).getTime();
            this.quantity = quantity;
            this.total = total;
        }

        /** Calculates price per unit of quantity (€/l). */
        public double price() {
            if (quantity != 0.0) {
                return total / quantity;
            } else {
                return 0.0;
            }
        }

        /** Calculates average daily consumption between two fill-ups. */
        public double dailyConsumption(FillUp other) {
            double difference_ms = date.getTime() - other.date.getTime();
            double days = difference_ms / 1000 / 3600 / 24;
            if (days < 0.5) {
                days = 1.0; // Avoid division by zero if two fill-ups on the
                // same day.
            }
            return quantity / days;
        }

        /** Calculates average daily consumption between two fill-ups. */
        public double dailyCost(FillUp other) {
            return price() * dailyConsumption(other);
        }

        // Getters and setters

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    };

    /** Formats the dates in a column containing Date objects. */
    class DateColumnGenerator implements Table.ColumnGenerator {
        private static final long serialVersionUID = 3741451390162331681L;

        /**
         * Generates the cell containing the Date value. The column is
         * irrelevant in this use case.
         */
        public Component generateCell(Table source, Object itemId,
                Object columnId) {
            Property prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(Date.class)) {
                Label label = new Label(String.format("%tF",
                        new Object[] { (Date) prop.getValue() }));
                label.addStyleName("column-type-date");
                return label;
            }

            return null;
        }
    }

    /** Formats the value in a column containing Double objects. */
    class ValueColumnGenerator implements Table.ColumnGenerator {
        private static final long serialVersionUID = -1365109458526609567L;

        String format; /* Format string for the Double values. */

        /** Creates double value column formatter with the given format string. */
        public ValueColumnGenerator(String format) {
            this.format = format;
        }

        /**
         * Generates the cell containing the Double value. The column is
         * irrelevant in this use case.
         */
        public Component generateCell(Table source, Object itemId,
                Object columnId) {
            Property prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(Double.class)) {
                Label label = new Label(String.format(format,
                        new Object[] { (Double) prop.getValue() }));

                // Set styles for the column: one indicating that
                // it's a value and a more specific one with the
                // column name in it. This assumes that the column
                // name is proper for CSS.
                label.addStyleName("column-type-value");
                label.addStyleName("column-" + (String) columnId);
                return label;
            }
            return null;
        }
    }

    /** Table column generator for calculating price column. */
    class PriceColumnGenerator implements Table.ColumnGenerator {
        private static final long serialVersionUID = 8970892155507886020L;

        public Component generateCell(Table source, Object itemId,
                Object columnId) {
            // Retrieve the item.
            BeanItem<?> item = (BeanItem<?>) source.getItem(itemId);

            // Retrieves the underlying POJO from the item.
            final FillUp fillup = (FillUp) item.getBean();

            // Create the generated component for displaying the calculated
            // value.
            final Label label = new Label(calculateAndFormatValue(fillup));

            // Refresh the label always when the source values change
            ValueChangeListener listener = new ValueChangeListener() {
                private static final long serialVersionUID = 8166986643301845612L;

                @Override
                public void valueChange(ValueChangeEvent event) {
                    label.setValue(calculateAndFormatValue(fillup));
                }
            };
            
            // We must know from the business object that the
            // calculation of the "price" property depends on these
            // properties
            for (String pid: new String[]{"quantity", "total"})
                ((ValueChangeNotifier)item.getItemProperty(pid)).addListener(listener);
            
            // We set the style here. You can't use a CellStyleGenerator for
            // generated columns.
            label.addStyleName("column-price");
            return label;
        }
        
        String calculateAndFormatValue(FillUp fillup) {
            return String.format("%1.2f €", fillup.price());            
        }
    }

    /** Table column generator for calculating consumption column. */
    class ConsumptionColumnGenerator implements Table.ColumnGenerator {
        private static final long serialVersionUID = 2742357693979144870L;

        /**
         * Generates a cell containing value calculated from the item.
         */
        public Component generateCell(final Table source,
                final Object itemId, Object columnId) {
            final Indexed container = (Indexed) source.getContainerDataSource();

            // Can not calculate consumption for the first item.
            if (container.isFirstId(itemId)) {
                Label label = new Label("N/A");
                label.addStyleName("column-consumption");
                return label;
            }

            // Index of the previous item.
            final Object prevItemId = container.prevItemId(itemId);
            
            // Retrieve the POJOs the calculated value depends on.
            final FillUp fillup = (FillUp) ((BeanItem<?>) container.getItem(itemId)).getBean();
            final FillUp prev   = (FillUp) ((BeanItem<?>) source.getItem(prevItemId)).getBean();
            
            // Generate the component for displaying the calculated value.
            final Label label = new Label(calculateValue(fillup, prev));

            // Refresh the label always when the source values change
            ValueChangeListener listener = new ValueChangeListener() {
                private static final long serialVersionUID = 8166986643301845612L;

                @Override
                public void valueChange(ValueChangeEvent event) {
                    label.setValue(calculateValue(fillup, prev));
                }
            };
            final Property quantProp = container.getContainerProperty(itemId, "quantity");
            final Property totalProp = container.getContainerProperty(itemId, "total");
            ((ValueChangeNotifier)quantProp).addListener(listener);
            ((ValueChangeNotifier)totalProp).addListener(listener);
            
            // We set the style here. You can't use a CellStyleGenerator for
            // generated columns.
            label.addStyleName(getColumnStyle());
            
            return label;
        }

        public String calculateValue(FillUp fillup, FillUp prev) {            
            return String.format("%3.2f l", fillup.dailyConsumption(prev));
        }
        
        String getColumnStyle() {
            return "column-consumption";
        }
    }

    /** Table column generator for calculating daily cost column. */
    class DailyCostColumnGenerator extends ConsumptionColumnGenerator {
        private static final long serialVersionUID = -6676173701305381931L;

        @Override
        public String calculateValue(FillUp fillup, FillUp prev) {
            return String.format("%3.2f €", fillup.dailyCost(prev));
        }
        
        @Override
        String getColumnStyle() {
            return "column-dailycost";
        }
    }

    /**
     * Custom field factory that sets the fields as immediate.
     */
    public class ImmediateFieldFactory extends DefaultFieldFactory {
        private static final long serialVersionUID = 7067711376117004831L;

        @Override
        public Field createField(Container container, Object itemId,
                Object propertyId, Component uiContext) {
            // Let the DefaultFieldFactory create the fields
            Field field = super.createField(container, itemId,
                                            propertyId, uiContext);

            // ...and just set them as immediate
            ((AbstractField) field).setImmediate(true);

            return field;
        }
    }

    void extended(VerticalLayout layout) {
        final Table table = new Table();

        // Define table columns. These include also the column for the generated
        // column, because we want to set the column label to something
        // different than the property ID.
        table.addContainerProperty("date",        Date.class,   null, "Date", null, null);
        table.addContainerProperty("quantity",    Double.class, null, "Quantity (l)", null, null);
        table.addContainerProperty("price",       Double.class, null, "Price (€/l)", null, null);
        table.addContainerProperty("total",       Double.class, null, "Total (€)", null, null);
        table.addContainerProperty("consumption", Double.class, null, "Consumption (l/day)", null, null);
        table.addContainerProperty("dailycost",   Double.class, null, "Daily Cost (€/day)", null, null);

        // Define the generated columns and their generators.
        table.addGeneratedColumn("date", new DateColumnGenerator());
        table.addGeneratedColumn("quantity", new ValueColumnGenerator("%.2f l"));
        table.addGeneratedColumn("price", new PriceColumnGenerator());
        table.addGeneratedColumn("total", new ValueColumnGenerator("%.2f €"));
        table.addGeneratedColumn("consumption", new ConsumptionColumnGenerator());
        table.addGeneratedColumn("dailycost", new DailyCostColumnGenerator());

        // Create a data source and bind it to the table.
        BeanItemContainer<FillUp> data = new BeanItemContainer<FillUp>(FillUp.class); 
        table.setContainerDataSource(data);

        // Generated columns are automatically placed after property columns, so
        // we have to set the order of the columns explicitly.
        table.setVisibleColumns(new Object[] { "date", "quantity", "price",
                "total", "consumption", "dailycost" });

        // Add some data.
        data.addBean(new FillUp(19, 2,  2005, 44.96, 51.21));
        data.addBean(new FillUp(30, 3,  2005, 44.91, 53.67));
        data.addBean(new FillUp(20, 4,  2005, 42.96, 49.06));
        data.addBean(new FillUp(23, 5,  2005, 47.37, 55.28));
        data.addBean(new FillUp(6,  6,  2005, 35.34, 41.52));
        data.addBean(new FillUp(30, 6,  2005, 16.07, 20.00));
        data.addBean(new FillUp(2,  7,  2005, 36.40, 36.19));
        data.addBean(new FillUp(6,  7,  2005, 39.17, 50.90));
        data.addBean(new FillUp(27, 7,  2005, 43.43, 53.03));
        data.addBean(new FillUp(17, 8,  2005, 20,    29.18));
        data.addBean(new FillUp(30, 8,  2005, 46.06, 59.09));
        data.addBean(new FillUp(22, 9,  2005, 46.11, 60.36));
        data.addBean(new FillUp(14, 10, 2005, 41.51, 50.19));
        data.addBean(new FillUp(12, 11, 2005, 35.24, 40.00));
        data.addBean(new FillUp(28, 11, 2005, 45.26, 53.27));

        // Have a check box that allows the user to make the
        // quantity and total columns editable.
        final CheckBox editable = new CheckBox(
                "Edit the input values - calculated columns are regenerated");
        editable.setImmediate(true);
        editable.addListener(new ClickListener() {
            private static final long serialVersionUID = -2709935228261875406L;

            public void buttonClick(ClickEvent event) {
                table.setEditable(editable.booleanValue());

                // The columns may not be generated when we want to have them
                // editable.
                if (editable.booleanValue()) {
                    table.removeGeneratedColumn("quantity");
                    table.removeGeneratedColumn("total");
                } else {
                    // In non-editable mode we want to show the formatted
                    // values.
                    table.addGeneratedColumn("quantity",
                            new ValueColumnGenerator("%.2f l"));
                    table.addGeneratedColumn("total", new ValueColumnGenerator(
                            "%.2f €"));
                }
                // The visible columns are affected by removal and addition of
                // generated columns so we have to redefine them.
                table.setVisibleColumns(new Object[] { "date", "quantity",
                        "price", "total", "consumption", "dailycost" });
            }
        });

        // Use a custom field factory to set the edit fields as immediate.
        // This is used when the table is in editable mode.
        table.setTableFieldFactory(new ImmediateFieldFactory());

        // Setting the table itself as immediate has no relevance in this
        // example,
        // because it is relevant only if the table is selectable and we want to
        // get the selection changes immediately.
        table.setImmediate(true);

        table.setHeight("300px");

        layout.setMargin(true);
        layout.addComponent(new Label(
                "Table with column generators that format and calculate cell values."));
        layout.addComponent(table);
        layout.addComponent(editable);
        layout.addComponent(new Label(
                "Columns displayed in blue are calculated from Quantity and Total. "
                        + "Others are simply formatted."));
        layout.setExpandRatio(table, 1);
        layout.setSizeUndefined();
        // setSizeFull();
    }
    // END-EXAMPLE: component.table.generatedcolumns.extended
}
