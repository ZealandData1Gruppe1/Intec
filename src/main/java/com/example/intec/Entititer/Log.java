package com.example.intec.Entititer;

import java.util.Date;

public class Log {
    private String soening;
    private Date dato;
    private Login bruger;
    private int id;

   public Log (){

    }

    public Log(String soening, Date dato, Login bruger, int id) {
        this.soening = soening;
        this.dato = dato;
        this.bruger = bruger;
        this.id = id;
    }

    public String getSoening() {
        return soening;
    }

    public void setSoening(String soening) {
        this.soening = soening;
    }

    public Date getDato() {
        return dato;
    }

    public void setDato(Date dato) {
        this.dato = dato;
    }

    public Login getBruger() {
        return bruger;
    }

    public void setBruger(Login bruger) {
        this.bruger = bruger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
