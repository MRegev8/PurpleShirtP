package com.example.purpleshirt;

public class Project {
    private String username;
    private String message;

    public Project() {
    }

    public Project(String username, String message) {
        this.username = username;
        this.message = message+"\n ________________________________________________";
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
