package com.javarush.island.voloshanovskyi.island_lokation;

public class Island {

    private final Cell[][] cells;
    public static final int ISLAND_WIDTH = 20;
    public static final int ISLAND_DEPTH = 100;

    public Island() {
        cells = new Cell[ISLAND_DEPTH][ISLAND_WIDTH];
        initializeCells();
    }
    private void initializeCells() {
        for (int row = 0; row < ISLAND_DEPTH; row++) {
            for (int col = 0; col < ISLAND_WIDTH; col++) {
                cells[row][col] = new Cell();
            }
        }
    }
}
