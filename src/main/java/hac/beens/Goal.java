package hac.beens;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;


@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric with no spaces")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Description must be alphanumeric with no special characters")
    private String description;

    @NotEmpty(message = "Target amount is mandatory")
    @PositiveOrZero(message = "Target amount must be positive")
    @Column(name = "target_amount")
    private double targetAmount;

    @NotEmpty(message = "Current amount must be positive")
    @PositiveOrZero(message = "Current amount must be positive")
    @Column(name = "current_amount")
    private double currentAmount;

    public Goal() {
    }

    public Goal(String username, String description, double targetAmount, double currentAmount) {
        this.username = username;
        this.description = description;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long goalId) {
        this.id = goalId;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }
}




