package com.example.intec.Entititer;

public class Person {
    private int idNR;
    private String fornavn;
    private String efternavn;
    private Firma firma;

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

    @Override
    public String toString() {
        return "Person{" +
                "idNR=" + idNR +
                ", fornavn='" + fornavn + '\'' +
                ", efternavn='" + efternavn + '\'' +
                ", firma=" + firma +
                '}';
    }
}
