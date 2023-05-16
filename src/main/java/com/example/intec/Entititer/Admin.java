package com.example.intec.Entititer;

public class Admin {
    private String brugernavn;
    private String kode;

    public Admin(){

    }

    public Admin(String brugernavn, String kode) {
        this.brugernavn = brugernavn;
        this.kode = kode;
    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public void setBrugernavn(String brugernavn) {
        this.brugernavn = brugernavn;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
}
