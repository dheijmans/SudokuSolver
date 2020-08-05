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

        this.getItems().addAll(solveButton);
    }

    private void handleSolve(ActionEvent event) {
        this.mainView.getSolver().solve();
        this.mainView.draw();
    }

}
