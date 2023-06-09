package com.example.intec.UIController;

import com.example.intec.Usecase.Usecase;
import com.example.intec.Entititer.Login;
import com.example.intec.Entititer.Firma;
import com.example.intec.Entititer.Person;
import com.example.intec.Entititer.Registrering;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class WebController {

    private Usecase uc = new Usecase();

    private ArrayList<Firma> firmaListen;
    private ArrayList<Registrering> historikListen;

    boolean shouldDisplayEnglish = uc.getDisplayEnglish();

    @GetMapping("/registrer")
    public String registrer(Model model) {
        Person person = new Person();
        firmaListen = uc.hentAlleTransportFirma();
        Firma tom = new Firma();
        String otherFirmanavn = "";
        String placeholder = "Udfyld hvis firma ikke er på listen / Fill out if company is not on the list ";
        model.addAttribute("placeholder", placeholder);
        model.addAttribute("otherFirmanavn", otherFirmanavn);
        model.addAttribute("firmaliste", firmaListen);
        model.addAttribute("person", person);
        model.addAttribute("valgtfirma", tom);
        model.addAttribute("language",shouldDisplayEnglish);
        return "registrer";
    }
    @GetMapping("/")
    public String visForside(Model model) {
        model.addAttribute("language",shouldDisplayEnglish);
        return "index";
    }
    @PostMapping("/")
    public String visForsidePost(Model Model, @ModelAttribute("mycheckbox") String mycheckbox, HttpServletRequest request)
    {
        String lang = (request.getParameter("mycheckbox"));
        if(lang != null && !lang.trim().isEmpty())
        {
            shouldDisplayEnglish = true;
        }

        return "index";
    }

    @PostMapping("/registrer")
    public String registrerPost(@ModelAttribute("person") Person person, @ModelAttribute("otherFirmanavn") String otherFirmanavn, Model model, @ModelAttribute("valgtfirma") Firma firma, @ModelAttribute("image") MultipartFile image) {
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
                        String success = "du er oprettet";
                        model.addAttribute("success", success);
                        uc.registrerPerson(person, valgtFirma, otherFirmanavn, image);
                        //return "redirect:/";
                        return "registrer";
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
        model.addAttribute("language",shouldDisplayEnglish);
        return "login";
    }
    
    @PostMapping("/login")
    public String loginPost(@ModelAttribute("login") Login login, Model model) {
        model.addAttribute("language",shouldDisplayEnglish);
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
    public String admin(Model model)
    {
        model.addAttribute("language",shouldDisplayEnglish);
        if (uc.getUserVerified().getBrugernavn().trim().length() == 0 || uc.getUserVerified().getBrugernavn() == null)
        {
            model.addAttribute("language",shouldDisplayEnglish);
            return "redirect:/login";

        }
        return "admin";
    }

    @GetMapping("/opretLogin")
    public String opretLogin(Model model) {
        if (uc.getUserVerified().getBrugernavn().trim().length() == 0 || uc.getUserVerified().getBrugernavn() == null)
        {
            model.addAttribute("language",shouldDisplayEnglish);
            return "redirect:/login";

        }
        Login login = new Login();
        model.addAttribute("login", login);
        model.addAttribute("language",shouldDisplayEnglish);
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
        if (uc.getUserVerified().getBrugernavn().trim().length() == 0 || uc.getUserVerified().getBrugernavn() == null)
        {
            model.addAttribute("language",shouldDisplayEnglish);
            return "redirect:/login";

        }
        String firma = "";
        model.addAttribute("firma", firma);
        model.addAttribute("language",shouldDisplayEnglish);

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
        if (uc.getUserVerified().getBrugernavn().trim().length() == 0 || uc.getUserVerified().getBrugernavn() == null)
        {
            model.addAttribute("language",shouldDisplayEnglish);
            return "redirect:/login";

        }
        uc.sletGamleOplysninger();

        String dataSlettet = "";
        model.addAttribute("dataSlettet", dataSlettet);

        return "admin";
    }

    @GetMapping("/historik")
    public String udtraekData (Model model)
    {
        model.addAttribute("language",shouldDisplayEnglish);
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
    public String visHistorik(@ModelAttribute("idnr") String idnr,@ModelAttribute("startdato") String startdato,@ModelAttribute("slutdato") String slutdato, Model model)
    {

        int id;
        String error = "";

        if (idnr.equals("")){
            idnr = "0";
        }
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date slut = null;
        if(startdato.trim().isEmpty()== false && slutdato.trim().isEmpty()== false) {
            try {
                start = formater.parse(startdato);
                slut = formater.parse(slutdato);
            } catch (ParseException e) {
                model.addAttribute("fejl", error);
                return "/historik";
            }
            id = Integer.parseInt(idnr);
            historikListen = uc.getHistoryData(id,start,slut);
            return "redirect:/historik";
        }
        if (startdato.trim().isEmpty()== true && slutdato.trim().isEmpty()== true){
            if (idnr.equals("0")){
                model.addAttribute("fejl", error);
                return "/historik";
            }
            else{
                id = Integer.parseInt(idnr);
                historikListen = uc.getHistoryData(id,start,slut);
                return "redirect:/historik";
            }
        }

        model.addAttribute("fejl", error);
        return "/historik";
    }

    @GetMapping("/fjernFirma")
    public String removeCompanyGet(Model model)
    {
        if (uc.getUserVerified().getBrugernavn().trim().length() == 0 || uc.getUserVerified().getBrugernavn() == null)
        {
            model.addAttribute("language",shouldDisplayEnglish);
            return "redirect:/login";

        }
        model.addAttribute("language",shouldDisplayEnglish);
        firmaListen = uc.hentAlleTransportFirma();
        model.addAttribute("firmaliste", firmaListen);
        return "fjernFirma";
    }

    @PostMapping("/fjernFirma")
    public String removeCompanyPost(Model model, @ModelAttribute("valgtfirma") Firma firma)
    {
        Firma valgtFirma = new Firma();
        for (int i = 0; i < firmaListen.size(); i++)
        {
            if (firmaListen.get(i).getFirmanavn().equalsIgnoreCase(firma.getFirmanavn()))
            {
                valgtFirma = firmaListen.get(i);
            }
        }
        uc.removeCompanyFromList(valgtFirma.getFirmanavn());

        return"admin";
    }
    @GetMapping("/GDPRslet")
    public String removePersonGet(Model model)
    {
        if (uc.getUserVerified().getBrugernavn().trim().length() == 0 || uc.getUserVerified().getBrugernavn() == null)
        {
            model.addAttribute("language",shouldDisplayEnglish);
            return "redirect:/login";

        }
        firmaListen = uc.hentAlleTransportFirma();
        String idnr = "";
        model.addAttribute("idnr", idnr);
        model.addAttribute("firmaliste", firmaListen);
        model.addAttribute("language",shouldDisplayEnglish);
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
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadImage(@ModelAttribute("imageID") String imageID) throws IOException, SQLException {
        byte[] imageBytes = uc.downloadImage(Integer.parseInt(imageID));

        String filename = "image" + imageID + ".jpg";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);
        headers.setContentDispositionFormData("attachment", filename);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageBytes);
    }




}
