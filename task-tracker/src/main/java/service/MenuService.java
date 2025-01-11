package main.java.service;

import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuService {
    private final TaskService taskService;
    private final Map<String, String> taskStatusMap;

    public MenuService() {
        this.taskService = new TaskServiceImpl();
        taskStatusMap = Map.of("mark-todo", "todo",
                "mark-in-progress", "in-progress",
                "mark-done", "done");

    }

    public void routers(String[] args) {
        if ("add".equalsIgnoreCase(args[1])) {
            System.out.println(taskService.addTask(getDescription(args, 2)));
        } else if ("update".equalsIgnoreCase(args[1])) {
            System.out.println(taskService.updateTaskByDescription(Integer.valueOf(args[2]), getDescription(args, 3)));
        } else if ("delete".equalsIgnoreCase(args[1])) {
            System.out.println(taskService.deleteTask(Integer.valueOf(args[2])));
        } else if ("list".equals(args[1]) && 2 == args.length) {
            System.out.println(taskService.getTasks());
        } else if ("list".equalsIgnoreCase(args[1]) && StringUtils.isNotBlank(args[2])) {
            System.out.println(taskService.getTasksByStatus(args[2]));
        } else if (taskStatusMap.containsKey(args[1])) {
            System.out.println(taskService.updateTaskByStatus(Integer.valueOf(args[2]), taskStatusMap.get(args[1])));
        }
    }

    private static String getDescription(String[] args, Integer itemCountToSkip) {
        return Arrays.stream(args)
                .skip(itemCountToSkip)
                .collect(Collectors.joining(" "));

    }
}
