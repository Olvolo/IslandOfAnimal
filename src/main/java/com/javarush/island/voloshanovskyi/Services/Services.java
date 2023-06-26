package com.javarush.island.voloshanovskyi.Services;

import com.javarush.island.voloshanovskyi.Animals.Animal;
import com.javarush.island.voloshanovskyi.island_lokation.Cell;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import static com.javarush.island.ParametersLifeCircule.CirculeParameters.TIME_LIFE_CIRCULE;
import static com.javarush.island.voloshanovskyi.Animals.AnimalParameters.*;
import static com.javarush.island.voloshanovskyi.Services.ParametersOfMethods.MAX_AVERAGE_WEIGHT;
import static com.javarush.island.voloshanovskyi.Services.ParametersOfMethods.MIN_AVERAGE_WEIGHT;
import static com.javarush.island.voloshanovskyi.island_lokation.Grass.MAX_QUANTITY_GRASS;
import static com.javarush.island.voloshanovskyi.island_lokation.Island.ISLAND_DEPTH;
import static com.javarush.island.voloshanovskyi.island_lokation.Island.ISLAND_WIDTH;

public class Services {
    public static void settlingTheIslandWithAnimalsAndGrass(Cell[][] cells, Random random) {
        for (int i = 0; i < ISLAND_WIDTH; i++) {
            for (int j = 0; j < ISLAND_DEPTH; j++) {
                cells[i][j] = new Cell();
                cells[i][j].setGrass(random.nextInt((MAX_QUANTITY_GRASS + 1)));
                for (Map.Entry<Class<? extends Animal>, Integer> entry : animalMaxInCellMap.entrySet()) {
                    Class<? extends Animal> animalClass = entry.getKey();
                    int maxInCell = entry.getValue();
                    double maxWeight = animalMaxWeightMap.get(entry.getKey());
                    for (int k = 0; k < random.nextInt(maxInCell + 1); k++) {
                        double weight = Math.round((random.nextDouble() * (MIN_AVERAGE_WEIGHT * maxWeight) + MAX_AVERAGE_WEIGHT * maxWeight) * 100.0) / 100.0;
                        Animal animal = null;
                        try {
                            animal = animalClass.getDeclaredConstructor(double.class).newInstance(weight);
                            animal.setWeight(weight);
                            animal.setAge(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Cell cell = cells[i][j];
                        cell.addAnimal(animal);
                        if (animal != null) {
                            animal.setCurrentCell(cell);
                        }
                    }
                }
            }
        }
    }
    public static void printAnimalConditions(Cell[][] cells) {
        for (int i = 0; i < ISLAND_WIDTH; i++) {
            for (int j = 0; j < ISLAND_DEPTH; j++) {
                Cell cell = cells[i][j];
                List<Animal> animals = cell.getAnimals();
                double totalWeight = 0.0;
                Map<Class<? extends Animal>, Integer> animalCountMap = new ConcurrentHashMap<>();
                System.out.println("В ячейке " + i + "-" + j);
                for (Animal animal : animals) {
                    animalCountMap.put(animal.getClass(), animalCountMap.getOrDefault(animal.getClass(), 0) + 1);
                    totalWeight += animal.getWeight();
                }
                long roundedTotalWeight = Math.round(totalWeight);
                System.out.println("Ячейка [" + i + "][" + j + "]:");
                for (Map.Entry<Class<? extends Animal>, Integer> entry : animalCountMap.entrySet()) {
                    Class<? extends Animal> animalClass = entry.getKey();
                    int count = entry.getValue();
                    System.out.println(animalClass.getSimpleName() + ": " + count + " экземпляров");
                }
                System.out.println("Количество животных: " + animals.size());
                System.out.println("Количество травы: " + cell.getGrass());
                System.out.println("Общий вес животных: " + roundedTotalWeight);
                System.out.println("________________________________________________________________");
            }
        }
    }

    public static void executionMethods(Cell[][] cells, ThreadPoolExecutor executorService, Map<Class<? extends Animal>, List<Animal>> animalGroups) {
        for (List<Animal> animalGroup : animalGroups.values()) {
            executorService.submit(() -> {
                long startTime = System.currentTimeMillis(); // Запоминаем начальное время

                while (System.currentTimeMillis() - startTime < TIME_LIFE_CIRCULE) { // Пока прошло менее 60 секунд

                    for (Animal animal : animalGroup) {
                        synchronized (animal) {
                            animal.eat(preferencesFoodMap, animalMaxWeightMap, animalMaxCamEatMap);
                        }
                    }
                    for (Animal animal : animalGroup) {
                        synchronized (animal) {
                            animal.grow(animalMaxAgeMap);
                        }
                    }
                    for (Animal animal : animalGroup) {
                        synchronized (animal) {
                            animal.procreate(animalMaxAgeMap, animalMaxWeightMap);
                        }
                    }
                    for (Animal animal : animalGroup) {
                        synchronized (cells) {
                            animal.move(animalMaxSpeedMap, animalMaxInCellMap, cells);
                        }
                    }
                }
                for (Animal animal : animalGroup) {
                    synchronized (animal) {
                        System.out.println(animal.getClass().getSimpleName() + " " + animal.getWeight() + " " + animal.getAge());
                    }
                }
            });
        }
    }
}
