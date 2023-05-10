package com.example.intec.DBController;

import com.example.intec.Entititer.Registrering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataController {
    private Connection connection;
    private Statement stmt;
    int locationID;
    public DataController(String Location) {
        connection = null;
        stmt = null;
        if(Location.equals("Haslev"))
        {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inteceu", "root", "Password1234");
            locationID = 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        }
        if(Location.equals("Boston"))
        {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intecus", "root", "Password1234");
                locationID = 2;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void insertRegistration(Registrering r)
    {
        try {
        String sql = "Insert into registration(fname, lname, company,checkin, licenseid,locationid) VALUES('"+r.getRegistreringPerson().getFornavn()+"','"+
                r.getRegistreringPerson().getEfternavn()+"','"+r.getRegistreringPerson().getFirma()+"','"+r.getRegistreringTidspunkt()+"','"+
                r.getRegistreringPerson().getKoerekortNR()+"',"+locationID;
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }
}
