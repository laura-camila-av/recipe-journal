package com.laura.recipejournal.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * The navigation sidebar shown on every screen. Built once by {@link Router}
 * and reused across screen changes — only the center content of the app's
 * layout is swapped, not the sidebar itself.
 * <p>
 * Icons are plain-text placeholders for now (Home / +); edit and search are
 * not wired up yet and will be added as separate follow-up features.
 */
public class Sidebar extends VBox {

    public Sidebar(Runnable onHome, Runnable onAdd) {
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(10));
        setPrefWidth(80);

        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> onHome.run());

        Button addButton = new Button("+");
        addButton.setOnAction(event -> onAdd.run());

        getChildren().addAll(homeButton, addButton);
    }
}