package com.example.intec.DBController;

import com.example.intec.Entititer.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataController {
    private static DataController INSTANCE;
    private Connection connection;
    private Statement stmt;
    int locationID;

    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DataController(String location) {
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

    public static DataController getInstance(String location) {
        if(INSTANCE == null) {
            INSTANCE = new DataController(location);
        }

        return INSTANCE;
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

    public Login hentLogin(String brugernavn){
        Login login = new Login();
        try{
            String sql = "Select * from login where username = '"+brugernavn+"'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                login.setBrugernavn(rs.getString("username"));
                login.setKode(rs.getString("password"));
                login.setRolle(login.rollefromString(rs.getString("role")));
                login.setIdNR(rs.getInt("pid"));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return login;
    }

    public void deleteOldRegistrations(){
        Date now = new Date();
        Date twoYearsAgo = substractYears(now,2);
        try{
            String sql = "delete from registration where checkin < '"+timeFormatter(twoYearsAgo)+"'";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteUnusedPerson(){
        Date now = new Date();
        Date twoYearsAgo = substractYears(now,2);
        try{
            String sql = "delete from person where timestamp < '"+timeFormatter(twoYearsAgo)+"'";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Date substractYears(Date d, int years)
    {
        d.setYear(d.getYear()-years);
        return d;
    }
    public void sletGamleData() {
        deleteUnusedPerson();
        deleteOldRegistrations();
    }

    public void sletOplysningerOmPerson(int idnr){
        try{
            String sql1 = "DELETE from log where loginid ='" + idnr + "'";
            String sql2 = "Delete from registration where pid = '" + idnr +"'";
            String sql3 = "Delete from person where idnr = '" + idnr+"'";
            Statement stmt = connection.createStatement();
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);
            stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }

    public void insertLogin(Login l) {

            try{
                String sql = "insert into login (username, password, role,pid) VALUES ('"+l.getBrugernavn()+"','" +l.getKode() +"','" +l.getRolle()+"',"+l.getIdNR()+")";
                Statement stmt = connection.createStatement();
                stmt.execute(sql);
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    public void updateCompanyOnlist(Firma f)
    {
        try{
            String sql = "UPDATE company set onlist = 1 WHERE id = '"+f.getID()+"'";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateCopanyOfflist(Firma f)
    {
        try{
            String sql = "UPDATE company set onlist = 0 WHERE id = '"+f.getID()+"'";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Registrering> getRegistrationIDTime(int idnr, Date startdato, Date slutdato)
    {
        ArrayList<Registrering> registreringsListen = new ArrayList<>();
        try{
            String sql = "Select person.idnr, person.fname, person.lname, company.companyname, location.locationname, registration.id, registration.checkin \n" +
                    "From (((registration " +
                    "Inner join person on registration.pid = person.idnr) " +
                    "Inner join company on registration.cid = company.id) " +
                    "Inner join location on registration.locationid = location.id) " +
                    "Where idnr = "+idnr+" AND registration.checkin > '"+timeFormatter(startdato)+"' AND registration.checkin < '" +timeFormatter(slutdato)+"'";


            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Firma f = new Firma();
                Person p = new Person();
                Registrering r = new Registrering();
                f.setFirmanavn(rs.getString("companyname"));
                r.setFirma(f);
                p.setIdNR(rs.getInt("idnr"));
                p.setFornavn(rs.getString("fname"));
                p.setEfternavn(rs.getString("lname"));
                r.setRegistreringPerson(p);
                String date = rs.getString("checkin");
                try {
                    r.setTjekinTidspunkt(formater.parse(date));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                r.setLokation(rs.getString("locationname"));
                r.setId(rs.getInt("id"));
                registreringsListen.add(r);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return registreringsListen;

    }

    public ArrayList<Registrering> getregistrationWithID(int idnr)
    {

        ArrayList<Registrering> registreringsListen = new ArrayList<>();
        try{
            String sql = "Select person.idnr, person.fname, person.lname, company.companyname, location.locationname, registration.id, registration.checkin \n" +
                    "From (((registration " +
                    "Inner join person on registration.pid = person.idnr) " +
                    "Inner join company on registration.cid = company.id) " +
                    "Inner join location on registration.locationid = location.id) " +
                    "Where idnr = "+idnr;

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Firma f = new Firma();
                Person p = new Person();
                Registrering r = new Registrering();
                f.setFirmanavn(rs.getString("companyname"));
                r.setFirma(f);
                p.setIdNR(rs.getInt("idnr"));
                p.setFornavn(rs.getString("fname"));
                p.setEfternavn(rs.getString("lname"));
                r.setRegistreringPerson(p);
                String date = rs.getString("checkin");
                    try {
                        r.setTjekinTidspunkt(formater.parse(date));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                r.setLokation(rs.getString("locationname"));
                r.setId(rs.getInt("id"));
                registreringsListen.add(r);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return registreringsListen;
    }

    public ArrayList<Registrering> getregistrationWithTime(Date startdato, Date slutdato)
    {
        ArrayList<Registrering> registreringsListen = new ArrayList<>();
        try{
            String sql = "Select person.idnr, person.fname, person.lname, company.companyname, location.locationname, registration.id, registration.checkin \n" +
                    "From (((registration " +
                    "Inner join person on registration.pid = person.idnr) " +
                    "Inner join company on registration.cid = company.id) " +
                    "Inner join location on registration.locationid = location.id) " +
                    "Where registration.checkin > '"+timeFormatter(startdato)+"' AND registration.checkin < '" +timeFormatter(slutdato)+"'";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Firma f = new Firma();
                Person p = new Person();
                Registrering r = new Registrering();
                f.setFirmanavn(rs.getString("companyname"));
                r.setFirma(f);
                p.setIdNR(rs.getInt("idnr"));
                p.setFornavn(rs.getString("fname"));
                p.setEfternavn(rs.getString("lname"));
                r.setRegistreringPerson(p);
                String date = rs.getString("checkin");
                try {
                    r.setTjekinTidspunkt(formater.parse(date));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                r.setLokation(rs.getString("locationname"));
                r.setId(rs.getInt("id"));
                registreringsListen.add(r);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return registreringsListen;
    }

    public void logDatabaseSoegning(int loginid, String query){
        Date d = new Date();
        String dato = timeFormatter(d);

        try{
            String sql = "insert into log (loginid, datetime, search) VALUES ("+loginid+" ,'"+dato+"','"+query+"')";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePerson(Person P){
        Date d = new Date();
        String dato = timeFormatter(d);
        try{
            String sql = "";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
