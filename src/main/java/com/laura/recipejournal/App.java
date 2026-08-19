package com.laura.recipejournal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import javax.swing.text.TableView;

import com.laura.recipejournal.model.Ingredient;
import com.laura.recipejournal.model.Macros;
import com.laura.recipejournal.model.Quantity;
import com.laura.recipejournal.model.Recipe;
import com.laura.recipejournal.model.RecipeIngredient;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        Macros bananaMacros = new Macros(0.3, 1.3, 27);
        Ingredient banana = new Ingredient("Banana", bananaMacros);
        Quantity bananaAmount = new Quantity(120, "g");
        RecipeIngredient bananaEntry = new RecipeIngredient(banana, bananaAmount);

        Macros oatsMacros = new Macros(7, 13, 66);
        Ingredient oats = new Ingredient("Oats", oatsMacros);
        Quantity oatsAmount = new Quantity(50, "g");
        RecipeIngredient oatsEntry = new RecipeIngredient(oats, oatsAmount);

        Macros milkMacros = new Macros(3, 3, 5);
        Ingredient milk = new Ingredient("Milk", milkMacros);
        Quantity milkAmount = new Quantity(150, "ml");
        RecipeIngredient milkEntry = new RecipeIngredient(milk, milkAmount);

        VBox ingredients = new VBox();
        List<RecipeIngredient> porridgeIngredients = List.of(bananaEntry, oatsEntry, milkEntry);


        for (RecipeIngredient ri : porridgeIngredients) {
            Label label = new Label(ri.toString());
            ingredients.getChildren().add(label);
        }

        Recipe porridge = new Recipe("Porridge", "2026-08-19");
        porridge.addRecipeIngredient(bananaEntry);
        porridge.addRecipeIngredient(oatsEntry);
        porridge.addRecipeIngredient(milkEntry);

        Macros totalMacros = porridge.calculateTotalMacros();

        HBox macros = new HBox();
        
        Label protein = new Label("Protein: " + totalMacros.getProtein() + "\t");
        Label carbohydrates = new Label("Carbohydrates: " + totalMacros.getCarbohydrate() + "\t");
        Label fat = new Label("Fat: " + totalMacros.getFat() + "\t");

        macros.getChildren().addAll(
            protein,
            carbohydrates,
            fat
        );

        VBox leftColumn = new VBox();
        leftColumn.getChildren().addAll(macros, ingredients);
        
        Scene scene = new Scene(leftColumn, 600, 400);
        primaryStage.setTitle("Recipe Journal");
        primaryStage.setScene(scene);
        primaryStage.show();

    
    }

    public static void main(String[] args) {
        launch(args);
    }
}
//stage = top level container
//scene is added to stage = drawing surface for graphical content
//scene