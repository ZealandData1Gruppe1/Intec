package com.example.intec.UIController;

import com.example.intec.BuisnessLogic.Usecase;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.TransportFirma;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.intec.Entititer.TransportFirma.GLS;

@Controller
public class indexController {


    @GetMapping("/index")
    public String visForside()
    {
        return "index";
    }

    @PostMapping("/registrerPerson")
    public String registrerPersonGem(Model model)
    {
        Person p = new Person();
        model.addAttribute("Person", p);
        Usecase uc = new Usecase();
        uc.RegistrerPerson(p);
        return "index";
    }

    @PostMapping("/registrerChauffør")
    public String registrerChaufførGem(Model model)
    {
        Usecase uc = new Usecase();
        Person p = new Person();
        String nytFirma ="";
        TransportFirma t = GLS;
        model.addAttribute("Person", p);
        model.addAttribute("Transportfirma", t);
        model.addAttribute("Nyt Firma", nytFirma);
        if(t.equals("Other"))
        {
            Firma f2 = uc.hentFirma(nytFirma);
            p.setFirma(f2);
        }
        else
        {
            Firma f1 = uc.hentFirma(t.getFirmaString());
            p.setFirma(f1);
        }
        uc.RegistrerPerson(p);
        return "index";
    }
}
