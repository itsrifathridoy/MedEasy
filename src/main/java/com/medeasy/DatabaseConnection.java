package com.medeasy;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private Connection connection;

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

}
