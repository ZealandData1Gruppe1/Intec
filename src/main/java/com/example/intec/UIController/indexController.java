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
    public String registrer(Model model)
    {
        Person person = new Person();
        ArrayList<Firma> firmaListe = uc.hentAlleTransportFirma();
        String otherFirmanavn = " ";
        model.addAttribute("firmaliste", firmaListe);
        model.addAttribute("person", person);
        model.addAttribute("otherFirmanavn",otherFirmanavn);

        return "registrer";

    }
    @PostMapping("/registrer")
    public String registerPost(@ModelAttribute("person") Person person, @ModelAttribute("otherFirmanavn") String otherFirmanavn) {

        uc.registrerPerson(person,otherFirmanavn);
        return "redirect:/";
    }


    @GetMapping("/")
    public String visForside() {
        uc.setLocation();
        return "index";
    }
}
