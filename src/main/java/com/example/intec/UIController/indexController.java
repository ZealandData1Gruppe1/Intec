package com.example.intec.UIController;

import com.example.intec.BuisnessLogic.Usecase;
import com.example.intec.Entititer.Login;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class indexController {

    Usecase uc = new Usecase();

    ArrayList<Firma> firmaListen;

    @GetMapping("/registrer")
    public String registrer(Model model) {
        Person person = new Person();
        firmaListen = uc.hentAlleTransportFirma();
        Firma tom = new Firma();
        String otherFirmanavn = "";
        model.addAttribute("firmaliste", firmaListen);
        model.addAttribute("person", person);
        model.addAttribute("otherFirmanavn", otherFirmanavn);
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
            return "udtræk";
        }
        else {
            String forkertLogin = "Forkert login-oplysninger";
            model.addAttribute("loginError", forkertLogin);
            return "login";
        }
    }


    @GetMapping("/admin")
    public String admin(Model model) {


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
        Firma firma = new Firma();
        model.addAttribute("firma", firma);
        return "opretFirma";
    }

    @PostMapping("/opretFirma")
    public String opretFirmaPost(@ModelAttribute("firma") Firma firma, Model model){
        return "opretFirma";
    }
}
