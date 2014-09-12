package com.vaadin.book;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.vaadin.terminal.gwt.server.ApplicationServlet;

public class CustomApplicationServlet
     extends ApplicationServlet {
    @Override
    protected void writeAjaxPageHtmlHeader(BufferedWriter page,
            String title, String themeUri,
            HttpServletRequest request) throws IOException {
        super.writeAjaxPageHtmlHeader(page, title,
                                      themeUri, request);
        
        page.write("<script type=\"text/javascript\"" +
        		          " src=\"js/other.js\"></script>");
    }
}
