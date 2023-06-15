package hac.beans.repo;

import hac.beans.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findAllByUsernameAndMonth(String username, String month);

    List<Budget> findByUsername(String username);
    //List<Budget> findByUser(String user);

}
