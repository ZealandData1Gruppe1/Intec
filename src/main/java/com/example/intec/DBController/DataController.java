package com.example.intec.DBController;

import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
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
            String sql= "insert into registration (fname, lname, company, checkin, licenseid, locationid) VALUES ('"+r.getRegistreringPerson().getFornavn()+"','"+r.getRegistreringPerson().getEfternavn()+"','"+r.getRegistreringPerson().getFirma()+"','"+r.getRegistreringTidspunkt()+"','"+r.getRegistreringPerson().getIdNR()+"',"+locationID+")";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }
    public Firma hentTransportFirma(String firmanavn)
    {
        Firma firma = new Firma();

        try{
            String sql = "Hent Transportfirma";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        return firma;
    }
    public Firma hentOtherFirma(String firmanavn)
    {
        Firma firma = new Firma();
        try{
            String sql = "Select * from otherCompany where companyname = '"+firmanavn+"'";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return firma;
    }
    public void opretOtherFirma(String firmanavn)
    {
        Firma firma = new Firma();

        try{
            String sql = "INSERT INTO otherCompany(companyname) VALUES ('"+firmanavn+"')";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertPersComp(Person p)
    {
        try{
            String sql = "insert into PersComp (pid, cid) VALUES('"+p.getIdNR()+", "+p.getFirma().getID()+")";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void opretPerson(Person p)
    {
        try{
            String sql = "insert into person (idNR, fname,lname,companyID) VALUES ('"+p.getIdNR()+", '"+p.getFornavn()+"', '"+p.getEfternavn()+"', "+p.getFirma().getID()+")";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Person hentPerson(int ID)
    {
        try{
            String sql = "SELECT * from person WHERE idNR ="+ID;
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Person();
    }
}
