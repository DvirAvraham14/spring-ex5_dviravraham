//package hac.controllers;
//
//import hac.beans.Expense;
//import hac.beans.repo.ExpenseRepository;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.security.Principal;
//
//@Controller
//public class ExpenseController {
//
//    @Autowired
//    private ExpenseRepository expenseRepository;
//
//    @GetMapping("/add-expense")
//    public String showAddExpensePage(Model model) {
//        model.addAttribute("expense", new Expense());
//        return "add-expense";
//    }
//
//    @PostMapping("/add-expense")
//    public String addExpense(@Valid @ModelAttribute Expense expense, BindingResult result, Principal principal) {
//        if (result.hasErrors()) {
//            return "add-expense";
//        }
//        expense.setUser(principal.getName());
//        expenseRepository.save(expense);
//        return "redirect:/my-expenses";
//    }
//
////    @GetMapping("/my-expenses")
////    public String showMyExpensesPage(Model model, Principal principal) {
////        String username = principal.getName();
////        Iterable<Expense> expenses = expenseRepository.findByUser(username);
////        model.addAttribute("expenses", expenses);
////        return "my-expenses";
////    }
//}
