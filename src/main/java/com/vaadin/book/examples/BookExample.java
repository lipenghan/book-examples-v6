package com.vaadin.book.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.ui.Component;

public class BookExample extends CaptionedExampleItem {
    private static final long serialVersionUID = 8205608518115765928L;

    /** Example bundle class that contains the example */
    public Class<?>  exclass;
    
    /** A more or less short description of the example */
    private String description;
    
    /** All Java source fragments associated with the example */
    private List<SourceFragment> sourceFragments;
    
    /** All CSS source fragments associated with the example */
    private List<SourceFragment> cssFragments;
    
    boolean loaded = false;

    /**
     * Creates a book example object.
     * 
     * You must read the example data with {@link BookExample#readExample(File) readExample()}
     * before you can access the data.
     * 
     * @param exampleId
     * @param shortName
     * @param exclass
     */
    public BookExample (String exampleId, String shortName, Class<?> exclass) {
        super(exampleId, shortName);

        this.exclass   = exclass;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<SourceFragment> getSourceFragments() {
        return sourceFragments;
    }
    
    public List<SourceFragment> getCssFragments() {
        return cssFragments;
    }
    
    /**
     * Loads the example.
     * 
     * Reads the source and CSS files and extracts any source fragments
     * associated with the example. 
     **/
    synchronized public void loadExample(File baseDirectory) {
        if (loaded)
            return;

        try {
            // Instantiate and initialize the example bundle to be
            // able to read metadata using reflection.
            Object bundle = exclass.newInstance();
            
            // Use "_" instead of "-" in descriptions
            String javaContext = context.replace('-', '_');
            
            java.lang.reflect.Field descField = null;
            try {
                descField = bundle.getClass().getField(javaContext+"Description");
                description = (String) descField.get(null);
            } catch (NoSuchFieldException e) {
                // These are OK and expected for most examples
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Read the source code
        String classname = exclass.getName();
        sourceFragments = readSourcesFromClass(classname, context, exampleId);

        // Load associated CSS
        String csspath = baseDirectory.toString()+"/VAADIN/themes/book-examples/styles.css";
        File   cssfile = new File(csspath);
        FileInputStream cssins;
        try {
            cssins = new FileInputStream(cssfile);
            
            // TODO Assume that there is only one CSS fragment
            cssFragments = readSourceFromStream(cssins, exampleId, csspath);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open styles.css: " + e.getMessage());
        }
        
        loaded = true;
    }

    public Component createInstance() {
        BookExampleBundle instance = null;
        
        try {
            // Instantiate and initialize the example
            instance = (BookExampleBundle) exclass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // Initialize (execute) the example
        instance.init(context);

        return (Component) instance;
    }
    
    /**
     * Reads source file for a given class and extracts examples for the given location.
     * 
     * The source file can contain one or more example fragments
     * 
     * @param className
     * @param context
     * @param exampleId the dot-delimited example identifier
     * @return
     */
    private List<SourceFragment> readSourcesFromClass(String className, String context, String exampleId) {
        // Some exceptions for specific special examples
        if ("com.vaadin.book.examples.advanced.UriFragmentExample".equals(className))
            if ("indexing".equals(context))
                className = "com.vaadin.book.applications.IndexingExampleApplication";
            else
                className = "com.vaadin.book.applications.UriFragmentApplication";
        else if ("com.vaadin.book.examples.intro.HelloWorldExample".equals(className))
            className = "com.vaadin.book.applications.HelloWorld";

        String srcname   = "/" + className.replace('.', '/') + ".java";
        InputStream ins  = getClass().getResourceAsStream(srcname);

        if (ins != null)
            return readSourceFromStream(ins, exampleId, srcname);
        else
            System.out.println("Error: unable to open "+srcname+" for reading.");
        
        // Empty list
        return new ArrayList<SourceFragment>();
    }    

    /**
     * Reads a stream that contains source content.
     * 
     * If the source content has multiple fragments or recursive file inclusion statements, the return
     * value will contain more than one fragment.
     **/
    private List<SourceFragment> readSourceFromStream(InputStream ins, String exampleId, String srcname) {
        // Read the proper source fragment from the stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        StringBuffer src = new StringBuffer();
        int state = 0;
        int lineNo = 0;
        
        List<SourceFragment> fragments = new ArrayList<SourceFragment>();
        SourceFragment fragment = new SourceFragment(srcname);
        try {
            for (String line = reader.readLine(); null != line; line = reader.readLine()) {
                line = line.replace("\t", "    ");
                lineNo++;
                
                switch (state) {
                case 0: // Fragment not found
                    if (line.indexOf("BEGIN-EXAMPLE: " + exampleId) != -1) {
                        state = 1;
                        fragment.setFragmentPos(lineNo);
                    } else if (line.indexOf("EXAMPLE-REF:") != -1) {
                        // Reference to another source file
                        Pattern p = Pattern.compile("\\s*// EXAMPLE-REF:\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"); //
                        Matcher m = p.matcher(line);
                        if (m.matches() && m.groupCount() == 3 && m.group(1).equals(exampleId)) {
                            String classname = m.group(2);
                            String reflocation = m.group(3);
                            List<SourceFragment> subs = readSourcesFromClass(classname, null, reflocation);
                            if (subs != null)
                                for (SourceFragment subfragment: subs)
                                    fragments.add(subfragment);
                        }
                    }
                    break;
                case 1: // Reading fragment
                    if (line.indexOf("END-EXAMPLE: " + exampleId) != -1) {
                        state = 999;
                    } else if (line.indexOf("END-EXAMPLE: ") != -1)
                        ; // Skip non-matching fragment tags
                    else if (line.indexOf("BEGIN-EXAMPLE: ") != -1)
                        ; // Skip non-matching fragment tags
                    else if (line.indexOf("EXAMPLE-REF:") != -1) {
                        // Reference to another source file
                        Pattern p = Pattern.compile("\\s*// EXAMPLE-REF:\\s+(\\S+)(\\s+\\S+)?\\s*"); //
                        Matcher m = p.matcher(line);
                        if (m.matches() && m.groupCount() >= 1) {
                            String classname = m.group(1);

                            String reflocation; // Optional
                            if (m.groupCount() == 2 && m.group(2) != null)
                                reflocation = m.group(2).trim();
                            else
                                reflocation = exampleId;
                            
                            List<SourceFragment> subs = readSourcesFromClass(classname, null, reflocation);
                            if (subs != null)
                                for (SourceFragment subfragment: subs)
                                    fragments.add(subfragment);
                        }
                    } else if (line.indexOf("// FORUM:") != -1) {
                        // Pick up Forum link
                        String link = line.substring(line.indexOf(":")+1).trim();
                        fragment.getForumLinks().add(link);
                    } else if (line.indexOf("// BOOK:") != -1) {
                            // Pick up Book link
                            String link = line.substring(line.indexOf(":")+1).trim();
                            fragment.getBookRefs().add(link);
                    } else if (line.indexOf("// KB:") != -1) {
                        // Pick up Book link
                        String ref = line.substring(line.indexOf(":")+1).trim();
                        fragment.getKbRefs().add(fragment.new Ref(ref));
                    } else if (line.indexOf("serialVersionUID") != -1)
                        state = 3;
                    else {
                        src.append(line);
                        src.append("\n");
                    }
                    break;
                case 3: // Skip one line
                    if (line.indexOf("END-EXAMPLE: " + exampleId) != -1)
                        state = 999;
                    else
                        state = 1;
                    break;
                default:
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read example source code because: " + e.getMessage());
            return null;
        }
        
        // Post-process
        String srcCode = src.toString();
        if (srcCode.length() == 0) {
            // Return list because may have found referenced examples
            return fragments;
        }
        
        // Shorten to minimum indentation
        int minindent = 999;
        String lines[] = srcCode.split("\n");
        for (int i=0; i<lines.length; i++) {
            int spacecount = 0;
            for (; spacecount<lines[i].length(); spacecount++)
                if (lines[i].charAt(spacecount) != ' ')
                    break;
            if (spacecount < lines[i].length() && spacecount < minindent)
                minindent = spacecount;
        }
        int srcWidth = 0;
        if (minindent < 999) {
            StringBuffer shortsrc = new StringBuffer();
            for (int i=0; i<lines.length; i++) {
                // The line can be shorter if it's all space
                String line;
                if (lines[i].length() > minindent)
                    line = lines[i].substring(minindent);
                else
                    line = lines[i];
                
                // Calculate width of the source fragment
                if (line.length() > srcWidth)
                    srcWidth = line.length();

                shortsrc.append(line + "\n");
            }
            srcCode = shortsrc.toString();
        }
        
        fragment.setSrcCode(srcCode);
        fragment.setSrcWidth(srcWidth);
        
        fragments.add(fragment);
        return fragments;
    }
}
