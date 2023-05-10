package com.example.intec.Entititer;

import java.util.Date;

public class Registrering {
    int id;
    Person registreringPerson;
    Date registreringTidspunkt;
    String lokation;

    public Registrering()
    {

    }
    public Registrering(Person registreringPerson, Date registreringTidspunkt, String lokation)
    {
        this.registreringPerson = registreringPerson;
        this.registreringTidspunkt = registreringTidspunkt;
        this.lokation = lokation;
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
