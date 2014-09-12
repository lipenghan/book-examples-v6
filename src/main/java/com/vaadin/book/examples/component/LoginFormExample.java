package com.vaadin.book.examples.component;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.book.BookExamplesApplication;
import com.vaadin.book.BookExamplesApplication.MyHttpListener;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class LoginFormExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;
    
    String context;

    public void init(String context) {
        this.context = context;
        setCompositionRoot(new Label("Dummy"));
    }
    
    @Override
    public void attach() {
        super.attach();

        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        if ("tomcatconversion".equals(context))
            tomcatconversion(layout);
        else if ("customization".equals(context))
            customization(layout);
        setCompositionRoot(layout);
    }
    
    @Override
    public void detach() {
        super.detach();
        ((BookExamplesApplication)getApplication()).removeAllMyHttpListeners();
    }

    void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.loginform.basic
        // A wrapper with a caption for the login form
        Panel loginPanel = new Panel("Login");
        loginPanel.setWidth("250px");
        
        // Create the form
        LoginForm login = new LoginForm();
        login.setSizeFull();
        loginPanel.addComponent(login);
        
        // Handle form submits
        login.addListener(new LoginListener() {
            private static final long serialVersionUID = -8940002299511418103L;

            @Override
            public void onLogin(LoginEvent event) {
                String username = event.getLoginParameter("username");
                String password = event.getLoginParameter("password");
                getWindow().showNotification("Logged in " +
                    username + " with password " + password);
            }
        });
        // END-EXAMPLE: component.loginform.basic
        
        layout.addComponent(loginPanel);
    }
    
    public final static String tomcatconversionDescription =
            "<h1>Tomcat Encoding Problem</h1>" +
            "<p>In Tomcat, the form POST parameters are assumed to be in ISO-8859-1, even if the browser " +
            "sends them in UTF-8. They need to be converted. A nicer solution is to use a " +
            "filter in Tomcat to change the encoding.</p>" +
            "<p>You can ignore the debug code in this example</p>";

    void tomcatconversion(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.loginform.tomcatconversion
        // Try to set the encoding before reading the parameter
        ((BookExamplesApplication)getApplication()).addMyHttpListener(new MyHttpListener() {
            private static final long serialVersionUID = 756429980135876333L;

            @Override
            public void onRequestStart(HttpServletRequest request,
                                       HttpServletResponse response) {
                String originalEncoding = request.getCharacterEncoding();
                try {
                    request.setCharacterEncoding("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                
                layout.addComponent(new Label("Path = " + request.getPathInfo() +
                                              ": Encoding was " + originalEncoding +
                                              ", after setting to utf-8, username = " +
                                              request.getParameter("username")));
                layout.addComponent(new Label());
            }
        });

        // A wrapper with a caption for the login form
        Panel loginPanel = new Panel("Login");
        loginPanel.setWidth("250px");
        
        // Create the form
        LoginForm login = new LoginForm() {
            @Override
            protected byte[] getLoginHTML() {
                String appUri = getApplication().getURL().toString()
                        + getWindow().getName() + "/";

                try {
                    return ("<!DOCTYPE html PUBLIC \"-//W3C//DTD "
                            + "XHTML 1.0 Transitional//EN\" "
                            + "\"http://www.w3.org/TR/xhtml1/"
                            + "DTD/xhtml1-transitional.dtd\">\n" + "<html>"
                            + "<head><script type='text/javascript'>"
                            + "var setTarget = function() {" + "var uri = '"
                            + appUri
                            + "loginHandler"
                            + "'; var f = document.getElementById('loginf');"
                            + "document.forms[0].action = uri;document.forms[0].username.focus();};"
                            + ""
                            + "var styles = window.parent.document.styleSheets;"
                            + "for(var j = 0; j < styles.length; j++) {\n"
                            + "if(styles[j].href) {"
                            + "var stylesheet = document.createElement('link');\n"
                            + "stylesheet.setAttribute('rel', 'stylesheet');\n"
                            + "stylesheet.setAttribute('type', 'text/css');\n"
                            + "stylesheet.setAttribute('href', styles[j].href);\n"
                            + "document.getElementsByTagName('head')[0].appendChild(stylesheet);\n"
                            + "}"
                            + "}\n"
                            + "function submitOnEnter(e) { var keycode = e.keyCode || e.which;"
                            + " if (keycode == 13) {document.forms[0].submit();}  } \n"
                            + "</script>"
                            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>"
                            + "</head><body onload='setTarget();' style='margin:0;padding:0; background:transparent;' class=\""
                            + ApplicationConnection.GENERATED_BODY_CLASSNAME
                            + "\">"
                            + "<div class='v-app v-app-loginpage' style=\"background:transparent;\">"
                            + "<iframe name='logintarget' style='width:0;height:0;"
                            + "border:0;margin:0;padding:0;'></iframe>"
                            + "<form id='loginf' target='logintarget' onkeypress=\"submitOnEnter(event)\" method=\"post\" accept-charset=\"UTF-8\">"
                            + "<div>"
                            + getUsernameCaption()
                            + "</div><div >"
                            + "<input class='v-textfield' style='display:block;' type='text' name='username'></div>"
                            + "<div>"
                            + getPasswordCaption()
                            + "</div>"
                            + "<div><input class='v-textfield' style='display:block;' type='password' name='password'></div>"
                            + "<div><div onclick=\"document.forms[0].submit();\" tabindex=\"0\" class=\"v-button\" role=\"button\" ><span class=\"v-button-wrap\"><span class=\"v-button-caption\">"
                            + getLoginButtonCaption()
                            + "</span></span></div></div></form></div>" + "</body></html>")
                            .getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("UTF-8 encoding not avalable", e);
                }
            }  
        };
        login.setSizeFull();
        loginPanel.addComponent(login);
        
        // Handle form submits
        login.addListener(new LoginListener() {
            private static final long serialVersionUID = -8940002299511418103L;

            @Override
            public void onLogin(LoginEvent event) {
                try {
                    // NOTE: Character encoding conversion needed because of a probable bug
                    String username = new String(event.getLoginParameter("username").getBytes("ISO-8859-1"), "UTF-8");
                    String password = new String(event.getLoginParameter("password").getBytes("ISO-8859-1"), "UTF-8");
                    getWindow().showNotification("Logged in " +
                        username + " with password " + password);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        // END-EXAMPLE: component.loginform.tomcatconversion
        
        layout.addComponent(loginPanel);
    }

    void customization(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.loginform.customization
        class MyLoginForm extends LoginForm {
            private static final long serialVersionUID = -1175293455878477596L;
            
            String usernameCaption;
            String passwordCaption;
            String submitCaption;
            
            public MyLoginForm(String usernamePrompt,
                    String passwordPrompt, String submitCaption) {
                this.usernameCaption = usernamePrompt;
                this.passwordCaption = passwordPrompt;
                this.submitCaption  = submitCaption;
            }
            
            @Override
            protected byte[] getLoginHTML() {
                // Application URI needed for submitting form
                String appUri = getApplication().getURL().toString()
                        + getWindow().getName() + "/";

                String x, h, b; // XML header, HTML head and body
                x = "<!DOCTYPE html PUBLIC \"-//W3C//DTD "
                  + "XHTML 1.0 Transitional//EN\" "
                  + "\"http://www.w3.org/TR/xhtml1/"
                  + "DTD/xhtml1-transitional.dtd\">\n";
                h = "<head><script type='text/javascript'>"
                  + "var setTarget = function() {"
                  + "  var uri = '" + appUri + "loginHandler';"
                  + "  var f = document.getElementById('loginf');"
                  + "  document.forms[0].action = uri;"
                  + "  document.forms[0].username.focus();"
                  + "};"
                  + ""
                  + "var styles = window.parent.document.styleSheets;"
                  + "for(var j = 0; j < styles.length; j++) {\n"
                  + "  if(styles[j].href) {"
                  + "    var stylesheet = document.createElement('link');\n"
                  + "    stylesheet.setAttribute('rel', 'stylesheet');\n"
                  + "    stylesheet.setAttribute('type', 'text/css');\n"
                  + "    stylesheet.setAttribute('href', styles[j].href);\n"
                  + "    document.getElementsByTagName('head')[0]"
                  + "                .appendChild(stylesheet);\n"
                  + "  }"
                  + "}\n"
                  + "function submitOnEnter(e) {"
                  + "  var keycode = e.keyCode || e.which;"
                  + "  if (keycode == 13) {document.forms[0].submit();}"
                  + "}\n"
                  + "</script>"
                  + "</head>";
                b = "<body onload='setTarget();'"
                  + "  style='margin:0;padding:0; background:transparent;'"
                  + "  class='"
                  + ApplicationConnection.GENERATED_BODY_CLASSNAME + "'>"
                  + "<div class='v-app v-app-loginpage'"
                  + "     style='background:transparent;'>"
                  + "<iframe name='logintarget' style='width:0;height:0;"
                  + "border:0;margin:0;padding:0;'></iframe>"
                  + "<form id='loginf' target='logintarget'"
                  + "      onkeypress='submitOnEnter(event)'"
                  + "      method='post'>"
                  + "<table>"
                  + "<tr><td>" + usernameCaption + "</td>"
                  + "<td><input class='v-textfield' style='display:block;'"
                  + "           type='text' name='username'></td></tr>"
                  + "<tr><td>" + passwordCaption + "</td>"
                  + "    <td><input class='v-textfield'"
                  + "          style='display:block;' type='password'"
                  + "          name='password'></td></tr>"
                  + "</table>"
                  + "<div>"
                  + "<div onclick='document.forms[0].submit();'"
                  + "     tabindex='0' class='v-button' role='button'>"
                  + "<span class='v-button-wrap'>"
                  + "<span class='v-button-caption'>"
                  + submitCaption + "</span>"
                  + "</span></div></div></form></div></body>";

                return (x + "<html>" + h + b + "</html>").getBytes();
            }
        };
        
        MyLoginForm loginForm = new MyLoginForm("Name of the User",
                "A passing word", "Login Me Now");
        // END-EXAMPLE: component.loginform.customization
        
        layout.addComponent(loginForm);
    }
}
