package com.example.intec.UIController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {


    @GetMapping("/index")
    public String visForside()
    {
        return "index";
    }
}
