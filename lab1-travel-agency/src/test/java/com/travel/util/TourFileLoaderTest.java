package com.travel.util;

import com.travel.model.Tour;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TourFileLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Коректне зчитування всіх типів путівок із файлу")
    void shouldLoadAllTourTypesFromFile() throws IOException {
        Path testFile = tempDir.resolve("test_tours.txt");
        List<String> lines = List.of(
                "# Коментар",
                "",
                "RESORT;T01;Анталія;Туреччина;60.0;7;PLANE;ALL_INCLUSIVE;5",
                "EXCURSION;T02;Прага;Чехія;45.0;5;BUS;BREAKFAST;6",
                "WELLNESS;T03;Карпати;Україна;50.0;10;TRAIN;HALF_BOARD;true",
                "SHOPPING;T04;Мілан;Італія;70.0;3;PLANE;NONE;4",
                "CRUISE;T05;Круїз;Іспанія;90.0;8;SHIP;ALL_INCLUSIVE;5"
        );
        Files.write(testFile, lines);

        TourFileLoader loader = new TourFileLoader();
        List<Tour> tours = loader.loadToursFromFile(testFile);

        assertEquals(5, tours.size());
    }

    @Test
    @DisplayName("Викидання помилки при невідомому типі туру у файлі")
    void shouldThrowExceptionOnUnknownTourType() throws IOException {
        Path badFile = tempDir.resolve("bad_tours.txt");
        Files.writeString(badFile, "UNKNOWN;T99;Тест;Країна;50.0;5;BUS;NONE;1");

        TourFileLoader loader = new TourFileLoader();
        assertThrows(IllegalArgumentException.class, () -> loader.loadToursFromFile(badFile));
    }
}