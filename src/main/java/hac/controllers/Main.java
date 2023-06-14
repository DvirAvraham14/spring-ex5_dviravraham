package hac.controllers;

import hac.beans.Goal;
import hac.beans.repo.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * this is a test controller, delete/replace it when you start working on your project
 */
@Controller
public class Main {

    @Autowired
    private GoalRepository goalRepository;

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


    @PostMapping("/goal")
    public String addGoal(Goal goal) {
        goalRepository.save(goal);
        return "Goal";
    }

    @GetMapping("/goal")
    public Iterable<Goal> getGoals() {
        return goalRepository.findAll();
    }
}

