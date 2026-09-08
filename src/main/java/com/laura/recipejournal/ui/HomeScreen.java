package com.laura.recipejournal.ui;

import com.laura.recipejournal.model.Recipe;

import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * The home screen: a title plus a wrapping grid of {@link RecipeTile}s, one
 * per recipe. Clicking a tile invokes {@code onRecipeSelected} with that
 * recipe.
 */
public class HomeScreen extends VBox {

    public HomeScreen(List<Recipe> recipes, Consumer<Recipe> onRecipeSelected) {
        setPadding(new Insets(10));
        setSpacing(10);

        Label title = new Label("Recipes");

        FlowPane recipeGrid = new FlowPane();
        recipeGrid.setHgap(15);
        recipeGrid.setVgap(15);

        for (Recipe recipe : recipes) {
            RecipeTile tile = new RecipeTile(recipe, () -> onRecipeSelected.accept(recipe));
            recipeGrid.getChildren().add(tile);
        }

        ScrollPane scrollPane = new ScrollPane(recipeGrid);
        scrollPane.setFitToWidth(true);

        getChildren().addAll(title, scrollPane);
    }
}