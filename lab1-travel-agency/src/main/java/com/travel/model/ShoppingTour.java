package com.travel.model;

public class ShoppingTour extends Tour {
    private final int mallsVisited;

    public ShoppingTour(String id, String title, String destination, double baseDailyRate,
                        int days, TransportType transport, MealPlan mealPlan, int mallsVisited) {
        super(id, title, destination, baseDailyRate, days, transport, mealPlan);
        this.mallsVisited = mallsVisited;
    }

    @Override
    public String getTourType() {
        return "Шопінг";
    }

    @Override
    protected double getSpecificFee() {
        return mallsVisited * 15.0;
    }

    public int getMallsVisited() {
        return mallsVisited;
    }
}