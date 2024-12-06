package com.article;
import com.recommendation.ConcurrentRecommendationHandler;
import com.user.User;
import com.user.UserManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.InputMismatchException;

import java.util.Scanner;

public class Main {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String MAGENTA = "\u001B[35m";

    public static void main(String[] args) {
        // Display the welcome message
        WelcomeMessage.display();

//        System.out.println("_______________Welcome to the News Recommendation System!______________");
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        User loggedInUser = null;
        // User login system
        System.out.print(BLUE + "Enter username: " + RESET);
        String username = scanner.nextLine();
        System.out.print(BLUE + "Enter password: " + RESET);
        String password = scanner.nextLine();

        if (userManager.login(username, password)) {
            loggedInUser = new User(username, password);
            System.out.println(GREEN + "Login successful!" + RESET);
        } else {
            System.out.println(RED + "Invalid login credentials." + RESET);
            return;
        }

        ConcurrentRecommendationHandler recommendationHandler = new ConcurrentRecommendationHandler();

        // Create a thread pool for handling multiple user requests
        ExecutorService executor = Executors.newCachedThreadPool();

        // Menu for article viewing and exit
        while (true) {
            try {
                System.out.println(CYAN + "\nSelect an option:" + RESET);
                System.out.println(MAGENTA + "1. View Technology Articles" + RESET);
                System.out.println(MAGENTA + "2. View Business Articles" + RESET);
                System.out.println(MAGENTA + "3. View Sports Articles" + RESET);
                System.out.println(MAGENTA + "4. View Entertainment Articles" + RESET);
                System.out.println(MAGENTA + "5. View Education Articles" + RESET);
                System.out.println(MAGENTA + "6. View Reading History" + RESET);
                System.out.println(MAGENTA + "7. Get Recommended Articles" + RESET);
                System.out.println(MAGENTA + "8. Exit" + RESET);
                System.out.print(YELLOW + "Enter your choice: " + RESET);
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        System.out.println(CYAN + "Technology Articles:" + RESET);
                        ArticleHandler.displayRandomArticles(loggedInUser.getUsername(), "technology");
                        break;
                    case 2:
                        System.out.println(CYAN + "Business Articles:" + RESET);
                        ArticleHandler.displayRandomArticles(loggedInUser.getUsername(), "business");
                        break;
                    case 3:
                        System.out.println(CYAN + "Sports Articles:" + RESET);
                        ArticleHandler.displayRandomArticles(loggedInUser.getUsername(), "sports");
                        break;
                    case 4:
                        System.out.println(CYAN + "Entertainment Articles:" + RESET);
                        ArticleHandler.displayRandomArticles(loggedInUser.getUsername(), "entertainment");
                        break;
                    case 5:
                        System.out.println(CYAN + "Education Articles:" + RESET);
                        ArticleHandler.displayRandomArticles(loggedInUser.getUsername(), "education");
                        break;
                    case 6:
                        ReadingHistoryManager.displayUserHistory(loggedInUser.getUsername());
                        break;
                    case 7:
                        System.out.println(CYAN + "Fetching recommendations..." + RESET );
                        Future<List<Article>> future = recommendationHandler.getRecommendedArticles(loggedInUser.getUsername());
                        try {
                            // Wait for the recommendations to be fetched
                            List<Article> recommendedArticles = future.get(); // Blocking call to wait for completion
                            if (recommendedArticles.isEmpty()) {
                                System.out.println(CYAN + "\nNo recommendations to show. Please like articles." + RESET);
                            } else {
                                System.out.println(CYAN + "\nHere are some articles you might like:" + RESET);
                                for (int i = 0; i < recommendedArticles.size(); i++) {
                                    Article article = recommendedArticles.get(i);
                                    System.out.println(CYAN + (i + 1) + ". " + article.getHeadline() + RESET);
                                    System.out.println(CYAN + "   Link: " + article.getUrl() + RESET);
                                    System.out.println(CYAN +"-------------------------------" + RESET);
                                }
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(CYAN +"Error fetching recommendations: " + e.getMessage() + RESET);
                        }

                        break;

                    case 8:
                        System.out.println(CYAN +"Exiting program. Goodbye!" + RESET);
                        executor.shutdown(); // Shutdown the executor service
                        return;
                    default:
                        System.out.println(CYAN +"Invalid option. Please try again." + RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println(CYAN +"Invalid input. Please enter a valid number." + RESET);
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}