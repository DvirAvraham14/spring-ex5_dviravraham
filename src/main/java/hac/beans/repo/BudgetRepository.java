package hac.beans.repo;

import hac.beans.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findAllByUsernameAndCategory(String username, String category);

    List<Budget> findAllByUsernameAndMonth(String username, String month);

    List<Budget> findAllByUsername(String username);

    Budget findByUsernameAndCategoryAndMonth(String username, String category, String month);
}
