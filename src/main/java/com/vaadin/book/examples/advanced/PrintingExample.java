package com.vaadin.book.examples.advanced;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PrintingExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("this".equals(context))
            printThisPage();
        else if ("open".equals(context))
            printOpenedPage();
        else if ("nonblocking".equals(context))
            printNonblockingPage();
        else if ("pdfgeneration".equals(context))
            pdfgeneration(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    void printThisPage () {
        // BEGIN-EXAMPLE: advanced.printing.this
        final Button print = new Button("Print This Page");
        print.addListener(new ClickListener() {
            private static final long serialVersionUID = 15335453452L;

            public void buttonClick(ClickEvent event) {
                print.getWindow().executeJavaScript("print();");
            }
        });
        // END-EXAMPLE: advanced.printing.this
        
        setCompositionRoot(print);
    }
    
    void printOpenedPage () {
        // BEGIN-EXAMPLE: advanced.printing.open
        // A button to open the printer-friendly page.
        Button print = new Button("Click to Print");

        print.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 6588417468637527327L;

			public void buttonClick(ClickEvent event) {
                // Create a window that contains what you want to print
                Window window = new Window("Window to Print");

                // Have some content to print
                window.addComponent(new Label(
                        "<h1>Here's some dynamic content</h1>\n" +
                        "<p>This is to be printed to the printer.</p>",
                        Label.CONTENT_XHTML));

                // Add the printing window as a new application-level
                // window
                getApplication().addWindow(window);

                // Open it as a popup window with no decorations
                getWindow().open(new ExternalResource(window.getURL()),
                        "_blank", 500, 200,  // Width and height 
                        Window.BORDER_NONE); // No decorations

                // Print automatically when the window opens.
                // This call will block until the print dialog exits!
                window.executeJavaScript("print();");
                
                // Close the window automatically after printing
                window.executeJavaScript("self.close();");
            }
        });
        // END-EXAMPLE: advanced.printing.open
        
        setCompositionRoot(print);
    }

    // TODO: This actually blocks also.
    void printNonblockingPage () {
        // A button to open the printer-friendly page.
        final Button print = new Button("Click to Print");

        print.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 349852897523897L;

            public void buttonClick(ClickEvent event) {
                // Content to be printed. Must double-quote newlines.
                String content = "<h1>Stuff to Print</h1>\\n" +
                                 "<p>Important stuff</p>\\n";

                // The code to print and close the window
                content += "<SCRIPT language=\"JavaScript\">" +
                           "  print();" +
                           "  close();" +
                           "</SCRIPT>";
                
                // Open the print window
                String js = "popup = window.open('', 'mywindow','status=1,width=350,height=150');\n" +
                            "popup.document.write('"+content+"');\n";
                print.getWindow().executeJavaScript(js);
            }
        });
        
        setCompositionRoot(print);
    }
    
    public final static String pdfgenerationDescription =
        "<h1>Generating a Printable PDF</h1>" +
        "<p>You can generate a PDF file dynamically using a <b>StreamResource</b>. The following example does it using the Apache FOP.</p>";

    // BEGIN-EXAMPLE: advanced.printing.pdfgeneration
    /** Generates the PDF dynamically when requested by HTTP. */
    class MyPdfSource implements StreamSource {
        private static final long serialVersionUID = 6580720404794033932L;

        String name; // A trivial content data model
        
        /** Constructor gets a content data model as parameter */
        public MyPdfSource(String name) {
            this.name = name;
        }
        
        @Override
        public InputStream getStream() {
            // Generate the FO content. You could use the Java DOM API
            // here as well and pass the DOM to the transformer.
            String fo = "<?xml version='1.0' encoding='ISO-8859-1'?>\n"+
            "<fo:root xmlns:fo='http://www.w3.org/1999/XSL/Format'>\n"+
            "<fo:layout-master-set>"+
            "  <fo:simple-page-master master-name='A4' margin='2cm'>"+
            "    <fo:region-body />"+
            "  </fo:simple-page-master>"+
            "</fo:layout-master-set>"+
            "<fo:page-sequence master-reference='A4'>"+
            "    <fo:flow flow-name='xsl-region-body'>"+
            "    <fo:block text-align='center'>"+
            "Hello There, "+ name + "!</fo:block>"+
            "  </fo:flow>"+
            "</fo:page-sequence>"+
            "</fo:root>\n";
            ByteArrayInputStream foStream =
                new ByteArrayInputStream(fo.getBytes());
            
            // Basic FOP configuration. You could create this object
            // just once and keep it.
            FopFactory fopFactory = FopFactory.newInstance();
            fopFactory.setStrictValidation(false); // For an example
            
            // Configuration for this PDF document - mainly metadata
            FOUserAgent userAgent = fopFactory.newFOUserAgent();
            userAgent.setProducer("My Vaadin Application");
            userAgent.setCreator("Me, Myself and I");
            userAgent.setAuthor("Da Author");
            userAgent.setCreationDate(new Date());
            userAgent.setTitle("Hello to " + name);
            userAgent.setKeywords("PDF Vaadin example");
            userAgent.setTargetResolution(300); // DPI

            // Transform to PDF
            ByteArrayOutputStream fopOut = new ByteArrayOutputStream();
            try {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
                        userAgent, fopOut);
                TransformerFactory factory =
                    TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                Source src = new
                    javax.xml.transform.stream.StreamSource(foStream);
                Result res = new SAXResult(fop.getDefaultHandler());
                transformer.transform(src, res);
                fopOut.close();
                return new ByteArrayInputStream(fopOut.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return null;
        }
    }
    
    void pdfgeneration(VerticalLayout layout) {
        // A user interface for a data model from which
        // the PDF is generated.
        final TextField name = new TextField("Name");
        name.setValue("Slartibartfast");

        Button print = new Button("Open PDF");
        print.addListener(new ClickListener() {
            private static final long serialVersionUID = 1269425538593656695L;

            @Override
            public void buttonClick(ClickEvent event) {
                // Create the PDF source and pass the data model to it
                StreamSource source =
                    new MyPdfSource((String) name.getValue());
                
                // Create the stream resource and give it a file name
                String filename = "pdf_printing_example.pdf";
                StreamResource resource = new StreamResource(source,
                        filename, getApplication());
                
                // These settings are not usually necessary. MIME type
                // is detected automatically from the file name, but
                // setting it explicitly may be necessary if the file
                // suffix is not ".pdf".
                resource.setMIMEType("application/pdf");
                resource.getStream().setParameter("Content-Disposition",
                        "attachment; filename="+filename);
                
                // Open it in this window - this will either launch
                // PDF viewer or let the user download the file. Could
                // use "_blank" target to open in another window, but
                // may not be necessary.
                getWindow().open(resource);
            }
        });
        
        layout.addComponent(name);
        layout.addComponent(print);
    }
    // END-EXAMPLE: advanced.printing.pdfgeneration
}
