package com.example.intec.UIController;

import com.example.intec.BuisnessLogic.Usecase;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

@Controller
public class indexController {

    Usecase uc = new Usecase();
    @GetMapping("/registrerChauffør")
    public String registrerChauf(Model model)
    {
        Person person = new Person();
        ArrayList<Firma> firmaListe = uc.hentTransportFirmaer();
        model.addAttribute("firmaliste", firmaListe);;
        model.addAttribute("person", person);
        return "registrerChauffør";
    }
    @PostMapping("/registrerChauffør")
    public String registerChauffeur(@ModelAttribute("person") Person person) {
        if (person.getFirma().getFirmanavn().equalsIgnoreCase("Other"))
        {
            Firma f2 = uc.hentFirma(person.getFirma().getFirmanavn());
            person.setFirma(f2);
        }
        else
        {
            Firma f1 = uc.hentFirma(person.getFirma().getFirmanavn());
            person.setFirma(f1);
        }
        uc.RegistrerPerson(person);
        return "redirect:/index";
    }
}
