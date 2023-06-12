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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
//    @PostMapping("/expense")
//    public String addExpense(@RequestParam(required = false) @RequestBody Expense expense, BindingResult result, Principal principal) {
//        if (result.hasErrors()) {
//            //userInfo.setId(id);
//            return "login";
//        }
//        System.out.println(principal.getName());
//        expenseRepository.save(expense);
//        return "Expense";
//    }

    @GetMapping("/expense")
    public Iterable<Expense> getExpenses(Expense expense) {
        return expenseRepository.findAll();
    }

    @GetMapping("/expenseHome")
    public String showAddExpensePageMain(Model model) {
        model.addAttribute("expense", new Expense());
        return "expenseHome";
    }

    @GetMapping("/add-expense")
    public String showAddExpensePage(Model model) {
        model.addAttribute("expense", new Expense());
        return "add-expense";
    }


    @GetMapping("/my-expenses")
    public String showExpenses(Model model, Principal principal) {
        String username = principal.getName();
        List<Expense> expenses = expenseRepository.findByUsername(username);
        model.addAttribute("expenses", expenses);
        return "my-expenses";
    }

    @GetMapping("/add-budget")
    public String showAddBudgetPage(Model model) {
        model.addAttribute("budget", new Budget());
        return "add-budget";
    }


    @GetMapping("/my-budgets")
    public String showBudgets(Model model, Principal principal) {
        String username = principal.getName();
        List<Budget> budgets = budgetRepository.findByUsername(username);
        model.addAttribute("budgetItems", budgets);
        return "my-budgets";
    }

    @GetMapping("/deleteBudget/{id}")
    public String deleteBudget(@PathVariable("id") Long id) {
        // Delete the budget item with the given ID
        budgetRepository.deleteById(id);

        return "budgetHome";
    }



    @GetMapping("/budgetHome")
    public String showBudgetPage(Model model, Principal principal) {
        model.addAttribute("budgets", new Budget());
        return "budgetHome";
    }

//    @GetMapping("/budget")
//    public Iterable<Budget> getBudgets() {
//        return budgetRepository.findAll();
//    }


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

//    @GetMapping("/my-expenses")
//    public String showMyExpensesPage(Model model, Principal principal) {
//        String username = principal.getName();
//        String query = "SELECT * FROM your_table_name WHERE name = 1";
//        //Iterable<Expense> expenses = expenseRepository.findByUser(username);
//        model.addAttribute("expense", query);
//        return "my-expenses";
//    }


//    @PostMapping("/budget")
//    public String addBudget(Budget budget) {
//        budgetRepository.save(budget);
//        return "Budget";
//    }