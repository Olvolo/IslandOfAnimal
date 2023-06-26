package com.javarush.island.voloshanovskyi;

import java.util.Random;
import com.javarush.island.voloshanovskyi.island_lokation.Cell;
import static com.javarush.island.voloshanovskyi.Services.GlobalProcesses.creationOfPoolTreadLifeCircule;
import static com.javarush.island.voloshanovskyi.Services.Services.*;
import static com.javarush.island.voloshanovskyi.island_lokation.Island.ISLAND_DEPTH;
import static com.javarush.island.voloshanovskyi.island_lokation.Island.ISLAND_WIDTH;

public class Main {

    public static void main(String[] args) {
        // Заселяем остров поклеточно рандомным количеством животных с рандомным весом и травой
        Cell[][] cells = new Cell[ISLAND_WIDTH][ISLAND_DEPTH];
        Random random = new Random();
        settlingTheIslandWithAnimalsAndGrass(cells, random);

        // Вывод данных о животных в ячейках
        printAnimalConditions(cells);

        // Создание пула потоков с ограничением по времени исполнения и запуск всех методов
        creationOfPoolTreadLifeCircule(cells);

        // Вывод данных о животных в ячейках после обработки
        printAnimalConditions(cells);
    }
}