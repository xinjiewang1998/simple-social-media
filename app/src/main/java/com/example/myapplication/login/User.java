package com.example.myapplication;

public class User {
    private String email;
    private String id;
    public User(){}

    public User(String name, String id) {
        this.email = name;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
