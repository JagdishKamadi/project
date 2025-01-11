package com.epam.constant;

public enum Category {
    GROCERIES(""),
    LEISURE(""),
    ELECTRONICS(""),
    UTILITIES(""),
    CLOTHING(""),
    HEALTH(""),
    OTHERS("");

    private String categoryDescription;

    private Category(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
