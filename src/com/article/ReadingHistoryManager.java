package com.article;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;

public class ReadingHistoryManager {

    private static final String HISTORY_FILE = "reading_history.csv";

    // Save interaction
    public static void saveReadingHistory(String username, Article article, String action) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(HISTORY_FILE, true))) {
            String[] record = {
                    username,
                    article.getHeadline(),
                    article.getDescription(),
                    article.getUrl(),
                    article.getCategory(),
                    action,
                    new Date().toString()
            };
            writer.writeNext(record);
        } catch (IOException e) {
            System.out.println("Error saving reading history: " + e.getMessage());
        }
    }

    // Load history for a specific user
    public static List<String[]> getUserHistory(String username) {
        List<String[]> history = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(HISTORY_FILE))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line[0].equalsIgnoreCase(username)) {
                    history.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading history: " + e.getMessage());
        }
        return history;
    }

    // Display history for the user, showing only liked articles
    public static void displayUserHistory(String username) {
        List<String[]> history = getUserHistory(username);
        if (history.isEmpty()) {
            System.out.println("No history found for user: " + username);
        } else {
            System.out.println("Reading History:");
            for (String[] record : history) {
                if (record.length >= 6 && "LIKED".equalsIgnoreCase(record[5])) {
                    System.out.println("Liked - \"" + record[1] + " (" + record[3] + ")\"");
                }
            }
        }
    }

}
