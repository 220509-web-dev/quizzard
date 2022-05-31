package com.revature.quizzard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class Driver {

    public static void main(String[] args) throws InterruptedException {

        try {

            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.setExecutor(Executors.newFixedThreadPool(10));

            httpServer.createContext("/hello", new HelloHandler());

            // Local anonymous class (kinda ugly, pre Java 8 syntax)
            httpServer.createContext("/test-1", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String payload = "This request was process by thread: " + Thread.currentThread().getName();
                    exchange.sendResponseHeaders(200, payload.length());
                    exchange.getResponseBody().write(payload.getBytes(StandardCharsets.UTF_8));
                    exchange.getResponseBody().close();
                }
            });

            // Java 8 Lambda expressions (the inline implementation of a functional interface's one abstract method)
            httpServer.createContext("/test-2", exchange -> {
                String payload = "This request was handled by a lambda expression handler";
                exchange.sendResponseHeaders(200, payload.length());
                exchange.getResponseBody().write(payload.getBytes(StandardCharsets.UTF_8));
                exchange.getResponseBody().close();
            });


            System.out.println("Application server started on port 8080");
            httpServer.start();

        } catch (IOException e) {
            System.err.println("Could not start the web server");
            Thread.sleep(500); // gives the error message time to log to the console
            throw new RuntimeException(e);
        }

    }

}
