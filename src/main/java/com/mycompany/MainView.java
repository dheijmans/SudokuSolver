package com.mycompany;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;

public class MainView extends VBox {

    private final Canvas canvas;
    private final Affine affine;
    private final Solver solver;

    public MainView() {
        Button solveButton = new Button("Solve");
        solveButton.setOnAction(this::handleSolve);
        this.canvas = new Canvas(400d, 400d);
        this.getChildren().addAll(solveButton, this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / 10d, this.canvas.getHeight() / 10d);
        this.affine.appendTranslation(0.5d, 0.5d);

        this.solver = new Solver();
        this.solver.grid = new int[][] {
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
    }

    private void handleSolve(ActionEvent actionEvent) {
        this.solver.solve();
        draw();
    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setStroke(Color.BLACK);
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                g.setLineWidth(0.07d);
            } else {
                g.setLineWidth(0.05d);
            }
            g.strokeLine(i, 0d, i, 9d);
            g.strokeLine(0d, i, 9d, i);
        }

        g.setFont(Font.font(0.8d));
        g.setTextAlign(TextAlignment.CENTER);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (this.solver.grid[y][x] != Solver.EMPTY) {
                    g.fillText(String.valueOf(this.solver.grid[y][x]), x + 0.5d, y + 0.8d);
                }
            }
        }
    }
}