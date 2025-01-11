package com.epam;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        System.out.println(today.minusDays(7));
        System.out.println(today.minusMonths(7));
    }
}
