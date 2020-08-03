package com.mycompany;

public class Solver {

    public static final int EMPTY = 0;

    int[][] grid;

    public Solver() {
        this.grid = new int[9][9];
    }

    private void printGrid() {
        for (int y = 0; y < 9; y++) {
            if (y % 3 == 0 && y != 0) {
                System.out.println("— — —   — — —   — — —");
            }
            for (int x = 0; x < 9; x++) {
                if (x % 3 == 0 && x != 0) {
                    System.out.print("| ");
                }
                if (this.grid[y][x] == EMPTY) {
                    if (x == 8) {
                        System.out.println(".");
                    } else {
                        System.out.print(". ");
                    }
                } else {
                    if (x == 8) {
                        System.out.println(this.grid[y][x]);
                    } else {
                        System.out.print(this.grid[y][x] + " ");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Solver solver = new Solver();
        solver.grid = new int[][] {
            {0, 0, 0, 0, 0, 0, 2, 0, 0},
            {0, 8, 0, 0, 0, 7, 0, 9, 0},
            {6, 0, 2, 0, 0, 0, 5, 0, 0},
            {0, 7, 0, 0, 6, 0, 0, 0, 0},
            {0, 0, 0, 9, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 2, 0, 0, 4, 0},
            {0, 0, 5, 0, 0, 0, 6, 0, 3},
            {0, 9, 0, 4, 0, 0, 0, 7, 0},
            {0, 0, 6, 0, 0, 0, 0, 0, 0}
        };
        solver.printGrid();
    }

}
