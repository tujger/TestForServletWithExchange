package com.example.servlethandler;

import com.google.common.net.HttpHeaders;

import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created 6/9/2017.
 */

public class RedirectServletHandler extends AbstractServletHandler {

    @Override
    public void perform(RequestWrapper requestWrapper) {

        try {

            URI uri = requestWrapper.getRequestURI();

            requestWrapper.sendRedirect("/help"+uri.getPath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
