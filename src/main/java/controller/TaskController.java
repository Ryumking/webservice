package controller;

import model.Task;
import service.TaskService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static javax.servlet.http.HttpServletResponse.*;

public class TaskController extends HttpServlet {
    private final ControllerHelper<Task> taskControllerHelper = new ControllerHelper<>(Task.class);
    private final TaskService taskService = new TaskService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final Long taskId = taskControllerHelper.getIdFromPath(req);
        if (taskId == 0L) {
            taskControllerHelper.writeToJson(resp, taskService.findAllTasks());
            return;
        }
        taskControllerHelper.writeToJson(resp, taskService.findTaskById(taskId));
        resp.setStatus(SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task task = taskControllerHelper.writeToObject(req);
        final long userId = Long.parseLong(req.getParameter("userId"));
        final long taskId = taskService.createTask(task, userId);
        if (taskId == 0) {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Bad parameters");
            resp.setStatus(SC_BAD_REQUEST);
            return;
        }
        resp.addHeader("Location", "/tasks/" + taskId);
        resp.setStatus(SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task task = taskControllerHelper.writeToObject(req);
        final long taskId = Long.parseLong(req.getParameter("taskId"));
        final boolean updateTask = taskService.updateTask(task, taskId);
        if (updateTask) {
            resp.setStatus(SC_CREATED);
        } else {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Bad parameters");
            resp.setStatus(SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final long taskId = taskControllerHelper.getIdFromPath(req);
        if (!taskService.delete(taskId)) {
            resp.setContentType("text/plain; charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("Bad taskId");
            resp.setStatus(SC_NOT_FOUND);
        }
        resp.setStatus(SC_NO_CONTENT);
    }
}
