package com.travel.service;

import com.travel.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelAgencyServiceTest {

    private TravelAgencyService service;
    private Tour tour1;
    private Tour tour2;

    @BeforeEach
    void setUp() {
        tour1 = new ShoppingTour("T01", "Мілан", "Італія", 70.0, 3, TransportType.PLANE, MealPlan.NONE, 2);
        tour2 = new CruiseTour("T02", "Круїз", "Іспанія", 100.0, 7, TransportType.SHIP, MealPlan.ALL_INCLUSIVE, 4);
        service = new TravelAgencyService(List.of(tour1, tour2));
    }

    @Test
    @DisplayName("Отримання повного списку путівок")
    void shouldReturnAllTours() {
        assertEquals(2, service.getAllTours().size());
    }

    @Test
    @DisplayName("Сортування путівок за зростанням ціни з використанням Mockito")
    void shouldSortToursByPriceAscending() {
        Tour expensiveMock = Mockito.mock(Tour.class);
        when(expensiveMock.calculateTotalPrice()).thenReturn(9999.0);

        Tour cheapMock = Mockito.mock(Tour.class);
        when(cheapMock.calculateTotalPrice()).thenReturn(150.0);

        TravelAgencyService mockService = new TravelAgencyService(List.of(expensiveMock, cheapMock));
        List<Tour> sorted = mockService.sortByPriceAscending();

        assertEquals(150.0, sorted.get(0).calculateTotalPrice());
        assertEquals(9999.0, sorted.get(1).calculateTotalPrice());
    }

    @Test
    @DisplayName("Сортування путівок за спаданням кількості днів")
    void shouldSortToursByDaysDescending() {
        List<Tour> sorted = service.sortByDaysDescending();
        assertEquals("T02", sorted.get(0).getId());
        assertEquals("T01", sorted.get(1).getId());
    }

    @Test
    @DisplayName("Фільтрація путівок за всіма критеріями")
    void shouldFilterToursByCriteria() {
        List<Tour> byTransportAndBudget = service.filterTours(null, TransportType.PLANE, null, null, null, 600.0);
        assertEquals(1, byTransportAndBudget.size());
        assertEquals("T01", byTransportAndBudget.get(0).getId());

        List<Tour> byTypeAndDays = service.filterTours("Круїз", TransportType.SHIP, MealPlan.ALL_INCLUSIVE, 5, 10, 2000.0);
        assertEquals(1, byTypeAndDays.size());
        assertEquals("T02", byTypeAndDays.get(0).getId());
    }

    @Test
    @DisplayName("Кастомізація існуючого та неіснуючого туру")
    void shouldCustomizeTourParameters() {
        Optional<Tour> updated = service.customizeAndSelectTour("T01", TransportType.BUS, MealPlan.BREAKFAST, 5);
        assertTrue(updated.isPresent());
        assertEquals(TransportType.BUS, updated.get().getTransport());
        assertEquals(MealPlan.BREAKFAST, updated.get().getMealPlan());
        assertEquals(5, updated.get().getDays());

        Optional<Tour> notFound = service.customizeAndSelectTour("UNKNOWN", TransportType.BUS, MealPlan.NONE, 2);
        assertTrue(notFound.isEmpty());
    }
}