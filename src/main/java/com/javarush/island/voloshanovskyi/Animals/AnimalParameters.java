package com.javarush.island.voloshanovskyi.Animals;

import com.javarush.island.voloshanovskyi.Animals.Herbivore.*;
import com.javarush.island.voloshanovskyi.Animals.Predator.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AnimalParameters {
    static double MAX_WEIGHT;
    static int MAX_SPEED;
    private static int MAX_COUNT;
    private static double MAX_CAN_EAT;

    public AnimalParameters(double MAX_WEIGHT, int MAX_SPEED, int MAX_COUNT, double MAX_CAN_EAT) {
        AnimalParameters.MAX_WEIGHT = MAX_WEIGHT;
        AnimalParameters.MAX_SPEED = MAX_SPEED;
        AnimalParameters.MAX_COUNT = MAX_COUNT;
        AnimalParameters.MAX_CAN_EAT = MAX_CAN_EAT;
    }
    public static final Map<Class<? extends Animal>, Map<Class<? extends Animal>, Double>> preferencesFoodMap = new ConcurrentHashMap<>();
    static {
        preferencesFoodMap.put(Bear.class, new ConcurrentHashMap<>());
        preferencesFoodMap.get(Bear.class).put(Boa.class, 0.8);
        preferencesFoodMap.get(Bear.class).put(Horse.class, 0.4);
        preferencesFoodMap.get(Bear.class).put(Deer.class, 0.8);
        preferencesFoodMap.get(Bear.class).put(Rabbit.class, 0.8);
        preferencesFoodMap.get(Bear.class).put(Mouse.class, 0.9);
        preferencesFoodMap.get(Bear.class).put(Goat.class, 0.7);
        preferencesFoodMap.get(Bear.class).put(Sheep.class, 0.7);
        preferencesFoodMap.get(Bear.class).put(Boar.class, 0.5);
        preferencesFoodMap.get(Bear.class).put(Buffalo.class, 0.2);
        preferencesFoodMap.get(Bear.class).put(Duck.class, 0.1);
        preferencesFoodMap.put(Boa.class, new ConcurrentHashMap<>());
        preferencesFoodMap.get(Boa.class).put(Fox.class, 0.15);
        preferencesFoodMap.get(Boa.class).put(Rabbit.class, 0.2);
        preferencesFoodMap.get(Boa.class).put(Mouse.class, 0.4);
        preferencesFoodMap.get(Boa.class).put(Duck.class, 0.1);
        preferencesFoodMap.put(Boar.class, new ConcurrentHashMap<>());
        preferencesFoodMap.get(Boar.class).put(Mouse.class, 0.5);
        preferencesFoodMap.get(Boar.class).put(Duck.class, 0.1);
        preferencesFoodMap.get(Boar.class).put(Caterpillar.class, 0.9);
        preferencesFoodMap.put(Duck.class, new ConcurrentHashMap<>());
        preferencesFoodMap.get(Duck.class).put(Caterpillar.class, 0.9);
        preferencesFoodMap.put(Eagle.class, new ConcurrentHashMap<>());
        preferencesFoodMap.get(Eagle.class).put(Fox.class, 0.1);
        preferencesFoodMap.get(Eagle.class).put(Rabbit.class, 0.9);
        preferencesFoodMap.get(Eagle.class).put(Mouse.class, 0.9);
        preferencesFoodMap.get(Eagle.class).put(Duck.class, 0.8);
        preferencesFoodMap.put(Fox.class, new ConcurrentHashMap<>());
        preferencesFoodMap.get(Fox.class).put(Rabbit.class, 0.7);
        preferencesFoodMap.get(Fox.class).put(Mouse.class, 0.9);
        preferencesFoodMap.get(Fox.class).put(Duck.class, 0.6);
        preferencesFoodMap.get(Fox.class).put(Caterpillar.class, 0.4);
        preferencesFoodMap.put(Mouse.class, new ConcurrentHashMap<>());
        preferencesFoodMap.get(Mouse.class).put(Caterpillar.class, 0.9);
        preferencesFoodMap.put(Wolf.class, new ConcurrentHashMap<>());
        preferencesFoodMap.get(Wolf.class).put(Horse.class, 0.1);
        preferencesFoodMap.get(Wolf.class).put(Deer.class, 0.15);
        preferencesFoodMap.get(Wolf.class).put(Rabbit.class, 0.6);
        preferencesFoodMap.get(Wolf.class).put(Mouse.class, 0.8);
        preferencesFoodMap.get(Wolf.class).put(Goat.class, 0.6);
        preferencesFoodMap.get(Wolf.class).put(Sheep.class, 0.7);
        preferencesFoodMap.get(Wolf.class).put(Boar.class, 0.15);
        preferencesFoodMap.get(Wolf.class).put(Buffalo.class, 0.1);
        preferencesFoodMap.get(Wolf.class).put(Duck.class, 0.4);
    }
    public static Map<Class<? extends Animal>, Double> animalMaxWeightMap = new ConcurrentHashMap<>() {{
        put(Bear.class, 500.0);
        put(Boa.class, 15.0);
        put(Boar.class, 400.0);
        put(Buffalo.class, 700.0);
        put(Caterpillar.class, 0.01);
        put(Duck.class, 1.0);
        put(Eagle.class, 6.0);
        put(Fox.class, 8.0);
        put(Goat.class, 60.0);
        put(Horse.class, 400.0);
        put(Mouse.class, 0.05);
        put(Rabbit.class, 2.0);
        put(Deer.class, 300.0);
        put(Sheep.class, 70.0);
        put(Wolf.class, 50.0);
    }};
    public static Map<Class<? extends Animal>, Integer> animalMaxInCellMap = new ConcurrentHashMap<>() {{
        put(Bear.class, 5);
        put(Boa.class, 30);
        put(Boar.class, 50);
        put(Buffalo.class, 10);
        put(Caterpillar.class, 1000);
        put(Duck.class, 200);
        put(Eagle.class, 20);
        put(Fox.class, 30);
        put(Goat.class, 140);
        put(Horse.class, 20);
        put(Mouse.class, 500);
        put(Rabbit.class, 150);
        put(Deer.class, 20);
        put(Sheep.class, 140);
        put(Wolf.class, 30);
    }};
    public static Map<Class<? extends Animal>, Integer> animalMaxAgeMap = new ConcurrentHashMap<>() {{
        put(Bear.class, 30);
        put(Boa.class, 30);
        put(Boar.class, 15);
        put(Buffalo.class, 15);
        put(Caterpillar.class, 4);
        put(Duck.class, 10);
        put(Eagle.class, 10);
        put(Fox.class, 15);
        put(Goat.class, 15);
        put(Horse.class, 20);
        put(Mouse.class, 6);
        put(Rabbit.class, 8);
        put(Deer.class, 15);
        put(Sheep.class, 15);
        put(Wolf.class, 20);
    }};
    public static Map<Class<? extends Animal>, Integer> animalMaxSpeedMap = new ConcurrentHashMap<>() {{
        put(Bear.class, 2);
        put(Boa.class, 1);
        put(Boar.class, 2);
        put(Buffalo.class, 3);
        put(Caterpillar.class, 0);
        put(Duck.class, 4);
        put(Eagle.class, 3);
        put(Fox.class, 2);
        put(Goat.class, 3);
        put(Horse.class, 4);
        put(Mouse.class, 1);
        put(Rabbit.class, 2);
        put(Deer.class, 4);
        put(Sheep.class, 3);
        put(Wolf.class, 3);
    }};
    public static Map<Class<? extends Animal>, Double> animalMaxCamEatMap = new ConcurrentHashMap<>() {{
        put(Bear.class, 80.0);
        put(Boa.class, 3.0);
        put(Boar.class, 50.0);
        put(Buffalo.class, 100.0);
        put(Caterpillar.class, 0.0);
        put(Duck.class, 0.15);
        put(Eagle.class, 1.0);
        put(Fox.class, 2.0);
        put(Goat.class, 3.0);
        put(Horse.class, 60.0);
        put(Mouse.class, 0.01);
        put(Rabbit.class, 0.45);
        put(Deer.class, 50.0);
        put(Sheep.class, 15.0);
        put(Wolf.class, 8.0);
    }};
}


