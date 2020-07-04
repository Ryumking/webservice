package controller;

import model.Task;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TaskController extends HttpServlet {
    private final ControllerHelper<Task> controllerHelper = new ControllerHelper<>(Task.class);
    private final TaskService taskService = new TaskService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Task task = controllerHelper.writeToObject(req);
        taskService.createTask(task);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            controllerHelper.writeToJson(resp,taskService.findAllTasks());
            return;
        }
        Long idFromPath = controllerHelper.getIdFromPath(pathInfo);
        controllerHelper.writeToJson(resp, taskService.findTaskById(idFromPath));
    }
}
