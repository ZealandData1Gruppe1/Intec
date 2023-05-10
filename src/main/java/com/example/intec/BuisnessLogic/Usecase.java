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
    public Firma hentFirma(String firmanavn)
    {
        Firma F1  = db.hentTransportFirma(firmanavn);
       if (F1.getID() >0) {
            return F1;
       }
       else if(F1.getID()<=0)
       {
           Firma F2 =db.hentOtherFirma(firmanavn);
           if(F2.getID() >0)
           {
               return F2;
           }
           else
           {
               db.opretOtherFirma(firmanavn);
               Firma F3 = db.hentOtherFirma(firmanavn);
               return F3;
           }
       }
    return new Firma();
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


}
