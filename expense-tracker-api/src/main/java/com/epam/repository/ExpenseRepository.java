package com.epam.repository;

import com.epam.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    public Optional<Expense> findByIdAndUserId(Integer id, Integer userId);

    @Query(name = "DELETE FROM expense WHERE id= ?1 AND user_id= ?2",
            nativeQuery = true)
     void deleteByIdAndUserId(Integer id, Integer userId);

    @Query("SELECT e FROM Expense e WHERE e.createdAt BETWEEN :startDate AND :endDate")
    List<Expense> getExpensesBaseOnCustomDateFilter(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
