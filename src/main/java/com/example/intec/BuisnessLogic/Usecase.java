package com.example.intec.BuisnessLogic;

import com.example.intec.DBController.DataController;
import com.example.intec.Entititer.Admin;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimeZone;

import static com.example.intec.DBController.DataController.getInstance;

public class Usecase {

    String location = "";
    DataController db;

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
    public boolean adminLogin (Admin a){
        Admin dataAdmin = db.hentAdmin(a.getBrugernavn());
        if (a.getBrugernavn().equals(dataAdmin.getBrugernavn()) && a.getKode().equals(dataAdmin.getKode())){
            return true;
        }
        else
            return false;
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
}


