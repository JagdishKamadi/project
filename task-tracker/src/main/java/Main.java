package main.java;

import main.java.service.MenuService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            MenuService menuService = new MenuService();
            while (true) {
                String input = scanner.nextLine();
                String[] commands = input.split("\\s+");

                if ("task-cli".equalsIgnoreCase(commands[0])) {
                    menuService.routers(commands);
                } else {
                    System.out.println("Invalid input, terminating the program!");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}