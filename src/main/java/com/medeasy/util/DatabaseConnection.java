package com.medeasy.util;

import com.medeasy.models.Patient;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() throws SQLException,ClassNotFoundException{
        this.connection = getConnection();
    }

    public Connection getConnection() throws SQLException,ClassNotFoundException{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://"+ Dotenv.load().get("DATABASE_HOSTNAME")+ ":"+Dotenv.load().get("DATABASE_PORT")+"/"+Dotenv.load().get("DATABASE_NAME"), Dotenv.load().get("DATABASE_USERNAME"), Dotenv.load().get("DATABASE_SECRET")
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
    public Patient getPatient(String emailOrBirthId,String cell) throws SQLException {
        Patient patient = new Patient();
        String sql = "SELECT * FROM `patients` WHERE" + "`"+cell+"` LIKE '"+emailOrBirthId+"'";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            patient.setbId(resultSet.getString(1));
            patient.setPersonNameBn(resultSet.getString(2));
            patient.setPersonNameEn(resultSet.getString(3));
            patient.setFatherNameBn(resultSet.getString(4));
            patient.setFatherNameEn(resultSet.getString(5));
            patient.setMotherNameBn(resultSet.getString(6));
            patient.setMotherNameEn(resultSet.getString(7));
            patient.setDob(resultSet.getString(8));
            patient.setEmail(resultSet.getString(9));
            patient.setAddressBn(resultSet.getString(10));
            patient.setAddressEn(resultSet.getString(11));
            patient.setOfficeNameBn(resultSet.getString(12));
            patient.setOfficeNameEn(resultSet.getString(13));
            patient.setUsername(resultSet.getString(14));
        }
        return patient;
    }


}
