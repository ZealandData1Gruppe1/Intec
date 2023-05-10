package com.example.intec.Entititer;

public class Person {
    String fornavn;
    String efternavn;
    String koerekortNR;
    String firma;


    public Person(){

    }
    public Person(String fornavn, String efternavn, String koerekortNR, String firma) {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.koerekortNR = koerekortNR;
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

    public String getKoerekortNR() {
        return koerekortNR;
    }

    public void setKoerekortNR(String koerekortNR) {
        this.koerekortNR = koerekortNR;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
}
