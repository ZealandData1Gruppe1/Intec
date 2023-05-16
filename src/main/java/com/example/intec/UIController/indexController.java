package com.example.intec.UIController;

import com.example.intec.BuisnessLogic.Usecase;
import com.example.intec.Entititer.Admin;
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
        String otherFirmanavn = " ";
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


    @GetMapping("/adminLogin")
    public String visAdminLogin(Model model) {
        Admin admin = new Admin();
        model.addAttribute("admin",admin);
        return "adminLogin";
    }


    @PostMapping("/adminLogin")
    public String loginPost(@ModelAttribute("admin") Admin admin) {
        return "index";
    }

}
