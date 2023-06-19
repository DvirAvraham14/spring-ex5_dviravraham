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

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("")
    public String showAdminPage(Model model) {
        return "/admin/index";
    }

    @ModelAttribute("editMode")
    public boolean addEditModeModelAttribute(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.endsWith("/add") ? false : uri.contains("/edit") || uri.contains("/update");
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @ModelAttribute("isAdmin")
    public boolean addIsAdminModelAttribute(){
        return true;
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
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

    //==================================================================================================

    @PostMapping("/budget/update")
    public String updateBudget(@RequestParam("id") Long id,@Valid Budget budget, BindingResult result,
                               Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Error");
            return "/user/budget/add";
        }
        String username = budgetRepository.findById(id).get().getUsername();
        budget.setUsername(username);
        budgetRepository.save(budget);
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
        return "/user/budget/add";
    }


    @PostMapping("/budget/del")
    public String deleteBudget(@RequestParam("id") Long id, Model model) {
        String username = budgetRepository.findById(id).get().getUsername();
        budgetRepository.deleteById(id);
        List<Budget> budgets = budgetRepository.findByUsername(username);
        model.addAttribute("budgetItems", budgets);
        return "/user/budget/view";
    }

    @PostMapping("/budget")
    public String showBudgets(@RequestParam("userId") String username, Model model, Principal principal) {
        try {
            List<Budget> budgets = budgetRepository.findByUsername(username);
            model.addAttribute("budgetItems", budgets);
            return "/user/budget/view";
        } catch (Exception e) {
            model.addAttribute("status", "Error");
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }


   //=================================================================

    @PostMapping("/expense")
    public String showExpenses(@RequestParam("userId") String username, Model model) {
        List<Expense> expenses = expenseRepository.findByUsername(username);
        model.addAttribute("expenses", expenses);
        return "/user/expense/view";
    }


    @PostMapping("/expense/del")
    public String deleteBudget(@RequestParam("id") Long id) {
        // Delete the budget item with the given ID
        expenseRepository.deleteById(id);
        return "/user/expense/view";
    }

    @PostMapping("/expense/edit")
    public String editExpense(@RequestParam("id") Long id, Model model) {
        // Find the budget item with the given ID
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid budget id: " + id));
        model.addAttribute("expense", expense);
        return  "/user/expense/add";
    }

    @PostMapping("/expense/update")
    public String updateExpense(@Valid Expense expense, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("expense", expense);
            return  "user/expense/add";
        }
        expenseRepository.save(expense);
        List<Expense> expenses = expenseRepository.findByUsername(expense.getUser());
        model.addAttribute("expenses", expenses);
        return "/user/expense/view";
    }


}
