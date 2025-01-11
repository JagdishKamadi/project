package com.epam.businessservice;

import com.epam.exception.ExpenseNotFoundException;
import com.epam.model.AppUser;
import com.epam.model.Expense;
import com.epam.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    public Expense add(Expense expense) {
        expense.setUserId(getCurrentAppUser().getId());
        return expenseRepository.save(expense);
    }

    public Expense remove(Integer id) {
        Expense expense = expenseRepository.findByIdAndUserId(id, getCurrentAppUser().getId()).orElseThrow(() -> new ExpenseNotFoundException(id));
        expenseRepository.deleteByIdAndUserId(id, getCurrentAppUser().getId());
        return expense;
    }

    public List<Expense> getExpensesBaseOnCustomDateFilter(LocalDate startDate, Optional<LocalDate> endDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
        Date today;

        if (endDate.isPresent()) {
            today = Date.from(endDate.get().now().atStartOfDay(defaultZoneId).toInstant());
            return expenseRepository.getExpensesBaseOnCustomDateFilter(date, today);
        }

        today = Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant());
        return expenseRepository.getExpensesBaseOnCustomDateFilter(date, today);
    }

    public Expense update(Integer id, Expense expenseToUpdate) {
        Expense expense = expenseRepository.findByIdAndUserId(id, getCurrentAppUser().getId()).orElseThrow(() -> new ExpenseNotFoundException(id));

        if (!expenseToUpdate.getDescription().isBlank()) {
            expense.setDescription(expenseToUpdate.getDescription());
        }
        if (!expenseToUpdate.getAmount().toString().isBlank()) {
            expense.setAmount(expenseToUpdate.getAmount());
        }
        if (!expenseToUpdate.getCategory().toString().isBlank()) {
            expense.setCategory(expenseToUpdate.getCategory());
        }
        return expenseRepository.save(expense);
    }

    private AppUser getCurrentAppUser() throws UsernameNotFoundException {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
