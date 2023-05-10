package com.example.intec.Entititer;

import java.util.Date;

public class Registrering {
    Person registreringPerson;
    Date registreringTidspunkt;
    String lokation;
    int id;


    public Registrering()
    {

    }
    public Registrering(Person registreringPerson, Date registreringTidspunkt, String lokation, int id)
    {
        this.registreringPerson = registreringPerson;
        this.registreringTidspunkt = registreringTidspunkt;
        this.lokation = lokation;
        this.id = id;
    }

    public Person getRegistreringPerson() {
        return registreringPerson;
    }

    public void setRegistreringPerson(Person registreringPerson) {
        this.registreringPerson = registreringPerson;
    }

    public Date getRegistreringTidspunkt() {
        return registreringTidspunkt;
    }

    public void setRegistreringTidspunkt(Date registreringTidspunkt) {
        this.registreringTidspunkt = registreringTidspunkt;
    }

    public String getLokation() {
        return lokation;
    }

    public void setLokation(String lokation) {
        this.lokation = lokation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
