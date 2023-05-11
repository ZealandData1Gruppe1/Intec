package com.example.intec.BuisnessLogic;

import com.example.intec.DBController.DataController;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UsecaseTest {
 Usecase uc = new Usecase();
    @Test
    void tjekAtViKanHenteTransportFirmaFraDatabasen() {
        Firma result = uc.hentTransportFirma("POSTNORD");
        assertEquals("POSTNORD",result.getFirmanavn());
    }
    @Test
    void tjekAtDenIkkeReturnereNogetNårDenIkkeFindesIDatabasen(){
        Firma result = uc.hentTransportFirma("Test");
        assertEquals(null,result.getFirmanavn());
    }

    @Test
    void tjekAtSidsteFirmaIArraylistenErOTHER() {
        ArrayList<Firma> result = uc.hentTransportFirmaer();
        assertEquals(result.get(result.size()-1).getFirmanavn(),"OTHER");
    }
    @Test
    void tjekAtSidsteFirmaIArraylistenErIkkeOTHER(){
        ArrayList<Firma> result = uc.hentTransportFirmaer();
        assertNotEquals(result.get(result.size()-1).getFirmanavn(),"POSTNORD");
    }

    @Test
    void TjekkerAtFunktionFindesPersonKanIdentifisereOmEnPersonFindesIDatabsenEllerEj() {
        Boolean result = uc.findesPerson(2323);
        assertEquals(true,result);
    }
    @Test
    void tjekkerAtFunktionFindesPersonIkkeReturnereTrueNårPersonenIkkeFindes(){
        Boolean result = uc.findesPerson(0);
        assertEquals(false,result);
    }


    @Test
    void TjekkerAtFunktionFindesTransportCompanyKanIdentifisereOmEtTransportCompanyFindesIDatabsenEllerEj() {
        Boolean result = uc.findesTransportCompany("POSTNORD");
        assertEquals(true,result);
    }
    @Test
    void tjekkerAtFunktionFindesTranportCompanyIkkeReturnereTrueNårTransporCompanyIkkeFindes(){
        Boolean result = uc.findesTransportCompany("Test");
        assertEquals(false,result);
    }
    @Test
    void TjekkerAtFunktionFindesOtherCompanyKanIdentifisereOmEtOtherCompanyFindesIDatabsenEllerEj() {
        Boolean result = uc.findesOtherCompany("Zealand");
        assertEquals(true,result);
    }
    @Test
    void tjekkerAtFunktionFindesOtherCompanyIkkeReturnereTrueNårOtherCompanyIkkeFindes(){
        Boolean result = uc.findesOtherCompany("Test");
        assertEquals(false,result);
    }

}