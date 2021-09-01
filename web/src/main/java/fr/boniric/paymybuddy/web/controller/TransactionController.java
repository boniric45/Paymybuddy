package fr.boniric.paymybuddy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    @GetMapping("/pay")
    public String transaction(){
        return "/pay";
    }



}
