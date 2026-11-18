package com.travel.model;

import java.util.Objects;

public abstract class Tour {
    private final String id;
    private final String title;
    private final String destination;
    private final double baseDailyRate;
    private int days;
    private TransportType transport;
    private MealPlan mealPlan;

    protected Tour(String id, String title, String destination, double baseDailyRate,
                   int days, TransportType transport, MealPlan mealPlan) {
        if (days <= 0 || baseDailyRate < 0) {
            throw new IllegalArgumentException("Days must be positive and daily rate non-negative.");
        }
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.destination = Objects.requireNonNull(destination, "Destination cannot be null");
        this.baseDailyRate = baseDailyRate;
        this.days = days;
        this.transport = Objects.requireNonNull(transport, "Transport cannot be null");
        this.mealPlan = Objects.requireNonNull(mealPlan, "MealPlan cannot be null");
    }

    public abstract String getTourType();

    protected abstract double getSpecificFee();

    public double calculateTotalPrice() {
        double accommodation = baseDailyRate * days;
        double meals = mealPlan.getDailyCost() * days;
        return accommodation + meals + transport.getBaseCost() + getSpecificFee();
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDestination() { return destination; }
    public double getBaseDailyRate() { return baseDailyRate; }
    public int getDays() { return days; }

    public void setDays(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than zero.");
        }
        this.days = days;
    }

    public TransportType getTransport() { return transport; }

    public void setTransport(TransportType transport) {
        this.transport = Objects.requireNonNull(transport, "Transport cannot be null");
    }

    public MealPlan getMealPlan() { return mealPlan; }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = Objects.requireNonNull(mealPlan, "MealPlan cannot be null");
    }

    @Override
    public String toString() {
        return String.format("[%s] ID:%s | %s (%s) | %d дн. | Транспорт: %s | Харчування: %s | Разом: %.2f$",
                getTourType(), id, title, destination, days, transport, mealPlan, calculateTotalPrice());
    }
}