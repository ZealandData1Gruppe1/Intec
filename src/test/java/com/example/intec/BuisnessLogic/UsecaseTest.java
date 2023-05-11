package com.example.intec.BuisnessLogic;

import com.example.intec.DBController.DataController;
import com.example.intec.Entititer.Firma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsecaseTest {
 Usecase uc = new Usecase();
    @Test
    void tjekOmViKanHenteTransportFirmaFraDatabasen() {
        Firma result = uc.hentTransportFirma("POSTNORD");
        assertEquals("POSTNORD",result.getFirmanavn());
    }
    @Test
    void tjekOmDenIkkeReturnereNogetNårDenIkkeFindesIDatabasen(){
        Firma result = uc.hentTransportFirma("Test");
        assertEquals(null,result.getFirmanavn());
    }

}