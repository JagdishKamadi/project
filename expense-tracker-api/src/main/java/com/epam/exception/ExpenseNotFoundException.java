package com.epam.exception;


public class ExpenseNotFoundException extends RuntimeException {

    public ExpenseNotFoundException() {
        super("Expense not found");
    }

    public ExpenseNotFoundException(Integer id) {
        super("Expense not found " + id);
    }
}
