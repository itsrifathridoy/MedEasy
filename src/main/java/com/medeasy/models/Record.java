package com.medeasy.models;

import java.sql.Blob;

public class Record {
    private String recordID;
    private String userID;
    private String title;
    private String type;
    private String doctorName;
    private String date;
    private Blob recordLink;

    public Record(String recordID, String userID, String title, String type, String doctorName, String date, Blob recordLink) {
        this.recordID = recordID;
        this.userID = userID;
        this.title = title;
        this.type = type;
        this.doctorName = doctorName;
        this.date = date;
        this.recordLink = recordLink;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Blob getRecordLink() {
        return recordLink;
    }

    public void setRecordLink(Blob recordLink) {
        this.recordLink = recordLink;
    }
}
