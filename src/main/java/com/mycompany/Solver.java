package com.mycompany;

public class Solver {

    public static final int EMPTY = 0;

    public int[][] grid;

    public Solver() {
        this.grid = new int[9][9];
    }

    public boolean solve() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (this.grid[y][x] == EMPTY) {
                    for (int n = 1; n < 10; n++) {
                        if (isValid(x, y, n)) {
                            this.grid[y][x] = n;
                            if (solve()) {
                                return true;
                            }
                            this.grid[y][x] = EMPTY;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGridValid() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (!isValid(x, y, this.grid[y][x]) && this.grid[y][x] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int x, int y, int n) {
        for (int i = 0; i < 9; i++) {
            if (n == grid[y][i] && i != x) {
                return false;
            }
        }
        for (int j = 0; j < 9; j++) {
            if (n == grid[j][x] && j != y) {
                return false;
            }
        }
        int blockX = Math.floorDiv(x, 3) * 3;
        int blockY = Math.floorDiv(y, 3) * 3;
        for (int i = blockX; i < blockX + 3; i++) {
            for (int j = blockY; j < blockY + 3; j++) {
                if (n == grid[j][i] && i != x && j != y) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printGrid() {
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

}
