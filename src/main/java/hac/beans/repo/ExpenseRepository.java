package hac.beans.repo;

import hac.beans.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUsername(String username);

    List<Expense> findAllByUsernameAndDateAndCategory(String username, LocalDate date, String category);

    List<Expense> findAllByUsernameAndCategory(String username, String category);

    List<Expense> findAllByUsernameAndDate(String username, LocalDate date);

    List<Expense> findAllByUsername(String username);
}
