package com.medeasy.util;

import com.medeasy.models.Patient;
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

    public void queryData(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        connection.close();
    }

    public Patient getPatient(String emailOrBirthId, String cell) throws SQLException {

                Patient patient = new Patient();
                String sql = "SELECT * FROM `patients` WHERE" + "`" + cell + "` LIKE '" + emailOrBirthId + "'";
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
                }
                return patient;

    }


}
