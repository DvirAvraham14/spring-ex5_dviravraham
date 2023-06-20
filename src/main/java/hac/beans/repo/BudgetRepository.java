package hac.beans.repo;

import hac.beans.Budget;
import hac.beans.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findAllByUsernameAndCategory(String username, String category);

    List<Budget> findAllByUsernameAndMonth(String username, String month);

 //   List<Budget> findByUsername(String username);

    List<Budget> findAllByUsername(String username);


}
