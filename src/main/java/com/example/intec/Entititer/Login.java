package com.example.intec.Entititer;

public class Login extends Person{


    private String brugernavn;
    private String kode;

    private Rolle rolle;


    public Login(){

    }

    public Login(int idNR, String fornavn, String efternavn, String brugernavn, String kode, Rolle rolle) {
        super(idNR, fornavn, efternavn);
        this.brugernavn = brugernavn;
        this.kode = kode;
        this.rolle = rolle;
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

    public String getRolle() {
        return rolle.name().toString();
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }
    public Rolle rollefromString(String text) {
        for (Rolle r : rolle.values()) {
            String navn = r.name().toString();
            if (navn.equalsIgnoreCase(text))
            {
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Login{" +
                "brugernavn='" + brugernavn + '\'' +
                ", kode='" + kode + '\'' +
                ", rolle=" + rolle +
                "} " + super.toString();
    }
}
