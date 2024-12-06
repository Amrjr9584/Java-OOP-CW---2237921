package com.user;

import java.util.Objects;

public class User {
    private String username;
    private String password;

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Override equals method to compare users by username
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    // Override hashCode for compatibility with equals
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    // Convert user object to a string suitable for CSV storage
    @Override
    public String toString() {
        return username + "," + password;
    }
}
