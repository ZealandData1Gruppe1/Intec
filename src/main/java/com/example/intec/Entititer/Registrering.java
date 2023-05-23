package com.example.intec.Entititer;

import java.util.Date;
import java.time.LocalDate;

public class Registrering {
    private int id;
    private Person registreringPerson;
    private Date tjekinTidspunkt;
    private String lokation;
    private Firma firma;

    public Registrering()
    {

    }
    public Registrering(Person registreringPerson,Firma firma, Date tjekinTidspunkt, String lokation)
    {
        this.registreringPerson = registreringPerson;
        this.firma = firma;
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

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    @Override
    public String toString() {
        return "Registrering{" +
                "id=" + id +
                ", registreringPerson=" + registreringPerson +
                ", tjekinTidspunkt=" + tjekinTidspunkt +
                ", lokation='" + lokation + '\'' +
                ", firma=" + firma +
                '}';
    }
}
