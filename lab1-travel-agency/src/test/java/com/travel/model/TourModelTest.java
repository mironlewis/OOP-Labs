package com.travel.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TourModelTest {

    @Test
    @DisplayName("Розрахунок вартості для всіх типів турів")
    void shouldCalculatePricesForAllTourTypes() {
        ResortTour resort = new ResortTour("T01", "Анталія", "Туреччина", 50.0, 7, TransportType.PLANE, MealPlan.ALL_INCLUSIVE, 5);
        ExcursionTour excursion = new ExcursionTour("T02", "Прага", "Чехія", 40.0, 5, TransportType.BUS, MealPlan.BREAKFAST, 4);
        WellnessTour wellnessWithSpa = new WellnessTour("T03", "Трускавець", "Україна", 50.0, 10, TransportType.TRAIN, MealPlan.HALF_BOARD, true);
        WellnessTour wellnessNoSpa = new WellnessTour("T04", "Моршин", "Україна", 50.0, 10, TransportType.TRAIN, MealPlan.HALF_BOARD, false);
        ShoppingTour shopping = new ShoppingTour("T05", "Мілан", "Італія", 70.0, 3, TransportType.PLANE, MealPlan.NONE, 3);
        CruiseTour cruise = new CruiseTour("T06", "Середземне море", "Іспанія", 90.0, 8, TransportType.SHIP, MealPlan.ALL_INCLUSIVE, 5);

        assertEquals(1335.0, resort.calculateTotalPrice(), 0.01);
        assertEquals(5, resort.getHotelStars());
        assertEquals("Відпочинок", resort.getTourType());

        assertEquals(415.0, excursion.calculateTotalPrice(), 0.01);
        assertEquals(4, excursion.getExcursionsCount());
        assertEquals("Екскурсії", excursion.getTourType());

        assertEquals(1270.0, wellnessWithSpa.calculateTotalPrice(), 0.01);
        assertTrue(wellnessWithSpa.isIncludesSpaProcedures());
        assertEquals("Лікування", wellnessWithSpa.getTourType());

        assertEquals(870.0, wellnessNoSpa.calculateTotalPrice(), 0.01);
        assertFalse(wellnessNoSpa.isIncludesSpaProcedures());

        assertEquals(505.0, shopping.calculateTotalPrice(), 0.01);
        assertEquals(3, shopping.getMallsVisited());
        assertEquals("Шопінг", shopping.getTourType());

        assertEquals(1635.0, cruise.calculateTotalPrice(), 0.01);
        assertEquals(5, cruise.getPortsOfCall());
        assertEquals("Круїз", cruise.getTourType());

        assertTrue(resort.toString().contains("Анталія"));
        assertEquals("Туреччина", resort.getDestination());
        assertEquals(50.0, resort.getBaseDailyRate());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    @DisplayName("Помилка при некоректній кількості днів у конструкторі та сеттері")
    void shouldThrowExceptionWhenDaysAreInvalid(int invalidDays) {
        assertThrows(IllegalArgumentException.class, () ->
                new ExcursionTour("T02", "Прага", "Чехія", 40.0, invalidDays, TransportType.BUS, MealPlan.BREAKFAST, 3)
        );

        Tour validTour = new ShoppingTour("T05", "Мілан", "Італія", 70.0, 3, TransportType.PLANE, MealPlan.NONE, 3);
        assertThrows(IllegalArgumentException.class, () -> validTour.setDays(invalidDays));
    }

    @Test
    @DisplayName("Помилка при від'ємній ставці за день")
    void shouldThrowExceptionWhenRateIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new CruiseTour("T06", "Круїз", "Іспанія", -10.0, 5, TransportType.SHIP, MealPlan.NONE, 2)
        );
    }
}