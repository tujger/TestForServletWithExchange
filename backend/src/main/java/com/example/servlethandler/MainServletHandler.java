package com.example.servlethandler;

import com.google.common.net.HttpHeaders;

import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created 6/9/2017.
 */

public class MainServletHandler extends AbstractServletHandler {

    @Override
    public void perform(RequestWrapper requestWrapper) {

        try {

            URI uri = requestWrapper.getRequestURI();

            String string = "MAIN " + uri.getPath();
            System.out.println(string);

            byte[] bytes = string.getBytes();
            Charset charset = StandardCharsets.ISO_8859_1;
            if(bytes[0] == -1 && bytes[1] == -2) charset = StandardCharsets.UTF_16;
            else if(bytes[0] == -2 && bytes[1] == -1) charset = StandardCharsets.UTF_16;

            requestWrapper.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
            requestWrapper.sendResponseHeaders(200,0);

            OutputStream os = requestWrapper.getOutputStream();
            os.write(string.getBytes(charset));
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
