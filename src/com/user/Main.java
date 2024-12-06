package com.user;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n1. Signup");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        if (userManager.signup(new User(username, password))) {
                            System.out.println("Signup successful!");
                        } else {
                            System.out.println("Signup failed! Username already exists.");
                        }
                    }
                    case 2 -> {
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        if (userManager.login(username, password)) {
                            System.out.println("Login successful!");
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                    }
                    case 3 -> {
                        System.out.println("Exiting program. Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.nextLine(); // Clear the invalid input from the buffer
            }
        }
    }
}
