package com.mycompany;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private final Canvas canvas;
    private final Affine affine;
    private final Solver solver;

    private Point2D markedBox;
    private Point2D selectedBox;
    private boolean editing;

    private boolean[][] unsolvedGrid;
    public static final boolean UNSOLVED = true;
    public static final boolean SOLVED = false;
    private String message;

    public MainView() {
        Toolbar toolbar = new Toolbar(this);

        this.canvas = new Canvas(400d, 440d);
        this.canvas.setOnMouseMoved(this::handleMarkBox);
        this.canvas.setOnMouseClicked(this::handleSelectBox);

        this.getChildren().addAll(toolbar, this.canvas);

        this.setOnKeyTyped(this::handleChangeBox);
        this.setOnKeyReleased(this::handleClearBox);

        this.editing = true;

        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / 10d, this.canvas.getHeight() / 11d);
        this.affine.appendTranslation(0.5d, 0.5d);

        this.solver = new Solver();
    }

    private void handleClearBox(KeyEvent event) {
        if (this.selectedBox != null && this.editing) {
            KeyCode input = event.getCode();
            if (input.equals(KeyCode.BACK_SPACE) || input.equals(KeyCode.DELETE)) {
                int x = (int) this.selectedBox.getX();
                int y = (int) this.selectedBox.getY();
                this.solver.grid[y][x] = Solver.EMPTY;
                draw();
            }
        }
    }

    private void handleChangeBox(KeyEvent event) {
        if (this.selectedBox != null && this.editing) {
            String input = event.getCharacter();
            if (Character.isDigit(input.charAt(0)) && input.charAt(0) != '0') {
                int x = (int) this.selectedBox.getX();
                int y = (int) this.selectedBox.getY();
                this.solver.grid[y][x] = Integer.parseInt(input);
                draw();
            }
        }
    }

    private void handleSelectBox(MouseEvent event) {
        if (this.markedBox != null) {
            this.selectedBox = this.markedBox;
        } else {
            this.selectedBox = null;
        }
        draw();
    }

    private void handleMarkBox(MouseEvent event) {
        try {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Point2D box = this.affine.inverseTransform(mouseX, mouseY);
            if (box.getX() >= 0 && box.getX() < 9 && box.getY() >= 0 && box.getY() < 9) {
                this.markedBox = new Point2D((int) box.getX(), (int) box.getY());
            } else {
                this.markedBox = null;
            }
            draw();
        } catch (NonInvertibleTransformException e) {
            System.out.println("Could not invert transform");
        }
    }

    public void draw() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (markedBox != null) {
            gc.setFill(Color.LIGHTGREY);
            gc.fillRect(this.markedBox.getX(), this.markedBox.getY(), 1, 1);
        }

        if (selectedBox != null) {
            gc.setFill(Color.DARKGREY);
            gc.fillRect(this.selectedBox.getX(), this.selectedBox.getY(), 1, 1);
        }

        gc.setStroke(Color.BLACK);
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                gc.setLineWidth(0.075d);
            } else {
                gc.setLineWidth(0.05d);
            }
            gc.strokeLine(i, 0d, i, 9d);
            gc.strokeLine(0d, i, 9d, i);
        }

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(0.75d));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (this.solver.grid[y][x] != Solver.EMPTY) {
                    if (this.unsolvedGrid != null) {
                        if (this.unsolvedGrid[y][x] == UNSOLVED) {
                            gc.setFill(Color.ROYALBLUE);
                        } else {
                            gc.setFill(Color.BLACK);
                        }
                    }
                    gc.fillText(String.valueOf(this.solver.grid[y][x]), x + 0.5d, y + 0.5d);
                }
            }
        }

        gc.setFill(Color.CRIMSON);
        gc.setFont(Font.font(0.45d));
        if (this.message != null) {
            gc.fillText(this.message, 4.5d, 9.4d);
        }
    }

    public Solver getSolver() {
        return this.solver;
    }

    public void setSelectedBox(Point2D selectedBox) {
        this.selectedBox = selectedBox;
    }

    public void setUnsolvedGrid() {
        this.unsolvedGrid = new boolean[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (this.solver.grid[y][x] == Solver.EMPTY) {
                    this.unsolvedGrid[y][x] = UNSOLVED;
                } else {
                    this.unsolvedGrid[y][x] = SOLVED;
                }
            }
        }
    }

    public void resetUnsolvedGrid() {
        this.unsolvedGrid = null;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
