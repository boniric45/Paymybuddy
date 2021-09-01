package fr.boniric.paymybuddy.web.controller;

import fr.boniric.paymybuddy.web.model.Contact;
import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.service.ContactService;
import fr.boniric.paymybuddy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;


    @GetMapping("/addconnection")
    public String addconnection(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userAuthenticate = auth.getName();
        model.addAttribute("userEmail", userAuthenticate);
        User usernew = new User();
        model.addAttribute("newcontact", usernew);
        return "/addconnection";
    }

    @PostMapping("/addconnection")
    public ModelAndView postMessage(User newUser, Model model) {

        //User Authenticate
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userAuthenticate = auth.getName();
        model.addAttribute("userEmail", userAuthenticate);

        System.out.println("UserAuthenticate > " + userAuthenticate + " New User > " + newUser.getEmail());

        //TODO règle vérifier mail
        //TODO règle vérifier balance

        User userContact = userService.getUserByEmail(newUser.getEmail());

        //récupération id if usercontact is présent
        if (userContact==null){
            return new ModelAndView("redirect:/transfer");
        } else {
            User userAuth = userService.getUserByEmail(userAuthenticate);
            int idUserAuthenticate = userAuth.getId();
            int idNewContact = userContact.getId();
            System.out.println("ID AUTH > "+idUserAuthenticate+" ID New > "+idNewContact);

            //TODO vérifier si le contact est déja présent
            contactService.saveId(idUserAuthenticate,idNewContact);

        }





        return new ModelAndView("redirect:/addconnection");
    }
}
