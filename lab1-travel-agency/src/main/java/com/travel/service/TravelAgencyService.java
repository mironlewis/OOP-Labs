package com.travel.service;

import com.travel.model.MealPlan;
import com.travel.model.Tour;
import com.travel.model.TransportType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TravelAgencyService {
    private final List<Tour> tours;

    public TravelAgencyService(List<Tour> initialTours) {
        this.tours = new ArrayList<>(initialTours);
    }

    public List<Tour> getAllTours() {
        return new ArrayList<>(tours);
    }

    public List<Tour> filterTours(String tourType, TransportType transport, MealPlan mealPlan,
                                  Integer minDays, Integer maxDays, Double maxBudget) {
        return tours.stream()
                .filter(t -> tourType == null || t.getTourType().equalsIgnoreCase(tourType))
                .filter(t -> transport == null || t.getTransport() == transport)
                .filter(t -> mealPlan == null || t.getMealPlan() == mealPlan)
                .filter(t -> minDays == null || t.getDays() >= minDays)
                .filter(t -> maxDays == null || t.getDays() <= maxDays)
                .filter(t -> maxBudget == null || t.calculateTotalPrice() <= maxBudget)
                .collect(Collectors.toList());
    }

    public List<Tour> sortByPriceAscending() {
        return tours.stream()
                .sorted(Comparator.comparingDouble(Tour::calculateTotalPrice))
                .collect(Collectors.toList());
    }

    public List<Tour> sortByDaysDescending() {
        return tours.stream()
                .sorted(Comparator.comparingInt(Tour::getDays).reversed())
                .collect(Collectors.toList());
    }

    public Optional<Tour> customizeAndSelectTour(String tourId, TransportType newTransport,
                                                 MealPlan newMealPlan, int newDays) {
        return tours.stream()
                .filter(t -> t.getId().equalsIgnoreCase(tourId))
                .findFirst()
                .map(tour -> {
                    tour.setTransport(newTransport);
                    tour.setMealPlan(newMealPlan);
                    tour.setDays(newDays);
                    return tour;
                });
    }
}