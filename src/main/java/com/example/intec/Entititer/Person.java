package com.example.intec.Entititer;

public class Person {
    int idNR;
    String fornavn;
    String efternavn;

    Firma firma;


    public Person(){

    }
    public Person(String fornavn, String efternavn, Firma firma) {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.firma = firma;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public int getIdNR() {
        return idNR;
    }

    public void setIdNR(int idNR) {
        this.idNR = idNR;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }
}
