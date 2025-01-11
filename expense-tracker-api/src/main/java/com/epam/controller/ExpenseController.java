package com.epam.controller;

import com.epam.businessservice.ExpenseService;
import com.epam.model.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Expense> add(@RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.add(expense), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<Expense> remove(@PathVariable Integer id) {
        return new ResponseEntity<>(expenseService.remove(id), HttpStatus.FOUND);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Expense> update(@PathVariable Integer id, @RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.update(id, expense), HttpStatus.OK);
    }

    @GetMapping(value = "/filter/pastWeekExpenses")
    public ResponseEntity<List<Expense>> getPastWeekExpense() {
        LocalDate today = LocalDate.now();
        return new ResponseEntity<>(expenseService.getExpensesBaseOnCustomDateFilter(today.minusDays(7), Optional.empty()), HttpStatus.OK);
    }

    @GetMapping(value = "/filter/pastMonthExpenses")
    public ResponseEntity<List<Expense>> getPastMonthExpense() {
        LocalDate today = LocalDate.now();
        return new ResponseEntity<>(expenseService.getExpensesBaseOnCustomDateFilter(today.minusMonths(1), Optional.empty()), HttpStatus.OK);
    }

    @GetMapping(value = "/filter/lastThreeMonthExpenses")
    public ResponseEntity<List<Expense>> getLastThreeMonthExpense() {
        LocalDate today = LocalDate.now();
        return new ResponseEntity<>(expenseService.getExpensesBaseOnCustomDateFilter(today.minusMonths(3), Optional.empty()), HttpStatus.OK);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Expense>> getCustomExpenses(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws DateTimeParseException {
        return new ResponseEntity<>(expenseService.getExpensesBaseOnCustomDateFilter(startDate, Optional.ofNullable(endDate)), HttpStatus.OK);
    }
}
