package com.example.intec.UIController;

import com.example.intec.BuisnessLogic.Usecase;
import com.example.intec.Entititer.Login;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class indexController {

    Usecase uc = new Usecase();

    ArrayList<Firma> firmaListen;
    ArrayList<Registrering> historikListen;

    @GetMapping("/registrer")
    public String registrer(Model model) {
        Person person = new Person();
        firmaListen = uc.hentAlleTransportFirma();
        Firma tom = new Firma();
        String otherFirmanavn = "";
        model.addAttribute("otherFirmanavn", otherFirmanavn);
        model.addAttribute("firmaliste", firmaListen);
        model.addAttribute("person", person);
        model.addAttribute("valgtfirma", tom);

        return "registrer";

    }
    @GetMapping("/")
    public String visForside(Model model) {
        uc.setLocation();
        Boolean language = uc.shouldDisplayEnglish();
        model.addAttribute("language",language);
        return "index";
    }
    @PostMapping("/registrer")
    public String registerPost(@ModelAttribute("person") Person person, @ModelAttribute("otherFirmanavn") String otherFirmanavn, Model model, @ModelAttribute("valgtfirma") Firma firma) {
        Firma valgtFirma = new Firma();
        for (int i = 0; i < firmaListen.size(); i++)
        {
            if (firmaListen.get(i).getFirmanavn().equalsIgnoreCase(firma.getFirmanavn()))
            {
                valgtFirma = firmaListen.get(i);
            }
        }
        if (uc.checkNavnForIkkeBogstaver(person.getFornavn()) == true)
            {
                if (uc.checkNavnForIkkeBogstaver(person.getEfternavn()) == true)
                {
                    if(uc.checkID(person.getIdNR()) == true)
                    {
                        uc.registrerPerson(person, valgtFirma, otherFirmanavn);
                        return "redirect:/";
                    }
                }
            }
            else
            {
                String forkerteOplysninger = "Forkerte Oplysninger: Indtast venligst dine oplysninger igen";
                model.addAttribute("opretError", forkerteOplysninger);
                return "registrer";
            }
            String forkerteOplysninger = "Forkerte Oplysninger: Indtast venligst dine oplysninger igen";
            model.addAttribute("opretError", forkerteOplysninger);
            return "registrer";
    }


    @GetMapping("/login")
    public String visAdminLogin(Model model) {
        Login login = new Login();
        model.addAttribute("login", login);
        return "login";
    }


    @PostMapping("/login")
    public String loginPost(@ModelAttribute("login") Login login, Model model) {
        if(uc.adminLogin(login).equalsIgnoreCase("Admin")) {
            return "admin";
        }
        if ((uc.adminLogin(login).equalsIgnoreCase("EkstrenMyndighed"))){
            return "historik";
        }
        else {
            String forkertLogin = "Forkert login-oplysninger";
            model.addAttribute("loginError", forkertLogin);
            return "login";
        }
    }


    @GetMapping("/admin")
    public String admin(Model model) {
        if (uc.getUserVerified().getBrugernavn().trim().length() == 0 || uc.getUserVerified().getBrugernavn() == null)
        {
            return "redirect:/login";
        }
        return "admin";
    }

    @GetMapping("/opretLogin")
    public String opretLogin(Model model) {
        Login login = new Login();
        boolean language = uc.shouldDisplayEnglish();
        model.addAttribute("login", login);
        model.addAttribute("language",language);
        return "opretLogin";
    }

    @PostMapping("/opretLogin")
    public String opretLoginPost(@ModelAttribute ("login") Login login, Model model) {

        if(uc.opretLogin(login) == true){
            return "index";
        }

        if (uc.opretLogin(login) == false){
            String forkertLogin = "Forkert login-oplysninger";
            model.addAttribute("loginError", forkertLogin);
            return "opretLogin";
        }
        return "index";
    }

    @GetMapping("/opretFirma")
    public String opretFirma(Model model){
        String firma = "";
        model.addAttribute("firma", firma);

        return "opretFirma";
    }

    @PostMapping("/opretFirma")
    public String opretFirmaPost(@ModelAttribute("firma") String firma, Model model){
        uc.addToCompanyList(firma);
        return "index";
    }

    @GetMapping("/slet")
    public String sletData (Model model)
    {
        uc.sletGamleOplysninger();

        String dataSlettet = "";
        model.addAttribute("dataSlettet", dataSlettet);
        return "admin";
    }

    @GetMapping("/historik")
    public String udtraekData (Model model)
    {
        if (uc.getUserVerified().getBrugernavn().trim().length() == 0 || null == uc.getUserVerified().getBrugernavn())
        {
            return "redirect:/login";
        }
        String placeholder = "yyyy-mm-dd hh:mm:ss";
        model.addAttribute("placeholder", placeholder);

        String idnr = "";
        model.addAttribute("idnr", idnr);

        String startdato = "";
        model.addAttribute("startdato", startdato);

        String slutdato = "";
        model.addAttribute("slutdato", slutdato);
        model.addAttribute("historikListen", historikListen);

        return "historik";
    }

    @PostMapping("/historik")
    public String getDataPost(@ModelAttribute("idnr") int idnr,@ModelAttribute("startdato") String startdato,@ModelAttribute("slutdato") String slutdato)
    {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date slut = null;
        if(startdato.trim().isEmpty()== false) {
            try {
                start = formater.parse(startdato);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            try {
                slut = formater.parse(slutdato);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        historikListen = uc.getHistoryData(idnr,start,slut);
        return "redirect:/historik";
    }

    @GetMapping("/fjernFirma")
    public String removeCompanyGet(Model model)
    {
        firmaListen = uc.hentAlleTransportFirma();
        model.addAttribute("firmaliste", firmaListen);
        return "fjernFirma";
    }

    @PostMapping("/fjernFirma")
    public void removeCompanyPost(Model model, @ModelAttribute("valgtfirma") Firma firma)
    {

        Firma valgtFirma = new Firma();
        for (int i = 0; i < firmaListen.size(); i++)
        {
            if (firmaListen.get(i).getFirmanavn().equalsIgnoreCase(firma.getFirmanavn()))
            {
                valgtFirma = firmaListen.get(i);
            }
        }

    }
    @GetMapping("/GDPRslet")
    public String removePersonGet(Model model)
    {
        firmaListen = uc.hentAlleTransportFirma();
        String idnr = "";
        model.addAttribute("idnr", idnr);
        model.addAttribute("firmaliste", firmaListen);
        return "GDPRslet";
    }

    @PostMapping("/GDPRslet")
    public String removePersonPost(Model model, @ModelAttribute("idnr") String idnr)
    {
        if(uc.sletOplysningerForID(Integer.parseInt(idnr)) != true)
        {
            String GDPRdataslet = "";
            model.addAttribute("GDPRdataslet",GDPRdataslet);
        }
        return "admin";
    }

}
