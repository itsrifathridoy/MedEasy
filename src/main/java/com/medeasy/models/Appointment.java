package com.medeasy.models;

public class Appointment {
    private String appointmentID;
    private String userID;
    private String doctorID;
    private String discription;
    private String status;

    public Appointment(String appointmentID, String userID, String doctorID, String discription, String status) {
        this.appointmentID = appointmentID;
        this.userID = userID;
        this.doctorID = doctorID;
        this.discription = discription;
        this.status = status;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
