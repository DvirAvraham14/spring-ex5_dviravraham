package hac.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotEmpty(message = "Username is mandatory")
//    private String username;

//    @NotEmpty(message = "Category is mandatory")
    private String category;
//
//    @NotEmpty(message = "Amount is mandatory")
//    @Positive(message = "Amount must be positive")
    private double amount;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String notes;

    public Expense() {
    }

    public Expense(String username, String category, double amount, LocalDate date, String notes) {
//        this.username = username;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long expenseId) {
        this.id = expenseId;
    }

//    public String getUser() {
//        return this.username;
//    }

//    public void setUser(String username) {
//        this.username = username;
//    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

