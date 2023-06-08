package hac.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;


@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is mandatory")
    private String username;

    @NotEmpty(message = "Category is mandatory")
    private String category;

    @NotEmpty(message = "Amount is mandatory")
    @PositiveOrZero(message = "Amount must be positive")
    @Column(name = "monthly_limit")
    private double monthlyLimit;

    public Budget() {
    }

    public Budget(String user, String category, double monthlyLimit) {
        this.username = user;
        this.category = category;
        this.monthlyLimit = monthlyLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long budgetId) {
        this.id = budgetId;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String username) {
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
}




