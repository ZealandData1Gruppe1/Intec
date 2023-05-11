package com.example.intec.DBController;

import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;

import java.sql.*;
import java.util.ArrayList;

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
            String sql= "insert into registration (checkin, pid, locationid) VALUES ('"+r.getTjekinTidspunkt()+"' , "+r.getRegistreringPerson().getIdNR()+" , "+locationID+")";
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
                String sql = "Select * from transportcompany WHERE companyname ='"+firmanavn+"'";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    firma.setID(rs.getInt("id"));
                    firma.setFirmanavn(rs.getString("companyname"));
                }
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
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                firma.setFirmanavn(rs.getString("companyname"));
                firma.setID(rs.getInt("id"));
            }
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
            String sql = "insert into percomp (pid, cid) VALUES("+p.getIdNR()+" , "+p.getFirma().getID()+")";
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
            String sql = "insert into person (idnr, fname, lname, companyID) VALUES ("+p.getIdNR()+" , '"+p.getFornavn()+"' , '"+p.getEfternavn()+"', "+p.getFirma().getID()+")";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }
    public Person hentPerson(int ID)
    {
        Person person = new Person();
        try{
            String sql = "SELECT * from person WHERE idnr ="+ID;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                    person.setIdNR(rs.getInt("idnr"));
                    person.setFornavn(rs.getString("fname"));
                    person.setEfternavn(rs.getString("lname"));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public ArrayList<Firma> hentTransportFirmaListe()
    {
        ArrayList<Firma> transportFirmaListen = new ArrayList<>();
        try{
            String sql = "Select * from transportcompany";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
            Firma f1 = new Firma();
            f1.setID(rs.getInt("id"));
            f1.setFirmanavn(rs.getString("companyname"));
            transportFirmaListen.add(f1);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transportFirmaListen;
    }
}
