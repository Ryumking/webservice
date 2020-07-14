package service;

import dao.TaskDao;
import model.Task;

import java.util.List;

public class TaskService {
    final TaskDao taskDAO = new TaskDao();

    public Long createTask(Task task, Long id) {
        return taskDAO.create(task);
    }

    public boolean updateTask(Task task, Long id) {
        return taskDAO.update(task, id);
    }

    public boolean delete(Long id) {
        return taskDAO.delete(id);
    }

    public Task findTaskById(Long id) {
        return taskDAO.readById(id);
    }

    public List<Task> findAllTasks() {
        return taskDAO.readAll();
    }
}
