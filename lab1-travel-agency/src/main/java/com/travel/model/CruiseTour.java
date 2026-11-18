package com.travel.model;

public class CruiseTour extends Tour {
    private final int portsOfCall;

    public CruiseTour(String id, String title, String destination, double baseDailyRate,
                      int days, TransportType transport, MealPlan mealPlan, int portsOfCall) {
        super(id, title, destination, baseDailyRate, days, transport, mealPlan);
        this.portsOfCall = portsOfCall;
    }

    @Override
    public String getTourType() {
        return "Круїз";
    }

    @Override
    protected double getSpecificFee() {
        return portsOfCall * 35.0;
    }

    public int getPortsOfCall() {
        return portsOfCall;
    }
}