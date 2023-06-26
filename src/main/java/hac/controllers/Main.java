package hac.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/*
    * This class represents a controller for the main page.
    * It is used to display the main page and the login page.
    * It also handles the 403 error page.
 */
@Controller
public class Main {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("message",
                    "You are logged in as " + principal.getName());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("status", "Error");
        String errorMessage = (ex.getMessage() != null ? ex.getMessage() : "Unknown error");
        System.out.println(errorMessage);
        model.addAttribute("message", errorMessage);
        return "error";
    }

}

