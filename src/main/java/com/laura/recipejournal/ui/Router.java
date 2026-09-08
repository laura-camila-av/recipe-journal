package com.laura.recipejournal.ui;

import com.laura.recipejournal.RecipeStorage;
import com.laura.recipejournal.model.Recipe;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Owns navigation between screens. This is the one class in the UI layer
 * that is allowed to know about every screen and wire them together — every
 * other UI class only knows about callbacks it was handed, not about
 * {@code Router} or its sibling screens.
 * <p>
 * The window layout is a single {@link BorderPane}: {@link Sidebar} is
 * docked on the left and built once; navigating only ever replaces the
 * center content, so the sidebar never gets rebuilt.
 */
public class Router {

    private final Stage stage;
    private final List<Recipe> recipes;
    private final String recipesFilePath;
    private final BorderPane root;

    public Router(Stage stage, List<Recipe> recipes, String recipesFilePath) {
        this.stage = stage;
        this.recipes = recipes;
        this.recipesFilePath = recipesFilePath;

        Sidebar sidebar = new Sidebar(this::showHome, this::showNewRecipe);

        this.root = new BorderPane();
        this.root.setLeft(sidebar);
    }

    /** The root node to place in the app's {@link javafx.scene.Scene}. */
    public BorderPane getRoot() {
        return root;
    }

    public void showHome() {
        HomeScreen homeScreen = new HomeScreen(recipes, this::showRecipe);
        root.setCenter(homeScreen);
    }

    public void showRecipe(Recipe recipe) {
        RecipeDetailScreen detailScreen = new RecipeDetailScreen(recipe, stage, this::publish);
        root.setCenter(detailScreen);
    }

    /** Creates a blank recipe, adds it to the shared list, and opens it. */
    public void showNewRecipe() {
        Recipe blankRecipe = new Recipe("Untitled Recipe", LocalDate.now().toString());
        recipes.add(blankRecipe);
        showRecipe(blankRecipe);
    }

    private void publish() {
        RecipeStorage.save(recipes, recipesFilePath);
    }
}