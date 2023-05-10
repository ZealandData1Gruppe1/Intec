package com.example.intec.DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataController {
    private Connection connection;
    private Statement stmt;

    DataController() {
        connection = null;
        stmt = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inteceu", "root", "Password1234");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
