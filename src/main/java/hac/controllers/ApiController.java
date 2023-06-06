package hac.controllers;

import hac.beens.Budget;
import hac.beens.Expense;
import hac.beens.Goal;
import hac.beens.repo.BudgetRepository;
import hac.beens.repo.ExpenseRepository;
import hac.beens.repo.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GoalRepository goalRepository;

    // expense section
    @PostMapping("/api/expense")
    public String addExpense(Expense expense) {
        expenseRepository.save(expense);
        return "Expense added";
    }


    // budget section

    @PostMapping("/api/budget")
    public String addBudget(Budget budget) {
        budgetRepository.save(budget);
        return "Budget added";
    }

    // goal section

    @PostMapping("/api/goal")
    public String addGoal(Goal goal) {
        goalRepository.save(goal);
        return "Goal added";
    }
}
