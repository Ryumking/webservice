package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskService {


    public Task findTaskById(Long id) {
        Task task = new Task();
        task.setId(1);
        task.setDescription("first task");
        task.setTitle("task");
        return task;
    }

    public List<Task> findAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId(1);
        task.setDescription("first task");
        task.setTitle("task");
        tasks.add(task);
        tasks.add(task);
        tasks.add(task);
        return tasks;
    }

    public Task createTask(Task task) {
        System.out.println(task);
        return task;
    }
}
