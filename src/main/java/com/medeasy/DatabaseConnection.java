package com.medeasy;

import com.medeasy.users.Patient;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        this.connection = getConnection();
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://"+ Dotenv.load().get("DATABASE_HOSTNAME")+ ":"+Dotenv.load().get("DATABASE_PORT")+"/"+Dotenv.load().get("DATABASE_NAME"), Dotenv.load().get("DATABASE_USERNAME"), Dotenv.load().get("DATABASE_SECRET")
            );

        } catch (Exception e) {
            System.out.println(e);
        }
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
            patient.setPatientName(resultSet.getString(2));
            patient.setFatherNameBn(resultSet.getString(3));
            patient.setMotherNameBn(resultSet.getString(4));
            patient.setDob(resultSet.getString(5));
            patient.setEmail(resultSet.getString(6));
            patient.setAddressBn(resultSet.getString(7));
            patient.setAddressEn(resultSet.getString(8));
            patient.setOfficeNameBn(resultSet.getString(9));
            patient.setOfficeNameEn(resultSet.getString(10));
            patient.setUsername(resultSet.getString(11));
        }
        return patient;
    }


}
