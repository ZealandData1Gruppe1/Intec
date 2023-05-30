package com.example.intec.Usecase;

import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Login;
import com.example.intec.Entititer.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UsecaseTest {
    Usecase uc = new Usecase();

    @Test
    void TjekkerAtFunktionFindesPersonKanIdentifisereOmEnPersonFindesIDatabsenEllerEj() {
        Boolean result = uc.findesPerson(2323);
        assertEquals(true, result);
    }

    @Test
    void tjekkerAtFunktionFindesPersonIkkeReturnereTrueNårPersonenIkkeFindes() {
        Boolean result = uc.findesPerson(0);
        assertEquals(false, result);
    }

    @Test
    void TjekkerAtFunktionFindesCompanyKanIdentifisereOmEtTransportCompanyFindesIDatabsenEllerEj() {
        Boolean result = uc.findesCompany("POSTNORD");
        System.out.println();
        assertEquals(true, result);
    }

    @Test
    void tjekkerAtFunktionFindesTranportCompanyIkkeReturnereTrueNårTransporCompanyIkkeFindes() {
        Boolean result = uc.findesCompany("yyyyyyyyyyyyyyyyyyyyyyyu");
        assertEquals(false, result);
    }

    @Test
    void testerAtViIkkeKanHenteEtLoginMedEnForkertRolle() {
        Login l = new Login();
        l.setBrugernavn("Test");
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            uc.adminLogin(l);

        });
    }

    @Test
    void testerNullPointExeptionNårBilledetIkkeEksistere()  {
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () ->{
            byte[] result = uc.downloadImage(0);
            byte test = result[5];
        });
    }

    @Test
    void testOmEnsNavnKanBeståAfPunktum() {
        Boolean result = uc.checkNavnForIkkeBogstaver("MR.BEAN");
        assertEquals(false,result);
    }

    @Test
    void testOmEnsNavnKanBeståAfGræskeBogstaver() {
        Boolean result = uc.checkNavnForIkkeBogstaver("βerta");
        assertEquals(true,result);
    }

    @Test
    void testOmEnsNavnKanBeståAfBindestreg() {
        Boolean result = uc.checkNavnForIkkeBogstaver("Jens-Peter");
        assertEquals(false,result);
    }

    @Test
    void testOmEnsNavnKanBeståAfKinesiskeBogstaver() {
        Boolean result = uc.checkNavnForIkkeBogstaver("的emil");
        assertEquals(true,result);
    }

    @Test
    void testOmNavnInputIkkeKanManipulereDatabasen() {
        Boolean result = uc.checkNavnForIkkeBogstaver("DROP TABLE log");
        assertEquals(false,result);
    }


}
