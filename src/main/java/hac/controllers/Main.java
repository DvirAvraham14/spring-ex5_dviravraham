package hac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * this is a test controller, delete/replace it when you start working on your project
 */
@Controller
public class Main {

//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("greeting", "Hello World");
//        return "index";
//    }

    /**
     * Home page.
     */
    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("message",
                    "You are logged in as " + principal.getName());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
