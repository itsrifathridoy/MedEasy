package com.medeasy.models;

import java.sql.Blob;

public class User {
    protected String userID;
    protected String email;
    protected String role;
    protected Blob blob;

    public User(String userID, String email, String role) {
        this.userID = userID;
        this.email = email;
        this.role = role;
    }

    public User(String userID, String email, String role, Blob blob) {
        this.userID = userID;
        this.email = email;
        this.role = role;
        this.blob = blob;
    }

    public User(String userID, String email) {
        this.userID = userID;
        this.email = email;
    }

    public User() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
