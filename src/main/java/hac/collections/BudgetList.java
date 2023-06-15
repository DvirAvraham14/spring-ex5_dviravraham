package hac.collections;

import hac.beans.Budget;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public class BudgetList {

    @Valid
    private ArrayList<Budget> budgets;


    public BudgetList() {
        this.budgets = new ArrayList<>();
        Budget budget = new Budget();
        addBudget(budget);
    }

    public BudgetList(List<Budget> bud) {
        this.budgets = new ArrayList<>();
        for (Budget budget : bud) {
            addBudget(budget);
        }
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

    public void setBudgets(BudgetList budgets) {
        this.budgets = new ArrayList<>();
        for (Budget budget : budgets.getBudgets()) {
            this.budgets.add(budget);
        }
    }

    public Budget get(int index) {
        return budgets.get(index);
    }

    public void removeBudget(int index) {
        budgets.remove(index);
    }

}
