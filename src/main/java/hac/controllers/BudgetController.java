package hac.controllers;

import hac.beans.Budget;
import hac.beans.repo.BudgetRepository;
import hac.collections.BudgetList;
import hac.services.BudgetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/budget")
public class BudgetController {
    private final String VIEW_PATH = "/user/budget/";

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @ModelAttribute("editMode")
    public boolean addEditModeModelAttribute(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.endsWith("/add") ? false : uri.contains("/edit");
    }

    @GetMapping("")
    public String showBudgetPage(Model model, Principal principal) {
        return VIEW_PATH + "index";
    }


    @GetMapping("/view")
    public String showBudgets(Model model, Principal principal) {
        String username = principal.getName();
        List<Budget> budgets = budgetRepository.findByUsername(username);
        model.addAttribute("budgetItems", budgets);
        return VIEW_PATH + "view";
    }


    @GetMapping("/add")
    public String showAddBudgetPage(Model model, Principal principal) {
        BudgetList budgets = new BudgetList();
        model.addAttribute("budgetList", budgets);
        return VIEW_PATH + "add";
    }

    @PostMapping(value = "/save", params = {"addRow"})
    public String addRow(@ModelAttribute("budgetList") BudgetList budgets, BindingResult result, Model model, Principal principal) {
        System.out.println("addRow");
        budgets.addBudget(new Budget());
        model.addAttribute("budgetList", budgets);
        return VIEW_PATH + "add";
    }


    @PostMapping(value = "/save", params = {"save"})
    public String saveBudget(@Valid @ModelAttribute("budgetList") BudgetList budgets, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            System.out.println("Errors");
            System.out.println(result.getAllErrors());

            model.addAttribute("errors", result.getAllErrors());
            return VIEW_PATH + "add";
        }
        try {
            budgetService.saveBudgets(budgets.getBudgets(), principal.getName(), budgets.getBudgets().get(0).getMonth());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(result.getAllErrors());
            model.addAttribute("errors", e.getMessage());
            return VIEW_PATH + "add";
        }
        return "redirect:/budget/view";
    }

    @PostMapping("/del/{id}")
    public String deleteBudget(@PathVariable("id") Long id) {
        // Delete the budget item with the given ID
        budgetRepository.deleteById(id);

        return VIEW_PATH + "view";
    }

    @PostMapping("/edit/{id}")
    public String editBudget(@PathVariable("id") Long id, Model model) {
        // Find the budget item with the given ID
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid budget id: " + id));
        model.addAttribute("budget", budget);
        return VIEW_PATH + "add";
    }

    @PostMapping("/update")
    public String updateBudget(@Valid Budget budget, BindingResult result, Model model, Principal principal) {
        budget.setUsername(principal.getName());
        if (result.hasErrors()) {
            System.out.println("jere");
            System.out.println(result.getAllErrors());
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("budget", budget);
            return VIEW_PATH + "add";
        }
        budgetRepository.save(budget);
        return "redirect:/budget/view";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

