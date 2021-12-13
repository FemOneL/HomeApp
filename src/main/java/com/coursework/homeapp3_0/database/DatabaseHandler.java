package com.coursework.homeapp3_0.database;

import java.sql.*;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void writeInDb(Appliance appliance) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.APPLIANCE_TABLE + "(" +
                Const.APPLIANCE_NAME + "," + Const.APPLIANCE_MODEL + "," +
                Const.APPLIANCE_COMPANY + "," + Const.APPLIANCE_POWER + "," +
                Const.APPLIANCE_STATUS + ")" + "VALUES(?,?,?,?,?)";

        PreparedStatement preparedStatement =  getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1, appliance.getName());
        preparedStatement.setString(2, appliance.getModel());
        preparedStatement.setString(3, appliance.getCompany());
        preparedStatement.setString(4, Integer.toString(appliance.getPower()));
        preparedStatement.setString(5, appliance.getStatus());

        preparedStatement.executeUpdate();
    }

    public void changeInDb(Appliance appliance) throws SQLException, ClassNotFoundException {
       String update = "UPDATE " + Const.APPLIANCE_TABLE + " SET " + Const.APPLIANCE_STATUS
                + " = " + "?" + " WHERE " + Const.APPLIANCE_NAME + "=? AND " + Const.APPLIANCE_MODEL + "=?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);
        preparedStatement.setString(1, appliance.getStatus());
        preparedStatement.setString(2, appliance.getName());
        preparedStatement.setString(3, appliance.getModel());
        preparedStatement.executeUpdate();
    }

    public ResultSet getAllPluggedFromDb() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.APPLIANCE_TABLE + " WHERE " +
                Const.APPLIANCE_STATUS + " =?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        preparedStatement.setString(1, "on");
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getFromDb() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.APPLIANCE_TABLE;
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public void removeFromDb(Appliance appliance) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM " + Const.APPLIANCE_TABLE + " WHERE " +
                Const.APPLIANCE_NAME + "=? AND " + Const.APPLIANCE_MODEL + "=?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
        preparedStatement.setString(1, appliance.getName());
        preparedStatement.setString(2, appliance.getModel());
        preparedStatement.executeUpdate();
    }
}
