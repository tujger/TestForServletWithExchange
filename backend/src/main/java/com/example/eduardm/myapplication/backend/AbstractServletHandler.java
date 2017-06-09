/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.eduardm.myapplication.backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract public class AbstractServletHandler extends HttpServlet implements HttpHandler {

    private RequestWrapper requestWrapper;

    public AbstractServletHandler() {
        requestWrapper = new RequestWrapper();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        requestWrapper.setHttpServletRequest(req);
        requestWrapper.setHttpServletResponse(resp);

        perform(requestWrapper);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        requestWrapper.setHttpServletRequest(req);
        requestWrapper.setHttpServletResponse(resp);

        perform(requestWrapper);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        requestWrapper.setHttpExchange(exchange);
        perform(requestWrapper);

    }

    abstract public void perform(RequestWrapper requestWrapper);

    public RequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    public void setRequestWrapper(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

}
