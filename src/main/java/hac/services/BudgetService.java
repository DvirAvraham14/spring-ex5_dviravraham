package hac.services;

import hac.beans.Budget;
import hac.beans.repo.BudgetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Transactional
    public void saveBudgets(ArrayList<Budget> budgets) {
        if (budgets == null) return;
        for (Budget budget : budgets) {
            budgetRepository.save(budget);
        }
    }

    public void saveBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    public Iterable<Budget> getBudgetsByUser(String username) {
        return budgetRepository.findByUsername(username);
    }

}
