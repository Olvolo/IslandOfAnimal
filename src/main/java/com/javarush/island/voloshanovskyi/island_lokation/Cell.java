package com.javarush.island.voloshanovskyi.island_lokation;

import com.javarush.island.voloshanovskyi.Animals.Animal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.javarush.island.voloshanovskyi.island_lokation.Grass.MAX_QUANTITY_GRASS;

public class Cell {
    @Override
    public String toString() {
        return super.toString();
    }
    private final List<Animal> animals;
    public int getGrass() {
        return grass;
    }
    public void setGrass(int grass) {
        this.grass = grass;
    }
    private int grass;
    public Cell() {
        animals = new CopyOnWriteArrayList<>();
        this.grass = 0;
    }
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }
    public List<Animal> getAnimals() {
        return animals;
    }
    public void incrementGrassCount() {
        if(grass < MAX_QUANTITY_GRASS) grass++;
    }
}
