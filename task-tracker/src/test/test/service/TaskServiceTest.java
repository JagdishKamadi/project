package test.test.service;


import main.java.model.Task;
import main.java.service.TaskService;
import main.java.service.TaskServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {

    private static final String TEST_FILE_PATH = "J:\\Backend Projects\\Backend-Projects\\Task Tracker\\src\\test\\test\\resources\\test_task_data.json";
    private static TaskService taskService;

    @BeforeAll
    static void setUp() {
        taskService = new TaskServiceImpl(TEST_FILE_PATH);
    }

    @AfterEach
    void doFinalTask() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddTask() {
        String description = "Buy groceries";
        String actualResponse = taskService.addTask(description);
        String expectedResponse = "Task added successfully (ID: 1)";

        assertEquals(expectedResponse, actualResponse);
        List<Task> tasks = taskService.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Buy groceries", tasks.get(0).getDescription());
    }

    @Test
    void testUpdateTaskByDescription_whenTaskIdExist() {
        taskService.addTask("Buy groceries and cook dinner");
        String actualResponse = taskService.updateTaskByDescription(1, "Buy groceries and some vegetables and cook dinner");
        String expectedResponse = "Task description updated successfully (ID: 1)";
        assertEquals(expectedResponse, actualResponse);
        List<Task> tasks = taskService.getTasks();
        System.out.println(tasks);
        assertEquals("Buy groceries and some vegetables and cook dinner", tasks.get(0).getDescription());
    }

    @Test()
    void testUpdateTaskByDescription_whenTaskIdDoesNotExist_throwsException() {
        String actualResponse = taskService.updateTaskByDescription(999, "New description");
        String expectedResponse = "Task description has not updated successfully (ID: 999)";
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testDeleteTask() {
        taskService.addTask("Task to be deleted");
        String actualResponse = taskService.deleteTask(1);
        String expectedResponse = "Task deleted successfully (ID: 1)";
        assertEquals(expectedResponse, actualResponse);
        assertTrue(taskService.getTasks().isEmpty());
    }

    @Test
    void testDeleteTask_whenTaskIdDoesNotExist_throwsException() {
        String actualResponse = taskService.deleteTask(1);
        String expectedResponse = "Task has not deleted (ID: 1)";
        assertEquals(expectedResponse, actualResponse);
        assertTrue(taskService.getTasks().isEmpty());
    }

    @Test
    void testGetTasksByStatus() {
        // Add multiple tasks with different statuses
        taskService.addTask("Task 1");
        taskService.updateTaskByStatus(1, "in-progress");
        taskService.addTask("Task 2");
        taskService.updateTaskByStatus(2, "completed");

        List<Task> inProgressTasks = taskService.getTasksByStatus("in-progress");
        List<Task> completedTasks = taskService.getTasksByStatus("completed");

        assertEquals(1, inProgressTasks.size());
        assertEquals(1, completedTasks.size());
    }

    @Test
    void testUpdateTaskByStatus() {
        // First, add a task to update
        taskService.addTask("Task to update status");

        // Now, update the task's status
        String response = taskService.updateTaskByStatus(1, "completed");

        assertTrue(response.contains("Task status updated successfully"));
        List<Task> tasks = taskService.getTasks();
        assertEquals("completed", tasks.get(0).getStatus());
    }
}
