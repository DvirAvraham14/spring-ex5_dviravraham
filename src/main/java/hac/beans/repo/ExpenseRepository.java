package hac.beans.repo;

import hac.beans.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUsername(String username);
    //Iterable<Expense> findByUser(String username);
}
