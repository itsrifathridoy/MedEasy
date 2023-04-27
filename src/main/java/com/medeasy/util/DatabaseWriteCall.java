package com.medeasy.util;

import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseWriteCall extends Task<Integer> {

    private String sql;
    private PreparedStatement statement;
    private HashMap<Integer,Object> queries;
    private int rowsInserted;

    public DatabaseWriteCall(String sql, HashMap<Integer,Object> queries) {
        this.sql=sql;
        this.queries=queries;
    }

    private void runQuery(DatabaseWriteCall databaseWriteCall)
    {
        databaseWriteCall.setOnSucceeded(workerStateEvent -> {
            rowsInserted = databaseWriteCall.getValue();
        });
        new Thread(databaseWriteCall).start();
    }

    public int getRowsInserted(DatabaseWriteCall databaseWriteCall) {
        runQuery(databaseWriteCall);
        return rowsInserted;
    }

    @Override
    protected Integer call(){

        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            prepareStatement(queries);
            rowsInserted = statement.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


        return rowsInserted;
    }
    void prepareStatement(HashMap<Integer,Object> queries) throws SQLException {
        for(int index:queries.keySet())
        {
            if(queries.get(index) instanceof String)
            {
                statement.setString(index, (String) queries.get(index));
            }
            else if(queries.get(index) instanceof Integer)
            {

                statement.setInt(index, (int) queries.get(index));
            }
            else if(queries.get(index) instanceof Boolean)
            {

                statement.setBoolean(index, (boolean) queries.get(index));
            }
            else if(queries.get(index) instanceof Double)
            {

                statement.setDouble(index, (double) queries.get(index));
            }

        }
    }
}
