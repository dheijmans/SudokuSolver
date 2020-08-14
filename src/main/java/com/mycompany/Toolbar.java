package com.mycompany;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private final MainView mainView;

    public Toolbar(MainView mainView) {
        this.mainView = mainView;

        Button solveButton = new Button("Solve");
        solveButton.setOnAction(this::handleSolve);
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(this::handleClear);

        this.getItems().addAll(solveButton, clearButton);
    }

    private void handleClear(ActionEvent event) {
        this.mainView.getSolver().grid = new int[9][9];
        this.mainView.setSelectedBox(null);
        this.mainView.resetUnsolvedGrid();
        this.mainView.setEditing(true);
        this.mainView.setMessage(null);
        this.mainView.draw();
    }

    private void handleSolve(ActionEvent event) {
        if (this.mainView.isEditing()) {
            this.mainView.setSelectedBox(null);
            if (this.mainView.getSolver().isGridValid()) {
                this.mainView.setUnsolvedGrid();
                if (!this.mainView.getSolver().solve()) {
                    this.mainView.setMessage("Grid has no solution!");
                } else {
                    this.mainView.setEditing(false);
                }
            } else {
                this.mainView.setMessage("Grid is invalid!");
            }
            this.mainView.draw();
        }
    }

}
