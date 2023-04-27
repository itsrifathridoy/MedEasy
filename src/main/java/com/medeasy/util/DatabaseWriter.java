package com.medeasy.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseWriter extends Thread {

    private String sql;
    private Map<Integer, Object> queryParams;
    private int rowInserted;
    public DatabaseWriter(String sql, Map<Integer, Object> queryParams) {
        this.sql = sql;
        this.queryParams = queryParams;
        start();
    }

    @Override
    public void run() {
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object> param : queryParams.entrySet()) {
                stmt.setObject(param.getKey(), param.getValue());
            }
            rowInserted = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getRowInserted() {
        return rowInserted;
    }

}
