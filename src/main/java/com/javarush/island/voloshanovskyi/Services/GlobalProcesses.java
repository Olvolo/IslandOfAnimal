package com.javarush.island.voloshanovskyi.Services;

import com.javarush.island.voloshanovskyi.Animals.Animal;
import com.javarush.island.voloshanovskyi.island_lokation.Cell;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.javarush.island.voloshanovskyi.Services.ParametersGlobalProcesses.TIME_OUT;
import static com.javarush.island.voloshanovskyi.Services.Services.executionMethods;
import static com.javarush.island.voloshanovskyi.island_lokation.Island.ISLAND_DEPTH;
import static com.javarush.island.voloshanovskyi.island_lokation.Island.ISLAND_WIDTH;

public class GlobalProcesses {
    public static void executionOfMethodsForAllAnimals(Cell[][] cells, ThreadPoolExecutor executorService) {
        for (int i = 0; i < ISLAND_WIDTH; i++) {
            for (int j = 0; j < ISLAND_DEPTH; j++) {
                Cell cell = cells[i][j];
                List<Animal> animals = cell.getAnimals();
                cell.incrementGrassCount();
                // Группировка животных по классам
                Map<Class<? extends Animal>, List<Animal>> animalGroups = new ConcurrentHashMap<>();
                for (Animal animal : animals) {
                    animalGroups.computeIfAbsent(animal.getClass(), key -> new CopyOnWriteArrayList<>()).add(animal);
                }
                // Выполнение методов для каждой группы животных
                executionMethods(cells, executorService, animalGroups);
            }
        }
    }
    public static void creationOfPoolTreadLifeCircule(Cell[][] cells) {
        try (ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                10, 10, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())) {
            // Отправка задач на выполнение для всех животных во всех клетках
            executionOfMethodsForAllAnimals(cells, executorService);
            // Ожидание завершения пула потоков
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(TIME_OUT, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                    if (!executorService.awaitTermination(TIME_OUT, TimeUnit.SECONDS)) {
                        System.err.println("Executor service did not terminate");
                    }
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
