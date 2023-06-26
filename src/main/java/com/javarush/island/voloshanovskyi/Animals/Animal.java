package com.javarush.island.voloshanovskyi.Animals;

import com.javarush.island.voloshanovskyi.Animals.Herbivore.Herbivore;
import com.javarush.island.voloshanovskyi.island_lokation.Cell;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.javarush.island.voloshanovskyi.Animals.AnimalParameters.animalMaxInCellMap;


public abstract class Animal {
    private Cell currentCell;
    private int currentIndexI;
    private int currentIndexJ;
    private int age = 0;
    private double weight;
    public Animal() {
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Animal(double weight) {
        this.weight = weight;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void eat(Map<Class<? extends Animal>, Map<Class<? extends Animal>, Double>> preferencesFoodMap,
                    Map<Class<? extends Animal>, Double> animalMaxWeightMap,
                    Map<Class<? extends Animal>, Double> animalMaxCamEatMap) {

        Class<? extends Animal> predatorClass = getClass();
        Map<Class<? extends Animal>, Double> preyPreferences = preferencesFoodMap.getOrDefault(predatorClass, new ConcurrentHashMap<>());
        List<Animal> potentialPreys = getCurrentCell().getAnimals().stream()
                .filter(animal -> preyPreferences.containsKey(animal.getClass()))
                .toList();

        if (!potentialPreys.isEmpty()) {
            // Вариант 1: Есть доступные жертвы, основанные на предпочтениях питания
            for (Animal prey : potentialPreys) {
                double probability = preyPreferences.getOrDefault(prey.getClass(), 0.0);
                if (Math.random() <= probability) {
                    // Процесс поедания жертвы
                    double predatorWeight = getWeight();
                    double preyWeight = prey.getWeight();
                    double maxPredatorWeight = animalMaxWeightMap.getOrDefault(predatorClass, 0.0);
                    double weightDifference = maxPredatorWeight - predatorWeight;

                    if (weightDifference <= 0) {
                        // Хищник уже достиг максимального веса
                        break;
                    }

                    double consumedWeight = Math.min(preyWeight, weightDifference);
                    double newPredatorWeight = predatorWeight + consumedWeight;
                    double newPreyWeight = Math.max(preyWeight - consumedWeight, 0.0);

                    if (newPredatorWeight > 0) {
                        setWeight(newPredatorWeight);
                    } else {
                        // Хищник достиг отрицательного веса, удаляем его из списка животных
                        getCurrentCell().getAnimals().remove(this);
                        break;
                    }

                    if (newPreyWeight <= 0.5 * preyWeight) {
                        getCurrentCell().getAnimals().remove(prey);
                    } else {
                        prey.setWeight(newPreyWeight);
                    }

                    System.out.println("Животное " + getClass().getSimpleName() + " съело " + prey.getClass().getSimpleName() +
                            " и теперь весит " + getWeight());
                }
            }
        }
        // Вариант 2: Животное является травоядным (наследником класса Herbivore) и может есть траву
        if (this instanceof Herbivore) {
            double maxGrassAmount = animalMaxCamEatMap.getOrDefault(predatorClass, 0.0);
            double consumedGrassAmount = Math.min(maxGrassAmount, getCurrentCell().getGrass());
            if (consumedGrassAmount > 0) {
                double predatorWeight = getWeight();
                double maxPredatorWeight = animalMaxWeightMap.getOrDefault(predatorClass, 0.0);
                double newPredatorWeight = Math.min(predatorWeight + consumedGrassAmount, maxPredatorWeight);
                // Проверяем оставшееся количество травы в ячейке
                if (getCurrentCell().getGrass() - consumedGrassAmount < 10) {
                    consumedGrassAmount = getCurrentCell().getGrass() - 10;
                    newPredatorWeight = Math.min(predatorWeight + consumedGrassAmount, maxPredatorWeight);
                    getCurrentCell().setGrass(10);
                } else {
                    getCurrentCell().setGrass((int) (getCurrentCell().getGrass() - consumedGrassAmount));
                }
                setWeight(newPredatorWeight);
                System.out.println("Животное " + getClass().getSimpleName() + " съело траву и теперь весит " + getWeight());
            }
        }
        //  Если нет доступных жертв и травы, животное продолжает быть голодным
        //  System.out.println("Животное " + getClass().getSimpleName() + " осталось голодным");
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public void procreate(Map<Class<? extends Animal>, Integer> animalMaxAgeMap, Map<Class<? extends Animal>, Double> animalMaxWeightMap) {
        double ageThreshold = 0.2 * animalMaxAgeMap.get(getClass());
        double weightThreshold = 0.5 * animalMaxWeightMap.get(getClass());
        double offspringWeight = 0.1 * animalMaxWeightMap.get(getClass());
        List<Animal> animals = currentCell.getAnimals();
        int count = 0;
        for (Animal animal : animals) {
            if (this.getClass().equals(animal.getClass())) {
                count++;
            }
        }
        int numberOfOffspring = count / 2;
        // Проверка на максимальное количество особей в ячейке
        int maxInCell = animalMaxInCellMap.getOrDefault(getClass(), 0);
        if (count + numberOfOffspring <= maxInCell) {
            if (age > ageThreshold && weight > weightThreshold && numberOfOffspring > 1) {
                try {
                    Constructor<? extends Animal> constructor = getClass().getDeclaredConstructor(double.class);
                    for (int i = 0; i < numberOfOffspring; i++) {
                        // Проверка на максимальное количество особей в ячейке для каждого потомка
                        if (count + i + 1 <= maxInCell) {
                            Animal offspring = constructor.newInstance(offspringWeight);
                            offspring.setAge(0);
                            if (currentCell != null) {
                                currentCell.addAnimal(offspring);
                                offspring.setCurrentCell(currentCell);
                                System.out.println("Животное " + offspring.getClass().getSimpleName() + " размножилось");
                            }
                        } else {
                            // Если превышено максимальное количество особей, выходим из цикла
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void grow(Map<Class<? extends Animal>, Integer> animalMaxAgeMap) {
        if (age < animalMaxAgeMap.get(getClass())) {
            age++;
            System.out.println("Подросло " + getClass().getSimpleName());
        } else {
            // Животное "умирает" - удаляем его из списка в своей ячейке
            if (currentCell != null) {
                currentCell.removeAnimal(this);
            }
        }
    }

    public void move(Map<Class<? extends Animal>, Integer> animalMaxSpeedMap,
                     Map<Class<? extends Animal>, Integer> animalMaxInCellMap,
                     Cell[][] cells) {
        int maxSpeed = animalMaxSpeedMap.getOrDefault(this.getClass(), 0);
        int maxInCell = animalMaxInCellMap.getOrDefault(this.getClass(), 0);
        // Определение размеров массива клеток
        int numCellsI = cells.length;
        int numCellsJ = cells[0].length;
        // Генерация случайных смещений для перемещения
        int moveI = (int) (Math.random() * (2 * maxSpeed + 1)) - maxSpeed;
        int moveJ = (int) (Math.random() * (2 * maxSpeed + 1)) - maxSpeed;
        // Вычисление новых индексов клетки
        int newI = currentIndexI + moveI;
        int newJ = currentIndexJ + moveJ;
        // Обертывание индексов клетки в пределах размеров массива
        newI = (newI + numCellsI) % numCellsI;
        newJ = (newJ + numCellsJ) % numCellsJ;
        // Получение новой клетки и проверка доступности
        Cell newCell = cells[newI][newJ];
        if (newCell != null && newCell.getAnimals().size() < maxInCell) {
            // Перемещение животного в новую клетку
            currentCell.removeAnimal(this);
            newCell.addAnimal(this);
            currentCell = newCell;
            currentIndexI = newI;
            currentIndexJ = newJ;
            System.out.println("Животное " + getClass().getSimpleName() + " убежало из своей клетки, кто-нибудь видел, куда оно спряталось? Вдруг оно голодное?");
        }
    }
}
