package com.travel.util;

import com.travel.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TourFileLoader {

    public List<Tour> loadToursFromFile(Path filePath) throws IOException {
        List<Tour> tours = new ArrayList<>();
        List<String> lines = Files.readAllLines(filePath);

        for (String line : lines) {
            if (line.isBlank() || line.startsWith("#")) {
                continue;
            }
            String[] parts = line.split(";");
            String type = parts[0].trim().toUpperCase();
            String id = parts[1].trim();
            String title = parts[2].trim();
            String destination = parts[3].trim();
            double dailyRate = Double.parseDouble(parts[4].trim());
            int days = Integer.parseInt(parts[5].trim());
            TransportType transport = TransportType.valueOf(parts[6].trim().toUpperCase());
            MealPlan mealPlan = MealPlan.valueOf(parts[7].trim().toUpperCase());
            String extraParam = parts[8].trim();

            switch (type) {
                case "RESORT" -> tours.add(new ResortTour(id, title, destination, dailyRate, days, transport, mealPlan, Integer.parseInt(extraParam)));
                case "EXCURSION" -> tours.add(new ExcursionTour(id, title, destination, dailyRate, days, transport, mealPlan, Integer.parseInt(extraParam)));
                case "WELLNESS" -> tours.add(new WellnessTour(id, title, destination, dailyRate, days, transport, mealPlan, Boolean.parseBoolean(extraParam)));
                case "SHOPPING" -> tours.add(new ShoppingTour(id, title, destination, dailyRate, days, transport, mealPlan, Integer.parseInt(extraParam)));
                case "CRUISE" -> tours.add(new CruiseTour(id, title, destination, dailyRate, days, transport, mealPlan, Integer.parseInt(extraParam)));
                default -> throw new IllegalArgumentException("Unknown tour type: " + type);
            }
        }
        return tours;
    }
}