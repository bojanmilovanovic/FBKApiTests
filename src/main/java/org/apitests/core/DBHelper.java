package org.apitests.core;

import java.io.IOException;
import java.sql.*;

public class DBHelper {

    private Connection connection;
    private static final String URL = "jdbc:oracle:thin:@c20-k8s-ora-01.crealogix.net:1521:";

    public void openDBConnectionFundings() throws SQLException, ClassNotFoundException {
        String driverName = "oracle.jdbc.driver.OracleDriver";
        Class.forName(driverName);
        connection = DriverManager.getConnection(URL+Globals.SID, "funding", "funding");
    }

    public void openDBConnectionTasks() throws SQLException, ClassNotFoundException {
        String driverName = "oracle.jdbc.driver.OracleDriver";
        Class.forName(driverName);
        connection = DriverManager.getConnection(URL+Globals.SID, "tasks", "tasks");
    }

    public ResultSet runQuery(String query) {
        Statement stmt;
        ResultSet rset = null;
        try {
            stmt = connection.createStatement();
            rset = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rset;
    }

    public int runUpdate(String update) throws Exception {
        Statement stmt;
        int num = 0;
        try {
            stmt = connection.createStatement();
            num = stmt.executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (num != 0) {
            return num;
        }
        throw new Exception("DB update was not successfull");
    }

    public void closeConnection() throws IOException {
        try {
            connection.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
