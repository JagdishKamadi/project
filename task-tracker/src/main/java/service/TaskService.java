package main.java.service;

import main.java.model.Task;

import java.util.List;

public interface TaskService {
    String addTask(String description);

    String updateTaskByDescription(Integer id, String newDescription);

    String deleteTask(Integer id);

    List<Task> getTasksByStatus(String status);

    String updateTaskByStatus(Integer id, String status);

    List<Task> getTasks();
}
