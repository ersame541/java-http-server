package javaapplication1;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.InputStream;
import java.util.Scanner;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class JavaApplication1 {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8180), 0);
        server.createContext("/api/hello", new HelloHandler());
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Server started at http://localhost:8180/api/hello");
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();

            if ("GET".equalsIgnoreCase(method)) {
                String response = "Hello, World!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }

            } else if ("POST".equalsIgnoreCase(method)) {
                InputStream is = exchange.getRequestBody();
                StringBuilder body = new StringBuilder();
                try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
                    while (scanner.hasNextLine()) {
                        body.append(scanner.nextLine());
                    }
                }

                String response = "You POSTed: " + body.toString();
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }

            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}
