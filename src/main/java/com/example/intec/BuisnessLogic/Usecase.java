package com.example.intec.BuisnessLogic;

import com.example.intec.DBController.DataController;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;

import java.util.Date;

public class Usecase {

    String location = "";
    DataController db;

    public Usecase() {
        this.location = "Haslev";
        this.db = new DataController(location);
    }

    public void RegistrerPerson(Person p)
    {
        Registrering r = new Registrering(p, new Date(), location);

        db.insertRegistration(r);
    }
}
