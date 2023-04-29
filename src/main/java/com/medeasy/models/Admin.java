package com.medeasy.models;

import java.sql.Blob;

public class Admin extends User{
    private String personNameEn;

    public Admin(String userID, String email, String role, Blob blob, String personNameEn) {
        super(userID, email, role, blob);
        this.personNameEn = personNameEn;
    }

    public Admin() {
    }

    public String getPersonNameEn() {
        return personNameEn;
    }

    public void setPersonNameEn(String personNameEn) {
        this.personNameEn = personNameEn;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "personNameEn='" + personNameEn + '\'' +
                ", userID='" + userID + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", blob=" + blob +
                '}';
    }
}
