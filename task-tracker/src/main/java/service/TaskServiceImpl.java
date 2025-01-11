package main.java.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.exception.TaskIdNotFoundException;
import main.java.model.Task;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    private final ObjectMapper objectMapper;
    private static final String FILE_PATH = "J:\\Backend Projects\\Backend-Projects\\Task Tracker\\src\\main\\java\\resources\\task_data.json";
    private final File file;


    public TaskServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.file = new File(FILE_PATH);
    }

    public TaskServiceImpl(String filePath) {
        this.objectMapper = new ObjectMapper();
        file = new File(filePath);
    }

    @Override
    public String addTask(String description) {
        List<Task> taskList = getTasks();
        Task task = updateBasicDetailsForTask(description, taskList);
        taskList.add(task);
        addOrUpdateTasks(taskList);

        return "Task added successfully (ID: " + task.getId() + ")";
    }

    @Override
    public String updateTaskByDescription(Integer id, String newDescription) {
        try {
            List<Task> taskList = getTasks();
            Task task = taskList.stream()
                    .filter(t -> id.equals(t.getId()))
                    .findFirst()
                    .orElseThrow(() -> new TaskIdNotFoundException(id));

            task.setDescription(newDescription);
            task.setUpdatedAt(LocalDateTime.now().toString());
            addOrUpdateTasks(taskList);
        } catch (TaskIdNotFoundException e) {
            System.out.println(e.getMessage());
            return "Task description has not updated successfully (ID: " + id + ")";
        }

        return "Task description updated successfully (ID: " + id + ")";
    }

    @Override
    public String deleteTask(Integer id) {
        try {
            List<Task> taskList = getTasks();
            Task task = taskList.stream()
                    .filter(t -> id.equals(t.getId()))
                    .findFirst()
                    .orElseThrow(() -> new TaskIdNotFoundException(id));

            taskList.remove(task);
            addOrUpdateTasks(taskList);
        } catch (TaskIdNotFoundException e) {
            System.out.println(e.getMessage());
            return "Task has not deleted (ID: " + id + ")";
        }

        return "Task deleted successfully (ID: " + id + ")";
    }

    @Override
    public List<Task> getTasksByStatus(String status) {
        return getTasks().stream()
                .filter(task -> status.equalsIgnoreCase(task.getStatus()))
                .toList();
    }

    @Override
    public String updateTaskByStatus(Integer id, String status) {
        try {
            List<Task> taskList = getTasks();
            Task task = taskList.stream()
                    .filter(t -> id.equals(t.getId()))
                    .findFirst()
                    .orElseThrow(() -> new TaskIdNotFoundException(id));

            task.setStatus(status);
            task.setUpdatedAt(LocalDateTime.now().toString());
            addOrUpdateTasks(taskList);
        } catch (TaskIdNotFoundException e) {
            System.out.println(e.getMessage());
            return "Task status has not updated  (ID:" + id + ")";
        }

        return "Task status updated successfully (ID: " + id + ")";
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = List.of();
        try {
            if (file.exists() && file.length() > 0) {
                tasks = objectMapper.readValue(file, new TypeReference<>() {
                });
            } else {
                tasks = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    private static Integer getNextId(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getId)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    private static Task updateBasicDetailsForTask(String description, List<Task> taskList) {
        Task task = new Task(description);
        task.setId(getNextId(taskList) + 1);
        task.setStatus("todo");
        task.setCreatedAt(LocalDateTime.now().toString());
        task.setUpdatedAt(LocalDateTime.now().toString());
        return task;
    }

    private void addOrUpdateTasks(List<Task> taskList) {
        try {
            objectMapper.writeValue(file, taskList);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
