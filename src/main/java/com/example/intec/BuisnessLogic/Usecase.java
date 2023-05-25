package com.example.intec.BuisnessLogic;

import com.example.intec.DBController.DataController;
import com.example.intec.Entititer.Login;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;

import java.util.Date;
import java.util.ArrayList;
import java.util.TimeZone;

import static com.example.intec.DBController.DataController.getInstance;

public class Usecase {

    private Login userVerified = new Login(0,"","","","");
    private String location = "";
    private DataController db;

    public Usecase() {
        this.db = getInstance(setLocation());
    }


    public void registrerPerson(Person p, Firma f, String otherfirma) {
        Person person = new Person();
        Firma firma = new Firma();
        if (f.getFirmanavn() == null) {
            if (findesCompany(otherfirma) == false) {
                db.opretFirma(otherfirma);
                firma = db.hentFirma(otherfirma);
            } else {
                firma = db.hentFirma(otherfirma);
            }
        }
        else
        {
            firma = f;
        }
            if (findesPerson(p.getIdNR()) == false) {
                db.opretPerson(p);
                person = db.hentPerson(p.getIdNR());
            }
            person = p;
            //Her skal db.updatePerson kaldes??
        Date nu = new Date();
        Registrering r = new Registrering(person,firma, nu, location);
        db.insertRegistration(r);

    }
    public ArrayList<Firma> hentAlleTransportFirma() {
        return db.hentTransportFirmaListe();
    }

    public boolean findesPerson(int idnr) {
        ;
        Person p1 = db.hentPerson(idnr);
        if (p1.getIdNR() > 0) {
            return true;
        } else return false;
    }

    public boolean findesBrugernavn(String brugernavn) {
        ;
        Login l1 = db.hentLogin(brugernavn);
        if (l1.getIdNR() > 0) {
            return true;
        } else return false;
    }

    public boolean findesCompany(String firmanavn) {
        if (db.hentFirma(firmanavn).getID() > 0) {
            return true;
        } else return false;
    }

    public boolean checkNavnForIkkeBogstaver(String navn){
        if (navn == null) {
            return false;
        }
        for (int i = 0; i < navn.length(); i++) {
            if ((Character.isLetter(navn.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    public boolean checkID(int id){
        if (id == 0) {
            return false;
        }
        return true;
    }

    public String setLocation() {
        TimeZone t = TimeZone.getDefault();
        String defaultLocation = t.getID();
        String continentName = defaultLocation.substring(0, Math.min(defaultLocation.length(), 6));

        if (continentName.equals("Europe")) {
            location = "Haslev";
            return location;
        } else {
            location = "Boston";
            return location;}
    }
    public String adminLogin (Login a){
        Login dataLogin = db.hentLogin(a.getBrugernavn());
        if (a.getBrugernavn().equals(dataLogin.getBrugernavn()) && a.getKode().equals(dataLogin.getKode())){
            userVerified = dataLogin;
            return dataLogin.getRolle();
        }
        else
            return "false";
    }
    public Boolean shouldDisplayEnglish()
    {

        if( location.equalsIgnoreCase("Haslev"))
        {
            return false;
        }
        if (location.equalsIgnoreCase("Boston"))
        {
            return true;
        }
        return true;
    }
    public Boolean sletOplysningerForID(int idnr){
        db.sletOplysningerOmPerson(idnr);
        return findesPerson(idnr);
    }
    public void sletGamleOplysninger()
    {
        db.sletGamleData();
    }
    public boolean opretLogin(Login l ) {
        if(findesPerson(l.getIdNR()) == false){
            Person p = new Person();
            p.setIdNR(l.getIdNR());
            p.setFornavn(l.getFornavn());
            p.setEfternavn(l.getEfternavn());
            db.opretPerson(p);
        }
        if(findesBrugernavn(l.getBrugernavn()) == true){
            return false;
        }
        db.insertLogin(l);
        return true;
    }
    public void addToCompanyList(String navn)
    {
        if(findesCompany(navn) == false)
        {
          db.opretFirma(navn);
        }
        Firma f =db.hentFirma(navn);
        db.updateCompanyOnlist(f);
    }
    public void removeCompanyFromList(String navn)
    {

        Firma f =db.hentFirma(navn);
        db.updateCopanyOfflist(f);
    }
    public Login getUserVerified() {
        return userVerified;
    }

    public ArrayList<Registrering> getHistoryData(int idnr, Date startdato, Date slutdato)
    {
        String searchID = "Der er søgt på IDnr : " + idnr;
        String searchStart = " Der er søgt på startdato : " +startdato;
        String searchEnd = " Der er søgt på slutdato : " +slutdato;


        if (idnr == 0)
        {

            db.logDatabaseSoegning(userVerified.getIdNR(), searchID+searchStart+searchEnd);
            return db.getregistrationWithTime(startdato,slutdato);
        }

        if(startdato == null || slutdato == null)
        {
            db.logDatabaseSoegning(userVerified.getIdNR(), searchID+searchStart+searchEnd);
            return  db.getregistrationWithID(idnr); 
        }
        db.logDatabaseSoegning(userVerified.getIdNR(), searchID+searchStart+searchEnd);
        return db.getRegistrationIDTime(idnr,startdato,slutdato);
    }


}


