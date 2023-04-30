package com.medeasy.models;

import java.sql.Blob;

public class Patient extends User{
    private String bId;
    private String personNameBn;
    private String personNameEn;
    private String dob;
    private String fatherNameBn;
    private String fatherNameEn;
    private String motherNameBn;
    private String motherNameEn;
    private String addressBn;
    private String addressEn;
    private String officeNameBn;
    private String officeNameEn;
    private String username;
    private String lastAppointment;
    private String disease;
    private String bloodGroup;

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLastAppointment() {
        return lastAppointment;
    }

    public void setLastAppointment(String lastAppointment) {
        this.lastAppointment = lastAppointment;
    }

    public Patient(String userID, String username, String lastAppointment, String bloodGroup, String disease,Blob blob) {
        super(userID, blob);
        this.username = username;
        this.lastAppointment = lastAppointment;
        this.disease = disease;
        this.bloodGroup = bloodGroup;
    }

    public Patient()
    {

    }
    public Patient(String personNameBn,String personNameEn, String dob, String fatherNameBn,String fatherNameEn, String motherNameBn, String motherNameEn, String addressBn, String addressEn, String officeNameBn, String officeNameEn) {
        this.personNameBn = personNameBn;
        this.personNameEn = personNameEn;
        this.dob = dob;
        this.fatherNameBn = fatherNameBn;
        this.fatherNameEn = fatherNameEn;
        this.motherNameBn = motherNameBn;
        this.motherNameEn = motherNameEn;
        this.addressBn = addressBn;
        this.addressEn = addressEn;
        this.officeNameBn = officeNameBn;
        this.officeNameEn = officeNameEn;
    }
    public Patient(String bId,String personNameBn,String personNameEn, String dob, String fatherNameBn,String fatherNameEn, String motherNameBn, String motherNameEn, String addressBn, String addressEn, String officeNameBn, String officeNameEn) {
        this.bId = bId;
        this.personNameBn = personNameBn;
        this.personNameEn = personNameEn;
        this.dob = dob;
        this.fatherNameBn = fatherNameBn;
        this.fatherNameEn = fatherNameEn;
        this.motherNameBn = motherNameBn;
        this.motherNameEn = motherNameEn;
        this.addressBn = addressBn;
        this.addressEn = addressEn;
        this.officeNameBn = officeNameBn;
        this.officeNameEn = officeNameEn;
    }
    public Patient(String userId,String email, String role,String bId,String personNameBn,String personNameEn, String dob, String fatherNameBn,String fatherNameEn, String motherNameBn, String motherNameEn, String addressBn, String addressEn, String officeNameBn, String officeNameEn) {
        super(userId,email,role);
        this.bId = bId;
        this.personNameBn = personNameBn;
        this.personNameEn = personNameEn;
        this.dob = dob;
        this.fatherNameBn = fatherNameBn;
        this.fatherNameEn = fatherNameEn;
        this.motherNameBn = motherNameBn;
        this.motherNameEn = motherNameEn;
        this.addressBn = addressBn;
        this.addressEn = addressEn;
        this.officeNameBn = officeNameBn;
        this.officeNameEn = officeNameEn;
    }
    public Patient(String bId,String name, String dob, String fatherNameBn, String motherNameBn, String addressBn, String addressEn, String officeNameBn, String officeNameEn) {
        this.personNameBn = name;
        this.dob = dob;
        this.bId = bId;
        this.fatherNameBn = fatherNameBn;
        this.motherNameBn = motherNameBn;
        this.addressBn = addressBn;
        this.addressEn = addressEn;
        this.officeNameBn = officeNameBn;
        this.officeNameEn = officeNameEn;
    }

    public Patient(Patient patient,String username,String email)

    {

        this.personNameBn = patient.personNameBn;
        this.personNameEn = patient.personNameEn;
        this.bId = patient.bId;
        this.dob = patient.dob;
        this.fatherNameBn = patient.fatherNameBn;
        this.fatherNameEn = patient.fatherNameEn;
        this.motherNameBn = patient.motherNameBn;
        this.motherNameEn = patient.motherNameEn;
        this.addressBn = patient.addressBn;
        this.addressEn = patient.addressEn;
        this.officeNameBn = patient.officeNameBn;
        this.officeNameEn = patient.officeNameEn;
        this.username = username;
        this.email = email;

    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPersonNameBn() {
        return personNameBn;
    }

    public void setPersonNameBn(String personNameBn) {
        this.personNameBn = personNameBn;
    }

    public String getPersonNameEn() {
        return personNameEn;
    }

    public void setPersonNameEn(String personNameEn) {
        this.personNameEn = personNameEn;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFatherNameBn() {
        return fatherNameBn;
    }

    public void setFatherNameBn(String fatherNameBn) {
        this.fatherNameBn = fatherNameBn;
    }

    public String getMotherNameBn() {
        return motherNameBn;
    }

    public void setMotherNameBn(String motherNameBn) {
        this.motherNameBn = motherNameBn;
    }

    public String getAddressBn() {
        return addressBn;
    }

    public String getFatherNameEn() {
        return fatherNameEn;
    }

    public void setFatherNameEn(String fatherNameEn) {
        this.fatherNameEn = fatherNameEn;
    }

    public String getMotherNameEn() {
        return motherNameEn;
    }

    public void setMotherNameEn(String motherNameEn) {
        this.motherNameEn = motherNameEn;
    }

    public void setAddressBn(String addressBn) {
        this.addressBn = addressBn;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getOfficeNameBn() {
        return officeNameBn;
    }

    public void setOfficeNameBn(String officeNameBn) {
        this.officeNameBn = officeNameBn;
    }

    public String getOfficeNameEn() {
        return officeNameEn;
    }

    public void setOfficeNameEn(String officeNameEn) {
        this.officeNameEn = officeNameEn;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "bId='" + bId + '\'' +
                ", personNameBn='" + personNameBn + '\'' +
                ", personNameEn='" + personNameEn + '\'' +
                ", dob='" + dob + '\'' +
                ", fatherNameBn='" + fatherNameBn + '\'' +
                ", fatherNameEn='" + fatherNameEn + '\'' +
                ", motherNameBn='" + motherNameBn + '\'' +
                ", motherNameEn='" + motherNameEn + '\'' +
                ", addressBn='" + addressBn + '\'' +
                ", addressEn='" + addressEn + '\'' +
                ", officeNameBn='" + officeNameBn + '\'' +
                ", officeNameEn='" + officeNameEn + '\'' +
                ", username='" + username + '\'' +
                ", lastAppointment='" + lastAppointment + '\'' +
                ", disease='" + disease + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", userID='" + userID + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", blob=" + blob +
                '}';
    }
}
