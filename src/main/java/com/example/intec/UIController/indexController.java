package com.example.intec.UIController;

import com.example.intec.BuisnessLogic.Usecase;
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

    @GetMapping("/registrer")
    public String registrer(Model model) {
        Person person = new Person();
        ArrayList<Firma> firmaListe = uc.hentAlleTransportFirma();
        String otherFirmanavn = " ";
        model.addAttribute("firmaliste", firmaListe);
        model.addAttribute("person", person);
        model.addAttribute("otherFirmanavn", otherFirmanavn);

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
    public String registerPost(@ModelAttribute("person") Person person, @ModelAttribute("otherFirmanavn") String otherFirmanavn, Model model) {
        if (uc.checkNavnForIkkeBogstaver(person.getFornavn()) == true) {
            if (uc.checkNavnForIkkeBogstaver(person.getEfternavn()) == true) {
                uc.registrerPerson(person, otherFirmanavn);
                return "redirect:/";
            }
        }
            else{
                String forkerteOplysninger = "Forkerte Oplysninger: Indtast venligst dine oplysninger igen";
                model.addAttribute("opretError", forkerteOplysninger);
                return "registrer";
            }


        String forkerteOplysninger = "Forkerte Oplysninger: Indtast venligst dine oplysninger igen";
        model.addAttribute("opretError", forkerteOplysninger);
        return "registrer";
    }

}
