package hac.collections;

import hac.beans.Budget;

import java.util.ArrayList;

public class BudgetList {
    private ArrayList<Budget> budgets;

    public BudgetList() {
        budgets = new ArrayList<Budget>();
        budgets.add(new Budget());
    }

    public BudgetList(String name) {
        budgets = new ArrayList<Budget>();
        Budget budget = new Budget(name);
        budgets.add(budget);
    }

    public void addBudget(Budget budget) {
        budgets.add(budget);
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
