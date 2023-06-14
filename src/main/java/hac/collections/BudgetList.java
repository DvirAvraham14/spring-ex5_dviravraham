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

    public void addBudget(Budget budget) {
        this.budgets.add(budget);
    }

    public ArrayList<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(ArrayList<Budget> budgets) {
        this.budgets = budgets;
    }

    public Budget getBudget(int index) {
        return budgets.get(index);
    }

    public void removeBudget(int index) {
        budgets.remove(index);
    }
}
