package com.example.intec.BuisnessLogic;

import com.example.intec.DBController.DataController;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Usecase {

    String location = "";
    DataController db;

    public Usecase() {
        this.location = "Haslev";
        this.db = new DataController(location);
    }

    public void registrerPerson(Person p, String othercompany)
    {
        Person person = new Person();
        if(p.getFirma().getFirmanavn().equalsIgnoreCase("other"))
        {
            if(findesOtherCompany(othercompany) == false)
            {
                db.opretOtherFirma(othercompany);

            }
            if (findesPerson(p.getIdNR()) == false)
            {
                Firma other = db.hentTransportFirma("OTHER");
                p.setFirma(other);
                db.opretPerson(p);
                person=db.hentPerson(p.getIdNR());
            }
            person.setFirma(db.hentOtherFirma(othercompany));
            db.insertPerComp(person);
        }
        else {
            Firma firma = db.hentTransportFirma(p.getFirma().getFirmanavn());
            p.setFirma(firma);
            if (findesPerson(p.getIdNR()) == false)
            {
                db.opretPerson(p);
                person = db.hentPerson(p.getIdNR());
                person.setFirma(firma);
            }
            else {
                person = p;
                person.setFirma(firma);
            }
        }

        Registrering r = new Registrering(person, LocalDate.now(), location);
        db.insertRegistration(r);

    }

    public void tilfoejPerscomp(Person p, String firmanavn){
        Firma f1 = db.hentOtherFirma(firmanavn);
        p.setFirma(f1);
        db.insertPerComp(p);

    }

    public Firma hentTransportFirma(String firmanavn) {
        return db.hentTransportFirma(firmanavn);
    }

    public ArrayList<Firma> hentAlleTransportFirma()
    {
      ArrayList<Firma> transportFirmaListen=db.hentTransportFirmaListe();
      for(int i =0; i<transportFirmaListen.size();i++)
      {
          if(transportFirmaListen.get(i).getFirmanavn().equalsIgnoreCase("other"))
          {
              Collections.swap(transportFirmaListen,i,transportFirmaListen.size()-1);
          }
      }
      return transportFirmaListen;
    }

    public boolean findesPerson(int idnr)
    {;
        Person p1 = db.hentPerson(idnr);
        if(p1.getIdNR() > 0)
        {
            return true;
        }
        else return false;
    }
    public boolean findesTransportCompany(String firmanavn)
    {
        if (db.hentTransportFirma(firmanavn).getID() >0)
        {
            return true;
        }
        else return false;
    }
    public boolean findesOtherCompany(String firmanavn)
    {
        if (db.hentOtherFirma(firmanavn).getID() > 0)
        {
            return true;
        }
        else return false;
    }


}
