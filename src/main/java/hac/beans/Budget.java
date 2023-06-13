package hac.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;


@Entity
@Table(name = "budgets")
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

    @DateTimeFormat(pattern = "MM-yyyy")
    private String month;

    public Budget() {
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}




