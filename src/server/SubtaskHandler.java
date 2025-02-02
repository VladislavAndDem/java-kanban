package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskIntersectionException;
import manager.TaskManager;
import task.SubTask;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    private final Gson gson;
    private final TaskManager manager;

    public SubtaskHandler(Gson gson, TaskManager manager) {
        this.gson = gson;
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_SUBTASK: {
                handleGetSubtask(exchange);
                break;
            }
            case GET_SUBTASKS: {
                handleGetSubtasks(exchange);
                break;
            }
            case DELETE_SUBTASK: {
                handleDeleteSubtask(exchange);
                break;
            }
            case POST_CREATE_SUBTASK: {
                handlePostCreateSubtask(exchange);
                break;
            }
            case POST_UPDATE_SUBTASK: {
                handlePostUpdateSubtask(exchange);
                break;
            }
            case UNKNOWN:
                sendText(exchange, "Некорректный запрос. Попробуйте еще раз", 400);
        }
    }

    private void handleGetSubtask(HttpExchange exchange) throws IOException {
        Optional<Integer> subtaskIdOpt = getId(exchange);
        if (subtaskIdOpt.isEmpty()) {
            sendText(exchange, "Некорректный идентификатор подзадачи", 400);
            return;
        }
        int subtaskId = subtaskIdOpt.get();
        try {
            String jsonResponse = gson.toJson(manager.getSubtaskByID(subtaskId).toString());
            sendText(exchange, jsonResponse, 200);
        } catch (IllegalArgumentException i) {
            sendNotFound(exchange, subtaskId);
        }
    }

    private void handleGetSubtasks(HttpExchange exchange) throws IOException {
        String response = manager.getSubTasks().stream()
                .map(SubTask::toString)
                .collect(Collectors.joining("\n"));
        String jsonResponse = gson.toJson(response);
        sendText(exchange, jsonResponse, 200);
    }

    private void handleDeleteSubtask(HttpExchange exchange) throws IOException {
        Optional<Integer> subtaskIdOpt = getId(exchange);
        if (subtaskIdOpt.isEmpty()) {
            sendText(exchange, "Некорректный идентификатор подзадачи", 400);
            return;
        }
        int subtaskId = subtaskIdOpt.get();
        manager.deleteSubtaskByID(subtaskId);
        sendText(exchange, "Подзадача с идентификатором " + subtaskId + " удалена", 200);
    }

    private void handlePostCreateSubtask(HttpExchange exchange) throws IOException {
        try {
            String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
            SubTask subtask = gson.fromJson(body, SubTask.class);
            manager.addNewSubTask(subtask);
            sendText(exchange, "Подзадача создана", 201);
        } catch (TaskIntersectionException i) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            sendText(exchange, "Ошибка сервера", 500);
        }
    }

    private void handlePostUpdateSubtask(HttpExchange exchange) throws IOException {
        Optional<Integer> subtaskIdOpt = getId(exchange);
        if (subtaskIdOpt.isEmpty()) {
            sendText(exchange, "Некорректный идентификатор подзадачи", 400);
            return;
        }
        int subtaskId = subtaskIdOpt.get();
        try {
            if (manager.getSubTasks().contains(manager.getSubtaskByID(subtaskId))) {
                String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
                SubTask subtask = gson.fromJson(body, SubTask.class);
                manager.updateSubtask(subtask);
                sendText(exchange, "Подзадача обновлена", 201);
            } else {
                sendNotFound(exchange, subtaskId);
            }
        } catch (TaskIntersectionException i) {
            sendHasInteractions(exchange);
        } catch (IllegalArgumentException i) {
            sendNotFound(exchange, subtaskId);
        } catch (Exception e) {
            sendText(exchange, "Ошибка сервера", 500);
        }
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && requestMethod.equals("GET")) {
            return Endpoint.GET_SUBTASKS;
        } else if (pathParts.length == 2 && requestMethod.equals("POST")) {
            return Endpoint.POST_CREATE_SUBTASK;
        } else if (pathParts.length == 3 && requestMethod.equals("GET")) {
            return Endpoint.GET_SUBTASK;
        } else if (pathParts.length == 3 && requestMethod.equals("POST")) {
            return Endpoint.POST_UPDATE_SUBTASK;
        } else if (pathParts.length == 3 && requestMethod.equals("DELETE")) {
            return Endpoint.DELETE_SUBTASK;
        }
        return Endpoint.UNKNOWN;
    }
}
