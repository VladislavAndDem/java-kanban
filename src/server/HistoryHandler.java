package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private final Gson gson;
    private final TaskManager manager;

    public HistoryHandler(Gson gson, TaskManager manager) {
        this.gson = gson;
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && exchange.getRequestMethod().equals("GET")) {
            String response = gson.toJson(manager.getHistory());
            String jsonResponse = gson.toJson(response);
            sendText(exchange, jsonResponse, 200);
        } else {
            sendText(exchange, "Некорректный запрос. Попробуйте еще раз", 400);
        }
    }
}