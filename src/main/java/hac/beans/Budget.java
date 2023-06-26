package hac.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Objects;

/*
    * This class represents a budget for a specific category for a specific month.
    * It is used to limit the amount of money that can be spent on a category in a month.
    * It is also used to display the budget for a specific category in a specific month.
 */
@Entity
@Table(name = "budgets", uniqueConstraints = { @UniqueConstraint(name="Checking", columnNames = {"username", "category", "month"})})
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private String username;

    @NotEmpty(message = "Category is mandatory")
    private String category;

    @PositiveOrZero(message = "Amount must be positive")
    private double monthlyLimit;

    @NotEmpty(message = "Month is mandatory")
    @DateTimeFormat(pattern = "MM-yyyy")
    private String month;




    public Budget() {
    }

    public Budget(String username) {
        this.username = username;
    }

    public Budget(Budget budget) {
        this.username = budget.getUsername();
        this.month = budget.getMonth();
    }

    public Budget(String username, String month) {
        this.username = username;
        this.month = month;
    }

    public Budget(String category, double monthlyLimit, String month) {
        this.category = category;
        this.monthlyLimit = monthlyLimit;
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long budgetId) {
        this.id = budgetId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    /*
        * This method is used to compare two budgets.
        * Two budgets are equal if they have the same username, month and category.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Budget)) return false;
        Budget budget = (Budget) o;
        return Objects.equals(username, budget.username) &&
                Objects.equals(month, budget.month) &&
                Objects.equals(category, budget.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, month, category);
    }

}




