package com.travel.model;

public class WellnessTour extends Tour {
    private final boolean includesSpaProcedures;

    public WellnessTour(String id, String title, String destination, double baseDailyRate,
                        int days, TransportType transport, MealPlan mealPlan, boolean includesSpaProcedures) {
        super(id, title, destination, baseDailyRate, days, transport, mealPlan);
        this.includesSpaProcedures = includesSpaProcedures;
    }

    @Override
    public String getTourType() {
        return "Лікування";
    }

    @Override
    protected double getSpecificFee() {
        return includesSpaProcedures ? 40.0 * getDays() : 0.0;
    }

    public boolean isIncludesSpaProcedures() {
        return includesSpaProcedures;
    }
}