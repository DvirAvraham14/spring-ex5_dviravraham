package hac.controllers;

import hac.beans.Budget;
import hac.beans.Category;
import hac.beans.Expense;
import hac.beans.repo.BudgetRepository;
import hac.beans.repo.CategoryRepository;
import hac.beans.repo.ExpenseRepository;
import hac.services.BudgetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

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

    @PostMapping("/categories/add")
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

    @Autowired
    private BudgetService budgetService;




    @ModelAttribute("editMode")
    public boolean addEditModeModelAttribute(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.endsWith("/add") ? false : uri.contains("/edit");
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("/budget/update")
    public String updateBudget(@RequestParam("id") Long id,@Valid Budget budget, BindingResult result,
                               Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("isAdmin", true);
            model.addAttribute("error", "Error");
            return "/user/budget/add";
        }
        String username = budgetRepository.findById(id).get().getUsername();
        budget.setUsername(username);
        budgetRepository.save(budget);
        model.addAttribute("isAdmin", true);
        model.addAttribute("budgetItems", budgetRepository.findByUsername(username));
        return "/user/budget/view";
    }

    @PostMapping("/budget/edit")
    public String editBudget(@RequestParam("id") Long id, Model model) {
        String username = budgetRepository.findById(id).get().getUsername();
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid budget id: " + id));
        budget.setUsername(username);
        model.addAttribute("budget", budget);
        model.addAttribute("isAdmin", true);
        return "/user/budget/add";
    }


    @PostMapping("/budget/del")
    public String deleteBudget(@RequestParam("id") Long id, Model model) {
        String username = budgetRepository.findById(id).get().getUsername();
        budgetRepository.deleteById(id);
        List<Budget> budgets = budgetRepository.findByUsername(username);
        model.addAttribute("budgetItems", budgets);
        model.addAttribute("isAdmin", true);
        return "/user/budget/view";
    }

    @PostMapping("/budget")
    public String showBudgets(@RequestParam("userId") String username, Model model, Principal principal) {
        try {
            List<Budget> budgets = budgetRepository.findByUsername(username);
            model.addAttribute("budgetItems", budgets);
            model.addAttribute("isAdmin", true);
            return "/user/budget/view";
        } catch (Exception e) {
            model.addAttribute("status", "Error");
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @Autowired
    private ExpenseRepository expenseRepository;


    @PostMapping("/expense")
    public String showExpenses(@RequestParam("userId") String username, Model model) {
        try {
            List<Expense> expenses = expenseRepository.findByUsername(username);
            model.addAttribute("expenses", expenses);
            model.addAttribute("isAdmin", true);

            return "/user/expense/view";
        } catch (Exception e) {
            model.addAttribute("status", "Error");
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

}
