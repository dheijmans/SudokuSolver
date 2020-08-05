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
        this.mainView.draw();
    }

    private void handleSolve(ActionEvent event) {
        this.mainView.getSolver().solve();
        this.mainView.draw();
    }

}
