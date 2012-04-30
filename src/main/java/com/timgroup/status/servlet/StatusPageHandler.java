package com.timgroup.status.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.timgroup.status.ApplicationReport;
import com.timgroup.status.StatusPage;

public class StatusPageHandler {
    
    private static final String UTF_8 = "UTF-8";
    
    private StatusPage statusPage;
    
    public void handle(String path, WebResponse response) throws IOException {
        if (path == null || path.isEmpty()) {
            response.redirect("/status");
        } else if (path.equals("/status")) {
            OutputStream out = response.respond("text/xml", UTF_8);
            ApplicationReport report = statusPage.getApplicationReport();
            report.render(new OutputStreamWriter(out, UTF_8));
        } else if (path.equals("/version")) {
            OutputStream out = response.respond("text/plain", UTF_8);
            out.write("".getBytes());
        } else if (path.equals("/" + StatusPage.DTD_FILENAME)) {
            sendResource(StatusPage.DTD_FILENAME, "application/xml-dtd", response);
        } else if (path.equals("/" + StatusPage.CSS_FILENAME)) {
            sendResource(StatusPage.CSS_FILENAME, "text/css", response);
        } else {
            response.reject(HttpServletResponse.SC_NOT_FOUND, "try asking for .../status/");
        }
    }
    
    private void sendResource(String filename, String contentType, WebResponse response) throws IOException {
        InputStream resource = StatusPage.class.getResourceAsStream(filename);
        if (resource == null) {
            response.reject(HttpServletResponse.SC_NOT_FOUND, "could not find resource with name " + filename);
            return;
        }
        OutputStream output = response.respond(contentType, UTF_8);
        IOUtils.copy(resource, output);
    }
    
    public void setStatusPage(StatusPage statusPage) {
        this.statusPage = statusPage;
    }
    
}
