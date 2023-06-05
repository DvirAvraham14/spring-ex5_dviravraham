//package hac.beens;
//
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "goals")
//public class Goal {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long goalId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private String description;
//    private double targetAmount;
//    private double currentAmount;
//
//    public Goal() {
//    }
//
//    public Goal(User user, String description, double targetAmount, double currentAmount) {
//        this.user = user;
//        this.description = description;
//        this.targetAmount = targetAmount;
//        this.currentAmount = currentAmount;
//    }
//
//    public Long getGoalId() {
//        return goalId;
//    }
//
//    public void setGoalId(Long goalId) {
//        this.goalId = goalId;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public double getTargetAmount() {
//        return targetAmount;
//    }
//
//    public void setTargetAmount(double targetAmount) {
//        this.targetAmount = targetAmount;
//    }
//
//    public double getCurrentAmount() {
//        return currentAmount;
//    }
//
//    public void setCurrentAmount(double currentAmount) {
//        this.currentAmount = currentAmount;
//    }
//}
//
//
//
//
