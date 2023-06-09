package hac.controllers;

import ch.qos.logback.core.model.Model;
import hac.beans.Expense;
import hac.beans.repo.BudgetRepository;
import hac.beans.repo.ExpenseRepository;
import hac.beans.repo.GoalRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GoalRepository goalRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    // expense section
    @RequestMapping(value = "/expense", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public String addExpense(@Valid Expense expense, BindingResult result, Model model) {
        if (result.hasErrors()) {
            //userInfo.setId(id);
            return "login";
        }
        System.out.println("dsfgthyjudsfgb   "+expense);
        expenseRepository.save(expense);
        return "redirect:/";
    }
//
//    @GetMapping("/expense")
//    public Iterable<Expense> getExpenses() {
//        return expenseRepository.findAll();
//    }


    // budget section
//
//    @PostMapping("/budget")
//    public String addBudget(Budget budget) {
//        budgetRepository.save(budget);
//        return "Budget";
//    }
//
//    @GetMapping("/budget")
//    public Iterable<Budget> getBudgets() {
//        return budgetRepository.findAll();
//    }

    // goal section

//    @PostMapping("/api/goal")
//    public String addGoal(Goal goal) {
//        goalRepository.save(goal);
//        return "Goal added";
//    }

    // then comment out the exception handler below and rerun the request,
    // you will see that the response is a 400 bad request returned by spring
    // in other words, if you don't handle the validation errors, Spring will return a 400 bad request
    // writing a custom eception handler allows us to return the errors in a more user friendly way
    // and also allows us to return any other Http response, even a 200 OK response instead of a 400 bad request

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
