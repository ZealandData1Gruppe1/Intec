package com.example.intec.Entititer;

import java.time.LocalDate;
import java.util.Date;

public class Registrering {
    int id;
    Person registreringPerson;
    LocalDate registreringTidspunkt;
    String lokation;

    public Registrering()
    {

    }
    public Registrering(Person registreringPerson, LocalDate registreringTidspunkt, String lokation)
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

    public LocalDate getRegistreringTidspunkt() {
        return registreringTidspunkt;
    }

    public void setRegistreringTidspunkt(LocalDate registreringTidspunkt) {
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
