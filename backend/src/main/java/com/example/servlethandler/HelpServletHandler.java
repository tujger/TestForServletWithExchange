package com.example.servlethandler;

import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;

import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Created 6/9/2017.
 */

public class HelpServletHandler extends AbstractServletHandler {

    @Override
    public void perform(RequestWrapper requestWrapper) {

        try {

            Logger Log = Logger.getLogger(HelpServletHandler.class.getCanonicalName());

            requestWrapper.setHeader(HttpHeaders.CONTENT_TYPE, "text/html");
            requestWrapper.sendResponseHeaders(200,0);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setServiceAccount(getServletContext().getResourceAsStream("/WEB-INF/TestHandlerVsServlet-4eedb75a0862.json"))
                    .setDatabaseUrl("https://testhandlervsservlet.firebaseio.com/")
                    .build();

            try {
                FirebaseApp.getInstance();
            }
            catch (Exception error){
                Log.info("doesn't exist...");
            }

            try {
                FirebaseApp.initializeApp(options);
            }
            catch(Exception error){
                Log.info("already exists...");
            }

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            String name = String.valueOf(Math.random()).replace(".","_");
            Task<Void> task = ref.child(name).setValue(true);

            Tasks.await(task);
            requestWrapper.getOutputStream().write(("<br>value name = "+name).getBytes());

            URI uri = requestWrapper.getRequestURI();

            String string = "<br>HELP " + uri.getPath();
            System.out.println(string);

            byte[] bytes = string.getBytes();
            Charset charset = StandardCharsets.ISO_8859_1;
            if(bytes[0] == -1 && bytes[1] == -2) charset = StandardCharsets.UTF_16;
            else if(bytes[0] == -2 && bytes[1] == -1) charset = StandardCharsets.UTF_16;

            requestWrapper.sendResponseHeaders(200,0);

            OutputStream os = requestWrapper.getOutputStream();
            os.write(string.getBytes(charset));
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
