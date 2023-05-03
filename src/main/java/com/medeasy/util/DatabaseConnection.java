package com.medeasy.util;

import com.medeasy.models.Admin;
import com.medeasy.models.Doctor;
import com.medeasy.models.Patient;
import com.medeasy.models.User;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.concurrent.Task;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() throws SQLException, ClassNotFoundException {
        this.connection = getConnection();
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + Dotenv.load().get("DATABASE_HOSTNAME") + ":" + Dotenv.load().get("DATABASE_PORT") + "/" + Dotenv.load().get("DATABASE_NAME"), Dotenv.load().get("DATABASE_USERNAME"), Dotenv.load().get("DATABASE_SECRET")
        );

        return connection;
    }

    public void insideData(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();
    }

    public ResultSet queryData(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public User getUser(String emailOrUserID, String cell) throws SQLException {

        User user = new User();
        String sql = "SELECT userID,email,role FROM `users` WHERE" + "`" + cell + "` LIKE '" + emailOrUserID + "'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            user.setUserID(resultSet.getString(1));
            user.setRole(resultSet.getString(2));
            user.setEmail(resultSet.getString(3));

        }
        return user;

    }

    public Patient getPatient(String emailOrBirthId, String cell) throws SQLException {

                Patient patient = new Patient();
                String sql = "SELECT * FROM `patients` WHERE" + " `" + cell + "` LIKE '" + emailOrBirthId + "'";
                PreparedStatement statement = connection.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    patient.setUserID(resultSet.getString(1));
                    patient.setbId(resultSet.getString(2));
                    patient.setPersonNameBn(resultSet.getString(3));
                    patient.setPersonNameEn(resultSet.getString(4));
                    patient.setFatherNameBn(resultSet.getString(5));
                    patient.setFatherNameEn(resultSet.getString(6));
                    patient.setMotherNameBn(resultSet.getString(7));
                    patient.setMotherNameEn(resultSet.getString(8));
                    patient.setDob(resultSet.getString(9));
                    patient.setEmail(resultSet.getString(10));
                    patient.setAddressBn(resultSet.getString(11));
                    patient.setAddressEn(resultSet.getString(12));
                    patient.setOfficeNameBn(resultSet.getString(13));
                    patient.setOfficeNameEn(resultSet.getString(14));
                    patient.setUsername(resultSet.getString(15));
                    patient.setBloodGroup(resultSet.getString(16));
                    patient.setLastAppointment(resultSet.getString(17));
                    patient.setDisease(resultSet.getString(18));
                    patient.setBlob(resultSet.getBlob(19));
                    patient.setHeight(resultSet.getString(20));
                    patient.setWeight(resultSet.getString(21));
                }
                return patient;

    }
    public Admin getAdmin(String emailOrUserID, String cell) throws SQLException {

        Admin admin = new Admin();
        String sql = "SELECT * FROM `admins` WHERE" + "`" + cell + "` LIKE '" + emailOrUserID + "'";
        PreparedStatement statement = connection.prepareStatement(sql);
        System.out.println("Before");
        ResultSet resultSet = statement.executeQuery();
        System.out.println("After");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(2));
            admin.setUserID(resultSet.getString(1));
            admin.setPersonNameEn(resultSet.getString(2));
            admin.setEmail(resultSet.getString(3));
            admin.setBlob(resultSet.getBlob(4));
        }
        return admin;

    }

    public Doctor getDoctor(String userID, String cell) throws SQLException {

        Doctor doctor = new Doctor();
        String sql = "SELECT * FROM `doctors` WHERE" + " `" + cell + "` LIKE '" + userID + "'";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            doctor.setUserID(resultSet.getString(1));
            doctor.setPersonNameEn(resultSet.getString(2));
            doctor.setGender(resultSet.getString(3));
            doctor.setDob(resultSet.getString(4));
            doctor.setEmail(resultSet.getString(5));
            doctor.setMobile(resultSet.getString(6));
            doctor.setSpecialities(resultSet.getString(7));
            doctor.setAppointments(resultSet.getString(8));
            doctor.setNumOfOperations(resultSet.getString(9));
            doctor.setQualification(resultSet.getString(10));
            doctor.setDesignation(resultSet.getString(11));
            doctor.setHospital(resultSet.getString(12));
            doctor.setHospitalAddress(resultSet.getString(13));
            doctor.setBlob(resultSet.getBlob(14));
        }
        return doctor;

    }



}
