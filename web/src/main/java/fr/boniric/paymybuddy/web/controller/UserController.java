package fr.boniric.paymybuddy.web.controller;

import fr.boniric.paymybuddy.web.config.SpringSecurityConfig;
import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class UserController {

    public String EMAILUSER;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "/login";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    //Create
    @GetMapping("/inscription")
    public String inscriptionPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/inscription";
    }

    @PostMapping(value = "/user")
    public ModelAndView saveUser(User user) {
        //Encode Password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        System.out.println(user);
        //save user
        userService.saveUser(user);

        //TODO existe déja a faire
        return new ModelAndView("redirect:/recapRegister");
    }

    @GetMapping("/transfer")
    public String success(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EMAILUSER = auth.getName();
        model.addAttribute("userEmail", EMAILUSER);
        return "/transfer";
    }


//    boolean flag=false;
//
//    @RequestMapping(value="/transfer", method=RequestMethod.POST, params="action=account")
//    public void account() {
//        flag=true;
//        System.out.println("account");
//    }
//
//    @RequestMapping(value="/transfer", method=RequestMethod.POST, params="action=rib")
//    public void rib() {
//        flag=false;
//        System.out.println("rib");
//    }

    @RequestMapping(value = "/transfer")
    public String showCheckbox(Model model) {
        boolean myBooleanVariable = false;
        model.addAttribute("myBooleanVariable", myBooleanVariable);
        return "sample-checkbox";
    }

    @GetMapping("/bad")
    public String badPage() {
        return "/bad";
    }

    @GetMapping("/recapRegister")
    public String recapRegister(Model model) {
        //TODO a finir

        // Data Saved
        model.addAttribute("varInfo", "Your data has been saved !");
        return "/recapRegister";
    }




//    //Récupération des data
//    @PostMapping("/login")
//    public String postMessage(@ModelAttribute User newUser){
//        User user = userService.getUserByEmail(newUser.getEmail());
//        String login = user.getEmail();
//        String pass = user.getPassword();
//         System.out.println("Login BDD > "+login+" Pass BDD >  "+pass);
//        return "/payment";
//    }


//    @GetMapping("/admin")
//    public String adminpage(){
//        return "payment";
//    }


//    @GetMapping("/user/{email}")
//    public String showUpdateForm(@PathVariable("email") String email, Model model) {
//        System.out.println(email);
//        User user = userService.getUserByEmail(email);
//        model.addAttribute("user", user);
//        if (user==null){
//            System.out.println("ko");
//            return "/bad";
//        } else {
//            System.out.println("Service > "+user);
//        }
//        return "login";
//    }
//


    // ok
//    @GetMapping("/user/{email}")
//    public String searchUserByMail(@PathVariable("email") String email, Model model) {
//        User user = userService.getUserByEmail(email);
//        model.addAttribute("user", user);
//        return "login.html";
//    }

//    @PostMapping("/saveUser/{email}")
//    public ModelAndView saveuser(@ModelAttribute User user) {
//        System.out.println(user.getFirstname());
//        User current = userService.getUserByEmail(user.getEmail());
//        return new ModelAndView("redirect:/user/"+current);
//    }


    //    //Login form
//    @RequestMapping(value = "/success", method = RequestMethod.POST)
//    public String login(Model model){
//        Iterable<User> listUser = userService.getUserAll();
//        model.addAttribute("users", listUser);
//        System.out.println(listUser);
//        return "payment.html";
//    }
//
//


//
//    //Login formt with error
//    @RequestMapping("/login-error.html")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "login.html";
//    }


//        @GetMapping("/updateEmployee/{id}")
//    public String updateEmployee(@PathVariable("id") final int id, Model model) {
//        Employee e = service.getEmployee(id);
//        model.addAttribute("employee", e);
//        return "formUpdateEmployee";
//    }

//    @GetMapping("/user")
//    public ModelAndView readEmploye(@ModelAttribute User user){
//        System.out.println(user.getEmail());
//        userService.getUserProxy();
//        return new ModelAndView("redirect:/");
//    }


//    @GetMapping("/createEmployee")
//    public String createEmployee(Model model) {
//        Employee e = new Employee();
//        model.addAttribute("employee", e);
//        return "formNewEmployee";
//    }
//
//    @GetMapping("/updateEmployee/{id}")
//    public String updateEmployee(@PathVariable("id") final int id, Model model) {
//        Employee e = service.getEmployee(id);
//        model.addAttribute("employee", e);
//        return "formUpdateEmployee";
//    }
//
//    @GetMapping("/deleteEmployee/{id}")
//    public ModelAndView deleteEmployee(@PathVariable("id") final int id) {
//        service.deleteEmployee(id);
//        return new ModelAndView("redirect:/");
//    }
//
//    @PostMapping("/saveEmployee")
//    public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
//        if(employee.getId() != null) {
//            // Employee from update form has the password field not filled,
//            // so we fill it with the current password.
//            Employee current = service.getEmployee(employee.getId());
//            employee.setPassword(current.getPassword());
//        }
//        service.saveEmployee(employee);
//        return new ModelAndView("redirect:/");
//    }


}
