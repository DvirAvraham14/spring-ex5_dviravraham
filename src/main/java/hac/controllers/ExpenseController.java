package hac.controllers;

import hac.beans.Budget;
import hac.beans.Category;
import hac.beans.Expense;
import hac.beans.repo.CategoryRepository;
import hac.beans.repo.ExpenseRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/expense")
public class ExpenseController {
    private final String VIEW_PATH = "/user/expense/";

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute("editMode")
    public boolean addEditModeModelAttribute(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.endsWith("/add") ? false : uri.contains("/edit");
    }

    @ModelAttribute("isAdmin")
    public boolean addIsAdminModelAttribute(){
        return false;
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @GetMapping("")
    public String expenseHome(Model model) {
        return VIEW_PATH + "index";
    }

    @GetMapping("/view")
    public String showExpenses(Model model, Principal principal) {
        String username = principal.getName();
        List<Expense> expenses = expenseRepository.findByUsername(username);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("expenses", expenses);
        return VIEW_PATH + "view";
    }

    @GetMapping("/add")
    public String showAddExpensePageMain(Expense expense, Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return VIEW_PATH + "add";
    }

    @PostMapping("/add")
    public String addExpense(@Valid @ModelAttribute Expense expense, BindingResult result, Principal principal, Model model) {
        expense.setUser(principal.getName());
        if (result.hasErrors()) {
            return VIEW_PATH + "add";
        }
        expenseRepository.save(expense);
        return "redirect:/expense/view";
    }

    @PostMapping("/del")
    public String deleteBudget(@RequestParam("id") Long id) {
        // Delete the budget item with the given ID
        expenseRepository.deleteById(id);
        return "redirect:/expense/view";
    }

    @PostMapping("/edit")
    public String editExpense(@RequestParam("id") Long id, Model model) {
        // Find the budget item with the given ID
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid budget id: " + id));
        model.addAttribute("expense", expense);
        return  VIEW_PATH + "add";
    }

    @PostMapping("/update")
    public String updateExpense(@Valid Expense expense, BindingResult result, Model model, Principal principal) {
        expense.setUser(principal.getName());
        if (result.hasErrors()) {
            model.addAttribute("expense", expense);
            return  VIEW_PATH + "edit";
        }
        expenseRepository.save(expense);
        return "redirect:/expense/view";
    }
}