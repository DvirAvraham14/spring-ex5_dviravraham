package hac.controllers;

import hac.beans.Budget;
import hac.beans.Expense;
import hac.beans.Goal;
import hac.beans.repo.BudgetRepository;
import hac.beans.repo.ExpenseRepository;
import hac.beans.repo.GoalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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


    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private GoalRepository goalRepository;

    // expense section
    @PostMapping("/expenseAdd")
    public String addExpense(@Valid Expense expense, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            //userInfo.setId(id);
            return "redirect:/expense";
        }
        System.out.println(principal.getName());
        expenseRepository.save(expense);
        return "index";
    }

    @GetMapping("/expense")
    public Iterable<Expense> getExpenses(Expense expense) {
        return expenseRepository.findAll();
    }


    @PostMapping("/budget")
    public String addBudget(Budget budget) {
        budgetRepository.save(budget);
        return "Budget";
    }

    @GetMapping("/budget")
    public Iterable<Budget> getBudgets() {
        return budgetRepository.findAll();
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
