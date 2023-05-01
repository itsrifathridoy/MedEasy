package com.medeasy.models;

import java.sql.Blob;

public class Doctor extends User{
    private String personNameEn;
    private String gender;
    private String dob;
    private String specialities;
    private String appointments;
    private String numOfOperations;
    private String qualification;
    private String designation;
    private String hospital;
    private String mobile;
    private String hospitalAddress;

    public Doctor(String userID,String personNameEn, String specialities, String qualification, String designation,Blob blob) {
        super(userID,blob);
        this.personNameEn = personNameEn;
        this.specialities = specialities;
        this.qualification = qualification;
        this.designation = designation;
    }

    public Doctor(String userID, String email, String role, Blob blob, String personNameEn, String gender, String dob, String specialities, String appointments, String numOfOperations, String qualification, String designation, String hospital, String mobile, String hospitalAddress) {
        super(userID, email, role, blob);
        this.personNameEn = personNameEn;
        this.gender = gender;
        this.dob = dob;
        this.specialities = specialities;
        this.appointments = appointments;
        this.numOfOperations = numOfOperations;
        this.qualification = qualification;
        this.designation = designation;
        this.hospital = hospital;
        this.mobile = mobile;
        this.hospitalAddress = hospitalAddress;
    }

    public Doctor() {

    }

    public String getPersonNameEn() {
        return personNameEn;
    }

    public void setPersonNameEn(String personNameEn) {
        this.personNameEn = personNameEn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getAppointments() {
        return appointments;
    }

    public void setAppointments(String appointments) {
        this.appointments = appointments;
    }

    public String getNumOfOperations() {
        return numOfOperations;
    }

    public void setNumOfOperations(String numOfOperations) {
        this.numOfOperations = numOfOperations;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }
}
