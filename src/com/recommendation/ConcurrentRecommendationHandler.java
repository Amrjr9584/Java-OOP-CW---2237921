package com.recommendation;



import com.article.Article;
import com.article.ArticleHandler;
import com.article.ReadingHistoryManager;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrentRecommendationHandler {

    private final ExecutorService executor;

    public ConcurrentRecommendationHandler() {
        this.executor = Executors.newCachedThreadPool();
    }

    // Method to recommend articles for a user
    public Future<List<Article>> getRecommendedArticles(String username) {
        return executor.submit(() -> recommendForUser(username));
    }
    public List<Article> recommendForUser(String username) {
        // Step 1: Fetch user's preferred categories from their history (liked articles)
        List<String[]> history = ReadingHistoryManager.getUserHistory(username);
        List<String> likedCategories = new ArrayList<>();

        // Get liked categories
        for (String[] record : history) {
            if (record.length >= 6 && "LIKED".equalsIgnoreCase(record[5])) {
                likedCategories.add(record[4]); // Add liked category to the list
            }
        }

        //  If no liked categories are found, return an empty list with a message
        if (likedCategories.isEmpty()) {
            System.out.println("No recommendations to show. Please like articles.");
            return Collections.emptyList();
        }

        // Step 2: Create a list to store recommendations
        List<Article> recommendations = new ArrayList<>();

        // Step 3: Fetch articles for each liked category
        for (String category : likedCategories) {
            if (recommendations.size() >= 15) break;  // Stop if we have 15 recommendations

            List<Article> categoryArticles = ArticleHandler.loadArticles(category);
            for (Article article : categoryArticles) {
                if (recommendations.size() < 15) {
                    recommendations.add(article);  // Add article to recommendations
                } else {
                    break;
                }
            }
        }

        // Step 4: Return up to 15 articles
        return recommendations.subList(0, Math.min(15, recommendations.size()));
    }



    // Gracefully shut down the executor service
    public void shutdown() {
        try {
            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

