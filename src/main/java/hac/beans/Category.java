package hac.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

/*
    * This class represents a category for a specific user.
    * It is used to limit the amount of money that can be spent on a category in a month.
    * It is also used to display the budget for a specific category in a specific month.
 */

@Entity(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Category name is mandatory")
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long categoryId) {
        this.id = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        this.name = categoryName;
    }

}
