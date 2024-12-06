package com.article;

import com.opencsv.CSVReader;
import java.util.Scanner;

import java.io.*;
import java.util.*;

public class ArticleHandler {

    // Load articles from the respective CSV file based on the category
    public static List<Article> loadArticles(String category) {
        List<Article> articles = new ArrayList<>();
        String filePath = category + "_data.csv"; // File names like business_data.csv, sports_data.csv, etc.

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] fields;
            csvReader.readNext(); // Skip the header line

            while ((fields = csvReader.readNext()) != null) {
                if (fields.length == 5) {
                    // Create article object and add to the list
                    Article article = new Article(
                            fields[0].trim(),
                            fields[1].trim(),
                            fields[2].trim(),
                            fields[3].trim(),
                            fields[4].trim()
                    );
                    articles.add(article);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return articles;
    }

    // Display a list of articles
    public static void displayArticles(List<Article> articles) {
        if (articles.isEmpty()) {
            System.out.println("No articles available.");
            return;
        }

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            System.out.println((i + 1) + ". Headline: " + article.getHeadline());
            System.out.println("   Category: " + article.getCategory());
            System.out.println("   URL: " + article.getUrl());
            System.out.println("   (Click or copy the URL above to access the article)");
            System.out.println("-------------------------------");
        }
    }

    // Fetch and display a random set of 5 articles
    public static void displayRandomArticles(String username, String category) {  //Since the dataset sample is very large, this methods shows 5 random aricles for user to view from each category

        List<Article> articles = loadArticles(category);

        if (articles.isEmpty()) {
            System.out.println("No articles found for category: " + category);
            return;
        }

        Random random = new Random();
        Set<Integer> seen = new HashSet<>();
        int count = 0;

        while (count < 5 && seen.size() < articles.size()) {
            int index = random.nextInt(articles.size());
            if (!seen.contains(index)) {
                seen.add(index);
                Article article = articles.get(index);
                System.out.println((count + 1) + ". Headline: " + article.getHeadline());
                System.out.println("   Category: " + article.getCategory());
                System.out.println("   URL: " + article.getUrl());
                System.out.println("-------------------------------");

                System.out.print("Did you like this article? (1: Like, 2: Skip, 3: Ignore): "); //After a news is viewed by user,  could choose any option among these
                Scanner scanner = new Scanner(System.in);
                int action = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                String actionStr;
                switch (action) {
                    case 1 -> actionStr = "LIKED";
                    case 2 -> actionStr = "SKIPPED";
                    case 3 -> actionStr = "IGNORED";
                    default -> {
                        System.out.println("Invalid input. No action recorded.");
                        continue;
                    }
                }

                // Save user interaction
                ReadingHistoryManager.saveReadingHistory(username, article, actionStr); //reading history will be saved in a csv file with every news liked,ignored and skipped by users.
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No articles found in the category.");
        }

    }
}
