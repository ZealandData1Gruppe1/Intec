package com.example.intec.Entititer;

import java.util.Date;

public class Log {
    private String soening;
    private Date dato;
    private Admin bruger;
    private int id;

   public Log (){

    }

    public Log(String soening, Date dato, Admin bruger, int id) {
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

    public Admin getBruger() {
        return bruger;
    }

    public void setBruger(Admin bruger) {
        this.bruger = bruger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
