package javaapplication1;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaApplication1 {

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8180"));
        HttpServer server = HttpServer.create(new InetSocketAddress(0), port);
        server.createContext("/api/hello", new HelloHandler());
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Server started at http://localhost:8180/api/hello");
    }

static class HelloHandler implements HttpHandler {
    // Thread-safe list to store posted data
    private final List<String> storedData = new ArrayList<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(method)) {
            StringBuilder response = new StringBuilder();
            if (storedData.isEmpty()) {
                response.append("No data posted yet.");
            } else {
                response.append("Stored data:\n");
                for (String data : storedData) {
                    response.append(data).append("\n");
                }
            }
            byte[] responseBytes = response.toString().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }

        } else if ("POST".equalsIgnoreCase(method)) {
            InputStream is = exchange.getRequestBody();
            StringBuilder body = new StringBuilder();
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
                while (scanner.hasNextLine()) {
                    body.append(scanner.nextLine());
                }
            }
            // Store the posted data
            synchronized (storedData) {
                storedData.add(body.toString());
            }

            String response = "You POSTed: " + body.toString();
            byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }

        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}
}




