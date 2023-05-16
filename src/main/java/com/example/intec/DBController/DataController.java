package com.example.intec.DBController;

import com.example.intec.Entititer.Admin;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataController {
    private Connection connection;
    private Statement stmt;
    int locationID;
    public DataController(String location) {
        connection = null;
        stmt = null;
        if(location.equals("Haslev") || location.equals(""))
        {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inteceu", "root", "Password1234");
            locationID = 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        }
        if(location.equals("Boston"))
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
            String sql= "insert into registration (checkin, pid, locationid, cid) VALUES ('"+timeFormatter(r.getTjekinTidspunkt()) +"' , "+r.getRegistreringPerson().getIdNR()+" , "+locationID+" , "+r.getFirma().getID()+")";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }
    public Firma hentFirma(String firmanavn)
    {
        Firma firma = new Firma();
            try{
                String sql = "Select * from company WHERE companyname ='"+firmanavn+"'";
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

    public void opretFirma(String firmanavn)
    {
        Firma firma = new Firma();
        try{
            String sql = "INSERT INTO company(companyname) VALUES ('"+firmanavn+"')";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void opretPerson(Person p)
    {
        Date d = new Date();
        try{
            String sql = "insert into person (idnr, fname, lname, timestamp) VALUES ("+p.getIdNR()+" , '"+p.getFornavn()+"' , '"+p.getEfternavn()+"' , '"+timeFormatter(d)+"')";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
        }

    }
    public Person hentPerson(int idNR)
    {
        Person person = new Person();
        try{
            String sql = "SELECT * from person WHERE idnr ="+idNR;
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
        Date d = new Date();
        try{
            String sql = "Update person set timestamp ='"+timeFormatter(d)+"' WHERE idnr ="+idNR;
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
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
            String sql = "Select * from company where onlist = 1";
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

    private String timeFormatter (Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tid = sdf.format(d);
        return tid;
    }

    public Admin hentAdmin(String brugernavn){
        Admin admin = new Admin();
        try{
            String sql = "Select * from admin where brugernavn = '"+brugernavn+"'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                admin.setBrugernavn(rs.getString("brugernavn"));
                admin.setKode(rs.getString("kode"));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }
}
