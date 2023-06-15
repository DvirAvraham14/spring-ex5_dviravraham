package hac.controllers;

import hac.beans.Budget;
import hac.beans.Category;
import hac.beans.Expense;
import hac.beans.repo.BudgetRepository;
import hac.beans.repo.CategoryRepository;
import hac.beans.repo.ExpenseRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository  categoryRepository;

    @GetMapping("")
    public String showAdminPage(Model model) {
        return "/admin/index";
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "/admin/categories";
    }

    @PostMapping
    public String createCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        System.out.println("deleteCategory" + id);
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }


    @PostMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable Long id, Model model) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            model.addAttribute("category", category);
            return "/admin/edit_category";
        }

        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

    @Autowired
    private UserConfig userConfig;

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<String> users = new ArrayList<>();
        users.add("1");
        users.add("2");
        users.add("3");
        model.addAttribute("users", users);
        return "/admin/users";
    }

    @Autowired
    BudgetRepository budgetRepository;

    @GetMapping("/budget/{name}")
    public String showBudgets(@PathVariable("name") String username ,Model model) {
        List<Budget> budgets = budgetRepository.findByUsername(username);
        model.addAttribute("budgetItems", budgets);
        return "/user/budget/view";
    }

    @Autowired
    private ExpenseRepository expenseRepository;
    @PostMapping("/expense/{name}")
    public String showExpenses(@PathVariable("name") String username,Model model) {
        List<Expense> expenses = expenseRepository.findByUsername(username);
        model.addAttribute("expenses", expenses);
        return "/user/expense/view";
    }
}
