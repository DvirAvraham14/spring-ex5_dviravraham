package hac.services;

import hac.beans.Budget;
import hac.beans.repo.BudgetRepository;
import hac.collections.BudgetList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;


    @Transactional
    public void saveBudgets(BudgetList budgets, String username, String month) {
        try {
            for (Budget budget : budgets.getBudgets()) {
                budget.setUsername(username);
                budget.setMonth(month);
                budgetRepository.save(budget);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public BudgetList getBudget(long id) {
        BudgetList budgets = new BudgetList();
        Budget budget = budgetRepository.findById(id).get();
        budgets.addBudget(budget);
        return budgets;
    }

    public BudgetList getByUserAndMonth(String username, String month) {
        List<Budget> budgets = budgetRepository.findAllByUsernameAndMonth(username, month);
        if (budgets.size() != 0) {
            return new BudgetList(budgets);
        } else {
            return new BudgetList();
        }
    }

    public void saveBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    public void deleteBudget(long id) {
        budgetRepository.deleteById(id);
    }


}
