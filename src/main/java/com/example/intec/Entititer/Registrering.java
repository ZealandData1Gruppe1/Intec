package com.example.intec.Entititer;

import java.util.Date;
import java.time.LocalDate;

public class Registrering {
    private int id;
    private Person registreringPerson;
    private Date tjekinTidspunkt;
    private Date tjekudTidspunkt;
    private String lokation;

    public Registrering()
    {

    }
    public Registrering(Person registreringPerson, Date tjekinTidspunkt, String lokation)
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

    public Date getTjekinTidspunkt() {
        return tjekinTidspunkt;
    }

    public void setTjekinTidspunkt(Date tjekinTidspunkt) {
        this.tjekinTidspunkt = tjekinTidspunkt;
    }

    public Date getTjekudTidspunkt() {
        return tjekudTidspunkt;
    }

    public void setTjekudTidspunkt(Date tjekudTidspunkt) {
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
