package com.example.intec.BuisnessLogic;

import com.example.intec.DBController.DataController;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimeZone;

public class Usecase {

    String location = "";
    DataController db;

    public Usecase() {
        this.db = new DataController(setLocation());
    }

    public void registrerPerson(Person p, String othercompany) {
        Person person = new Person();
        if (p.getFirma().getFirmanavn().equalsIgnoreCase("other")) {
            if (findesOtherCompany(othercompany) == false) {
                db.opretOtherFirma(othercompany);

            }
            if (findesPerson(p.getIdNR()) == false) {
                Firma other = db.hentTransportFirma("OTHER");
                p.setFirma(other);
                db.opretPerson(p);
                person = db.hentPerson(p.getIdNR());
            }
            person.setFirma(db.hentOtherFirma(othercompany));
            db.insertPerComp(person);
        } else {
            Firma firma = db.hentTransportFirma(p.getFirma().getFirmanavn());
            p.setFirma(firma);
            if (findesPerson(p.getIdNR()) == false) {
                db.opretPerson(p);
                person = db.hentPerson(p.getIdNR());
                person.setFirma(firma);
            } else {
                person = p;
                person.setFirma(firma);
            }
        }

        Date nu = new Date();
        Registrering r = new Registrering(person, nu, location);
        db.insertRegistration(r);

    }

    public void tilfoejPerscomp(Person p, String firmanavn) {
        Firma f1 = db.hentOtherFirma(firmanavn);
        p.setFirma(f1);
        db.insertPerComp(p);

    }

    public Firma hentTransportFirma(String firmanavn) {
        return db.hentTransportFirma(firmanavn);
    }

    public ArrayList<Firma> hentAlleTransportFirma() {
        ArrayList<Firma> transportFirmaListen = db.hentTransportFirmaListe();
        for (int i = 0; i < transportFirmaListen.size(); i++) {
            if (transportFirmaListen.get(i).getFirmanavn().equalsIgnoreCase("other")) {
                Collections.swap(transportFirmaListen, i, transportFirmaListen.size() - 1);
            }
        }
        return transportFirmaListen;
    }

    public boolean findesPerson(int idnr) {
        ;
        Person p1 = db.hentPerson(idnr);
        if (p1.getIdNR() > 0) {
            return true;
        } else return false;
    }

    public boolean findesTransportCompany(String firmanavn) {
        if (db.hentTransportFirma(firmanavn).getID() > 0) {
            return true;
        } else return false;
    }

    public boolean findesOtherCompany(String firmanavn) {
        if (db.hentOtherFirma(firmanavn).getID() > 0) {
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
}


