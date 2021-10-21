package com.example.myapplication.login;

/**
 * class to store the user information receive from firebase.
 */
public class User {
    //Field
    private String email;
    private String id;

    // Constructor
    public User() {
    }

    public User(String name, String id) {
        this.email = name;
        this.id = id;
    }

    //getter and setter.
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
