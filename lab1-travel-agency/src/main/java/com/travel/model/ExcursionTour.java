package com.travel.model;

public class ExcursionTour extends Tour {
    private final int excursionsCount;

    public ExcursionTour(String id, String title, String destination, double baseDailyRate,
                         int days, TransportType transport, MealPlan mealPlan, int excursionsCount) {
        super(id, title, destination, baseDailyRate, days, transport, mealPlan);
        this.excursionsCount = excursionsCount;
    }

    @Override
    public String getTourType() {
        return "Екскурсії";
    }

    @Override
    protected double getSpecificFee() {
        return excursionsCount * 25.0;
    }

    public int getExcursionsCount() {
        return excursionsCount;
    }
}