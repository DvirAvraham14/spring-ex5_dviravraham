package hac.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class Main {

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

