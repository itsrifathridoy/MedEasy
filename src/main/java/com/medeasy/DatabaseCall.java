package com.medeasy;

import javafx.concurrent.Task;

import java.sql.*;
import java.util.HashMap;

public class DatabaseCall extends Task<ResultSet> {

    private String sql;
    private PreparedStatement statement;
    private HashMap<Integer,Object> queries;
    private ResultSet resultSet;

    public DatabaseCall(String sql,HashMap<Integer,Object> queries) {
        this.sql=sql;
        this.queries=queries;
    }


    @Override
    protected ResultSet call(){

        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            prepareStatement(queries);
            resultSet = statement.executeQuery();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


        return resultSet;
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
