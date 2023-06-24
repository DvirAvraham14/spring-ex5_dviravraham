package hac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hac.beans.Budget;
import hac.beans.Category;
import hac.beans.repo.BudgetRepository;
import hac.beans.repo.CategoryRepository;
import hac.collections.BudgetList;
import hac.services.BudgetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Autowired
    private CategoryRepository categoryRepository;

    // Exception handler for handling any unhandled exceptions
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e) {
        model.addAttribute("status", "Error");
        model.addAttribute("message", e.getMessage());
        return "error";
    }

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
    public List<Category> addCategoriesModelAttribute(){
        return categoryRepository.findAll();
    }

    @GetMapping("")
    public String showBudgetPage() {
        return VIEW_PATH + "index";
    }


    @GetMapping("/view")
    public String showBudgets(Model model, Principal principal) {
        String username = principal.getName();
        List<Budget> budgets = budgetRepository.findAllByUsername(username);
        model.addAttribute("budgetItems", budgets);

        return VIEW_PATH + "view";
    }


    @GetMapping("/add")
    public String showAddBudgetPage(Model model) {
        BudgetList budgets = new BudgetList();
        model.addAttribute("budgetList", budgets);
        return VIEW_PATH + "add";
    }

    @PostMapping(value = "/getBudget")
    public String getBudget(@ModelAttribute("budgetList") BudgetList budgets, Model model, Principal principal,
                            @RequestBody String date) {
        // Parse the JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(date);
            String extractedDate = jsonNode.get("date").asText();
            BudgetList bud = new BudgetList(budgetRepository.findAllByUsernameAndMonth(principal.getName(), extractedDate));
            if (bud.getBudgets().size() != 0) {
                budgets.setBudgets(bud);
                model.addAttribute("budgetList", budgets);
                return "user/budget/forms/new :: addBudget";
            }
            budgets.get(0).setMonth(extractedDate);
            return "user/budget/forms/new :: addBudget";
            // Use the extracted date as needed

        } catch (Exception e) {
            // Handle parsing error
            e.printStackTrace();
        }
        return "user/budget/forms/new :: addBudget";
    }


    @PostMapping(value = "/save", params = {"addRow"})
    public String addRow(@Valid @ModelAttribute("budgetList") BudgetList budgets, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return VIEW_PATH + "add";
        }

        budgets.add();
        model.addAttribute("budgetList", budgets);
        return VIEW_PATH + "add";
    }

    @PostMapping(value = "/save", params = {"DelRow"})
    public String delRow(@ModelAttribute("budgetList") BudgetList budgets, BindingResult result, Model model,
                         @RequestParam("DelRow") int delRow) {
        if (!result.hasErrors()) {
            if(budgets.getBudgets().get(delRow).getId() != null)
                budgetRepository.delete(budgets.getBudgets().get(delRow));
            budgets.deleteBudget(delRow);

            model.addAttribute("budgetList", budgets);
        }

        return VIEW_PATH + "add";
    }


    @PostMapping(value = "/save", params = {"save"})
    public String saveBudget(@Valid @ModelAttribute("budgetList") BudgetList budgets, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            System.out.println("Duplicate entry for category and month");

            return VIEW_PATH + "add";
        }
        try {
            budgetService.saveBudgets(budgets, principal.getName(), budgets.getBudgets().get(0).getMonth());
        }catch (DataIntegrityViolationException e){
            model.addAttribute("duplicate", "Duplicate entry for category and month");
            return VIEW_PATH + "add";
        }
        catch (Exception e) {
            return VIEW_PATH + "add";
        }
        return "redirect:/budget/view";
    }


    @PostMapping("/del/{id}")
    public String deleteBudget(@PathVariable("id") Long id) {
            budgetService.deleteBudget(id);
        return "redirect:/budget/view";
    }


    @PostMapping("/edit")
    public String editBudget(@RequestParam("id") Long id, Model model) {
        // Find the budget item with the given ID
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid budget id: " + id));
        model.addAttribute("budget", budget);
        return VIEW_PATH + "add";
    }


//
    @PostMapping("/update")
    public String updateBudget(@Valid Budget budget, BindingResult result,
                               Model model, Principal principal) {

        if (result.hasErrors()) {
            model.addAttribute("budget", budget);
            return VIEW_PATH + "add";
        }
        System.out.println(budget.getId());
        budget.setUsername(principal.getName());
        Budget existingBudget = budgetRepository.findByUsernameAndCategoryAndMonth(budget.getUsername(), budget.getCategory(), budget.getMonth());
        // Check if there is an existing budget with the same category and month but different limit
        if(existingBudget != null && Math.abs(existingBudget.getMonthlyLimit() - budget.getMonthlyLimit()) > 0.001){
            budgetService.deleteBudget(existingBudget.getId());
            budgetRepository.save(budget);
            return "redirect:/budget/view";
        }
        // Check if there is an existing budget with the same category and month
        if (existingBudget != null) {
            FieldError error = new FieldError("budget", "category", "A budget with the same category and month already exists.");
            result.addError(error);
            model.addAttribute("budget", budget);
            model.addAttribute("editMode", true);
            return VIEW_PATH + "add";
        }

        // No existing budget found, save the new budget
//        budgetService.deleteBudget(existingBudget.getId());
        budgetRepository.save(budget);
        return "redirect:/budget/view";
    }

//
//    @PostMapping("/update")
//    public String updateBudget(@Valid Budget budget, BindingResult result,
//                               Model model, Principal principal) {
//
//        if (result.hasErrors()) {
//            model.addAttribute("budget", budget);
//            return VIEW_PATH + "add";
//        }
//        budget.setUsername(principal.getName());
//        System.out.println(budget.getCategory());
//        budgetRepository.save(budget);
//
//        return "redirect:/budget/view";
//    }

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

    @PostMapping("/filter")
    public String filterBudget(@RequestParam(value = "category", required = false) String category,
                                 Model model, Principal principal) {
        String username = principal.getName();

        if (category == null || category.isEmpty()) {
            category = null; // Set category to null to indicate searching for all categories
        }

        System.out.println("Category:     " + category);
        List<Budget> budgets;
       if (category != null) {
           // Only category is specified
           System.out.println("Category is not null");
           budgets = budgetRepository.findAllByUsernameAndCategory(username, category);
       } else {
            // Neither category nor date is specified, search all expenses
           budgets = budgetRepository.findAllByUsername(username);
        }

        model.addAttribute("budgetItems", budgets);
        model.addAttribute("categoryValue", category);
        return VIEW_PATH + "view";
    }

}

