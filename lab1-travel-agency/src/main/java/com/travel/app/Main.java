package com.travel.app;

import com.travel.model.MealPlan;
import com.travel.model.Tour;
import com.travel.model.TransportType;
import com.travel.service.TravelAgencyService;
import com.travel.util.TourFileLoader;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            TourFileLoader loader = new TourFileLoader();
            List<Tour> loadedTours = loader.loadToursFromFile(Path.of("tours.txt"));
            TravelAgencyService service = new TravelAgencyService(loadedTours);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n=== ТУРИСТИЧНА АГЕНЦІЯ ===");
                System.out.println("1. Показати всі путівки");
                System.out.println("2. Сортувати за ціною (від дешевших)");
                System.out.println("3. Підібрати тур (фільтр за транспортом та макс. бюджетом)");
                System.out.println("4. Налаштувати та обрати тур за ID");
                System.out.println("0. Вихід");
                System.out.print("Ваш вибір: ");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> service.getAllTours().forEach(System.out::println);
                    case "2" -> service.sortByPriceAscending().forEach(System.out::println);
                    case "3" -> {
                        System.out.print("Транспорт (BUS, TRAIN, PLANE, SHIP або Enter для пропуску): ");
                        String trInput = scanner.nextLine().trim();
                        TransportType tr = trInput.isEmpty() ? null : TransportType.valueOf(trInput.toUpperCase());

                        System.out.print("Максимальний бюджет ($): ");
                        String budgetInput = scanner.nextLine().trim();
                        Double budget = budgetInput.isEmpty() ? null : Double.parseDouble(budgetInput);

                        service.filterTours(null, tr, null, null, null, budget).forEach(System.out::println);
                    }
                    case "4" -> {
                        System.out.print("Введіть ID туру (напр. T01): ");
                        String id = scanner.nextLine().trim();
                        System.out.print("Новий транспорт (BUS, TRAIN, PLANE, SHIP): ");
                        TransportType tr = TransportType.valueOf(scanner.nextLine().trim().toUpperCase());
                        System.out.print("Харчування (NONE, BREAKFAST, HALF_BOARD, ALL_INCLUSIVE): ");
                        MealPlan mp = MealPlan.valueOf(scanner.nextLine().trim().toUpperCase());
                        System.out.print("Кількість днів: ");
                        int days = Integer.parseInt(scanner.nextLine().trim());

                        service.customizeAndSelectTour(id, tr, mp, days)
                                .ifPresentOrElse(
                                        t -> System.out.println("Обрано оновлений тур:\n" + t),
                                        () -> System.out.println("Тур з таким ID не знайдено.")
                                );
                    }
                    case "0" -> { return; }
                    default -> System.out.println("Невірний вибір.");
                }
            }
        } catch (Exception e) {
            System.err.println("Помилка запуску: " + e.getMessage());
        }
    }
}