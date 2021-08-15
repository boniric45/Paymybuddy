package fr.boniric.paymybuddy.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class LoginController {

    @RequestMapping("/*")
    @RolesAllowed("USER")
    public String getUser(){
        return "Welcome User";
    }
    @RequestMapping("/admin")
    @RolesAllowed("ADMIN")
    public String getAdmin(){
        return "Welcome Admin";
    }

}
