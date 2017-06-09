package com.example.servlethandler;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyServer {

        public static void main(final String[] args ) throws InterruptedException , IOException {

            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8081), 0);

            MainServletHandler mainHandler = new MainServletHandler();
            server.createContext("/", mainHandler);

            HelpServletHandler helpHandler = new HelpServletHandler();
            server.createContext("/help", helpHandler);

            RedirectServletHandler redirectHandler = new RedirectServletHandler();
            server.createContext("/redirect", redirectHandler);

            ExecutorService executor = Executors.newCachedThreadPool();
            server.setExecutor(executor);

            server.start();

        }

    }
