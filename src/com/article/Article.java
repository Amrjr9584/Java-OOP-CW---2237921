package com.article;

public class Article {
    private String headline;
    private String description;
    private String content;
    private String url;
    private String category;

    // Constructor to initialize the article object
    public Article(String headline, String description, String content, String url, String category) {
        this.headline = headline;
        this.description = description;
        this.content = content;
        this.url = url;
        this.category = category;
    }

    // Getters
    public String getHeadline() {
        return headline;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    // Convert the article object to CSV format
    public String toCSV() {
        return headline + "," + description + "," + content + "," + url + "," + category;
    }
}
