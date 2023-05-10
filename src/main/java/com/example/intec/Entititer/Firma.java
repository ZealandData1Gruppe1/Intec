package com.example.intec.Entititer;

import java.util.ArrayList;

public class Firma {
    int ID;
    String Firmanavn;

    ArrayList<Person> ansatte = new ArrayList<>();

    public Firma() {
    }

    public Firma(String Firmanavn)
    {
        this.Firmanavn = Firmanavn;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirmanavn() {
        return Firmanavn;
    }

    public void setFirmanavn(String firmanavn) {
        Firmanavn = firmanavn;
    }

    public ArrayList<Person> getAnsatte() {
        return ansatte;
    }

    public void setAnsatte(ArrayList<Person> ansatte) {
        this.ansatte = ansatte;
    }
}
