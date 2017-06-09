package com.example.eduardm.myapplication.backend;

import com.google.common.net.HttpHeaders;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created 6/9/2017.
 */

public class RequestWrapper {

    private final static int MODE_SERVLET = 0;
    private final static int MODE_EXCHANGE = 1;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    private HttpExchange httpExchange;

    private int mode;

    @Override
    public String toString() {
        return "RequestWrapper{}";
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        setMode(MODE_SERVLET);
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
        setMode(MODE_SERVLET);
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpExchange(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        setMode(MODE_EXCHANGE);
    }

    public HttpExchange getHttpExchange() {
        return httpExchange;
    }

    public URI getRequestURI() {
        if(mode == MODE_SERVLET) {
            try {
                return new URI(httpServletRequest.getRequestURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
        } else if(mode == MODE_EXCHANGE) {
            return httpExchange.getRequestURI();
        }
        return null;
    }

    public void setHeader(String name, String value) {
        if(mode == MODE_SERVLET) {
            httpServletResponse.setHeader(name, value);
        } else if(mode == MODE_EXCHANGE) {
            httpExchange.getResponseHeaders().set(name, value);
        }
    }

    public void sendResponseHeaders(int code, int arg1) {
        if(mode == MODE_SERVLET) {
        } else if(mode == MODE_EXCHANGE) {
            try {
                httpExchange.sendResponseHeaders(code, arg1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public OutputStream getOutputStream() throws IOException {
        if(mode == MODE_SERVLET) {
            return httpServletResponse.getOutputStream();
        } else if(mode == MODE_EXCHANGE) {
            return httpExchange.getResponseBody();
        }
        return null;
    }

    public InputStream getInputStream() throws IOException {
        if(mode == MODE_SERVLET) {
            return httpServletRequest.getInputStream();
        } else if(mode == MODE_EXCHANGE) {
            return httpExchange.getRequestBody();
        }
        return null;
    }


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
