package com.travel.model;

public enum MealPlan {
    NONE(0.0),
    BREAKFAST(15.0),
    HALF_BOARD(30.0),
    ALL_INCLUSIVE(55.0);

    private final double dailyCost;

    MealPlan(double dailyCost) {
        this.dailyCost = dailyCost;
    }

    public double getDailyCost() {
        return dailyCost;
    }
}