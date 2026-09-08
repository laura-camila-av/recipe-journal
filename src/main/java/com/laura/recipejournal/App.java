package com.laura.recipejournal;

import com.laura.recipejournal.model.Recipe;
import com.laura.recipejournal.ui.Router;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final String RECIPES_FILE = "recipes.json";

    @Override
    public void start(Stage primaryStage) {
        List<Recipe> recipes = RecipeStorage.load(RECIPES_FILE);

        Router router = new Router(primaryStage, recipes, RECIPES_FILE);
        router.showHome();

        Scene scene = new Scene(router.getRoot(), 900, 500);
        scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        primaryStage.setTitle("Recipe Journal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}