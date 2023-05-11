package com.example.intec.BuisnessLogic;

import com.example.intec.DBController.DataController;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Usecase {

    String location = "";
    DataController db;

    public Usecase() {
        this.location = "Haslev";
        this.db = new DataController(location);
    }

    public void RegistrerPerson(Person p)
    {
        Person person = db.hentPerson(p.getIdNR());
        if(person.getIdNR()<=0)
        {
            db.opretPerson(p);
            person = db.hentPerson(p.getIdNR());
            person.setFirma(p.getFirma());

        }
        Registrering r = new Registrering(person, LocalDate.now(), location);
        if(p.getFirma().getID()==10) //10 = "Other firma" er valgt i HTML form
        {
            db.insertPersComp(person);
        }
        db.insertRegistration(r);

    }
    public void tjekFirmaFindesEllerOpret(String firmanavn)
    {
        Firma F1  = db.hentTransportFirma(firmanavn);
       if (F1.getID() >0) {
           return;
       }
       else if(F1.getID()<=0)
       {
           Firma F2 =db.hentOtherFirma(firmanavn);
           if(F2.getID() >0)
           {
               return;
           }
           else
           {
               db.opretOtherFirma(firmanavn);
           }
       }
    }


    public void tilfoejPerscomp(Person p, String firmanavn){
        Firma f1 = db.hentOtherFirma(firmanavn);
        p.setFirma(f1);
        db.insertPersComp(p);

    }


    public Firma hentTransportFirma(String firmanavn) {
        return db.hentTransportFirma(firmanavn);
    }



    public ArrayList<Firma> hentTransportFirmaer()
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


    public Person tjekOmPersonFindesEllerOpret(Person p) {
        Person p1 = db.hentPerson(p.getIdNR());
        if (p1.getIdNR() >0)
        {
            return p1;
        }
        else {
            db.opretPerson(p);
            Person p2 = db.hentPerson(p.getIdNR());
            return p2;
        }
    }


}
