package com.travel.model;

public class ResortTour extends Tour {
    private final int hotelStars;

    public ResortTour(String id, String title, String destination, double baseDailyRate,
                      int days, TransportType transport, MealPlan mealPlan, int hotelStars) {
        super(id, title, destination, baseDailyRate, days, transport, mealPlan);
        this.hotelStars = hotelStars;
    }

    @Override
    public String getTourType() {
        return "Відпочинок";
    }

    @Override
    protected double getSpecificFee() {
        return hotelStars * 10.0 * getDays();
    }

    public int getHotelStars() {
        return hotelStars;
    }
}