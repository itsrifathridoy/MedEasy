package com.medeasy.models;

public class Appointment {
    private String appointmentID;
    private String userID;
    private String doctorID;
    private String discription;
    private String status;
    private String currentStage;
    private String name;
    private String time;

    public String getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Appointment(String appointmentID, String userID, String doctorID, String discription, String status) {
        this.appointmentID = appointmentID;
        this.userID = userID;
        this.doctorID = doctorID;
        this.discription = discription;
        this.status = status;
    }
    public Appointment(String appointmentID, String userID, String doctorID, String discription, String status,String currentStage,String name,String time) {
        this.appointmentID = appointmentID;
        this.userID = userID;
        this.doctorID = doctorID;
        this.discription = discription;
        this.status = status;
        this.currentStage = currentStage;
        this.name=name;
        this.time =time;
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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) obj;
        return appointment.userID.equals(this.userID);
    }
}
