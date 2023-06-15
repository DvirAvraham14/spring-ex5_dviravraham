package hac.collections;

import hac.beans.Budget;
import jakarta.validation.Valid;

import java.util.ArrayList;

public class BudgetList {

    @Valid
    private ArrayList<Budget> budgets;

    public BudgetList() {
        this.budgets = new ArrayList<>();
        Budget budget = new Budget();
        addBudget(budget);
    }

    public void add() {
        Budget budget = new Budget();
        budget.setMonth(budgets.get(0).getMonth());
        addBudget(budget);
    }

    public void addBudget(Budget budget) {
        this.budgets.add(budget);
    }

    public void deleteBudget(int index) {
        if (budgets.size() > 1) {
            budgets.remove(index);
        }
    }

    public ArrayList<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(ArrayList<Budget> budgets) {
        this.budgets = budgets;
    }

    public Budget get(int index) {
        return budgets.get(index);
    }

    public void removeBudget(int index) {
        budgets.remove(index);
    }
}
