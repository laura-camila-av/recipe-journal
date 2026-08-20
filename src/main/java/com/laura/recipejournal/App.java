package com.laura.recipejournal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.util.List;

import javafx.scene.image.ImageView;

import com.laura.recipejournal.model.Ingredient;
import com.laura.recipejournal.model.Macros;
import com.laura.recipejournal.model.Quantity;
import com.laura.recipejournal.model.Recipe;
import com.laura.recipejournal.model.RecipeIngredient;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        //NEWWWW
        ImageView coverImageView = new ImageView();
        coverImageView.setFitWidth(300);
        coverImageView.setFitHeight(200);
        coverImageView.setPreserveRatio(true);

        Label coverImageLabel = new Label("Cover Image");

        VBox imageBox = new VBox();
        imageBox.getChildren().addAll(coverImageLabel, coverImageView);

        List<String> porridgeNotes = List.of(
            "Notes notes notes xxx xxxxx xxx",
            "Notes notes notes xxx xxxxx xxx",
            "Notes notes notes xxx xxxxx xxx"
        );

        VBox notes = new VBox();
        for (String note : porridgeNotes) {
            Label noteLabel = new Label("• " + note);
            notes.getChildren().add(noteLabel);
        }

        VBox rightColumn = new VBox();
        rightColumn.getChildren().addAll(imageBox, notes);
        //NEWWWW

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

        Label instructionsLabel = new Label(porridge.getInstructions());
        TextField instructionsTextField = new TextField();
        instructionsTextField.setVisible(false);

        StackPane instructionsEditPane = new StackPane();
        instructionsEditPane.getChildren().addAll(instructionsLabel, instructionsTextField);

        instructionsLabel.setOnMouseClicked(event -> {
        instructionsTextField.setText(instructionsLabel.getText());
        instructionsTextField.setVisible(true);
        instructionsLabel.setVisible(false);
        });

        instructionsTextField.setOnAction(event -> {
            porridge.editInstruction(instructionsTextField.getText());
            instructionsLabel.setText(porridge.getInstructions());
            instructionsTextField.setVisible(false);
            instructionsLabel.setVisible(true);
        });

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
        leftColumn.getChildren().addAll(macros, instructionsEditPane, ingredients);

        ScrollPane leftScrollPane = new ScrollPane();
        leftScrollPane.setContent(leftColumn);

        ScrollPane rightScrollPane = new ScrollPane();
        rightScrollPane.setContent(rightColumn);

        HBox recipePage = new HBox();
        recipePage.getChildren().addAll(leftScrollPane, rightScrollPane);

        Label nameLabel = new Label(porridge.getName());
        TextField nameTextField = new TextField();
        nameTextField.setVisible(false);

        StackPane nameEditPane = new StackPane();
        nameEditPane.getChildren().addAll(nameLabel, nameTextField);

        nameLabel.setOnMouseClicked(event -> {
            nameTextField.setText(nameLabel.getText());
            nameTextField.setVisible(true);
            nameLabel.setVisible(false);
        });

        nameTextField.setOnAction(event -> {
            nameLabel.setText(nameTextField.getText());
            nameTextField.setVisible(false);
            nameLabel.setVisible(true);
        });

        VBox appRoot = new VBox();
        appRoot.getChildren().addAll(nameEditPane, recipePage);

        Scene scene = new Scene(appRoot, 900, 500);
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