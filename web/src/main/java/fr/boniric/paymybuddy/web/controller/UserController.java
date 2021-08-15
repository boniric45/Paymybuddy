package fr.boniric.paymybuddy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // renvoi la page web home
    @GetMapping("/")
    public String home() {

        return "home";
    }



}
