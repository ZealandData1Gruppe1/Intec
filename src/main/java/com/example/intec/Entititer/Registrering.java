package com.example.intec.Entititer;

import java.time.LocalDate;
import java.util.Date;

public class Registrering {
    int id;
    Person registreringPerson;
    LocalDate tjekinTidspunkt;
    LocalDate tjekudTidspunkt;
    String lokation;

    public Registrering()
    {

    }
    public Registrering(Person registreringPerson, LocalDate tjekinTidspunkt, String lokation)
    {
        this.registreringPerson = registreringPerson;
        this.tjekinTidspunkt  = tjekinTidspunkt;
        this.lokation = lokation;
    }

    public Person getRegistreringPerson() {
        return registreringPerson;
    }

    public void setRegistreringPerson(Person registreringPerson) {
        this.registreringPerson = registreringPerson;
    }

    public LocalDate getTjekinTidspunkt() {
        return tjekinTidspunkt;
    }

    public void setTjekinTidspunkt(LocalDate tjekinTidspunkt) {
        this.tjekinTidspunkt = tjekinTidspunkt;
    }

    public LocalDate getTjekudTidspunkt() {
        return tjekudTidspunkt;
    }

    public void setTjekudTidspunkt(LocalDate tjekudTidspunkt) {
        this.tjekudTidspunkt = tjekudTidspunkt;
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
