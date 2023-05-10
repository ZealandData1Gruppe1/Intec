package com.example.intec.Entititer;

public enum TransportFirma {
    POSTNORD,
    GLS,
    DAO,
    DHL,
    DSV,
    BRING,
    UPS;

    public String getFirmaString()
    {
        return this.toString();
    }
}
