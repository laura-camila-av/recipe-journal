package com.laura.recipejournal.ui;

import com.laura.recipejournal.model.Macros;
import com.laura.recipejournal.model.Recipe;
import com.laura.recipejournal.model.RecipeIngredient;

import java.io.File;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * The recipe detail / edit screen: macros summary + ingredients on the
 * left, cover photo + notes on the right, each independently scrollable.
 * Tap-to-edit for the name and instructions.
 * <p>
 * Extends {@link StackPane} (rather than a plain layout container) so the
 * ingredient add/edit form can float on top of the page content as an
 * overlay, matching the design sketch, without needing a separate window.
 */
public class RecipeDetailScreen extends StackPane {

    public RecipeDetailScreen(Recipe recipe, Stage stage, Runnable onPublish, Runnable onRecipeChanged) {
        VBox mainContent = new VBox();
        mainContent.setPadding(new Insets(10));

        ImageView coverImageView = new ImageView();
        coverImageView.setFitWidth(300);
        coverImageView.setFitHeight(200);
        coverImageView.setPreserveRatio(true);

        String existingCoverPhoto = recipe.getCoverPhoto();
        if (existingCoverPhoto != null) {
            coverImageView.setImage(new Image(existingCoverPhoto));
        }

        Label coverImageLabel = new Label("Cover Image");

        Button chooseImageButton = new Button("Choose Image");

        chooseImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Cover Image");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );

            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                coverImageView.setImage(image);
                recipe.setCoverPhoto(selectedFile.toURI().toString());
            }
        });

        VBox imageBox = new VBox();
        imageBox.getChildren().addAll(coverImageLabel, coverImageView, chooseImageButton);

        List<String> notesPlaceholder = List.of(
            "Notes notes notes xxx xxxxx xxx",
            "Notes notes notes xxx xxxxx xxx",
            "Notes notes notes xxx xxxxx xxx"
        );

        VBox notes = new VBox();
        for (String note : notesPlaceholder) {
            Label noteLabel = new Label("• " + note);
            notes.getChildren().add(noteLabel);
        }

        VBox ingredients = new VBox();

        for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
            IngredientRow row = new IngredientRow(ri, () -> showIngredientForm(recipe, ri, onRecipeChanged));
            ingredients.getChildren().add(row);
        }

        IngredientRow addRow = new IngredientRow(null, () -> showIngredientForm(recipe, null, onRecipeChanged));
        ingredients.getChildren().add(addRow);

        Button publishButton = new Button("Publish");
        publishButton.setOnAction(event -> onPublish.run());

        Label instructionsLabel = new Label(
            recipe.getInstructions().isEmpty() ? "Double click to add instructions." : recipe.getInstructions()
        );

        instructionsLabel.setWrapText(true);

        TextArea instructionsTextArea = new TextArea();
        instructionsTextArea.setVisible(false);
        instructionsTextArea.setWrapText(true);

        StackPane instructionsEditPane = new StackPane();
        instructionsEditPane.setMinHeight(60);
        instructionsEditPane.getChildren().addAll(instructionsLabel, instructionsTextArea);

        instructionsEditPane.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !instructionsTextArea.isVisible()) {
                instructionsTextArea.setText(recipe.getInstructions());
                instructionsTextArea.setVisible(true);
                instructionsLabel.setVisible(false);
                instructionsTextArea.requestFocus();
            }
        });

        instructionsTextArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                event.consume();
                recipe.editInstruction(instructionsTextArea.getText());
                instructionsLabel.setText(
                    recipe.getInstructions().isEmpty() ? "Double click to add instructions." : recipe.getInstructions()
                );
                instructionsTextArea.setVisible(false);
                instructionsLabel.setVisible(true);
            }
        });

        VBox rightColumn = new VBox();
        rightColumn.getChildren().addAll(imageBox, instructionsEditPane);

        Macros totalMacros = recipe.calculateTotalMacros();
        double totalCalories = recipe.calculateTotalCalories();

        HBox macros = new HBox();
        Label protein = new Label("Protein: " + totalMacros.getProtein() + "\t");
        Label carbohydrates = new Label("Carbohydrates: " + totalMacros.getCarbohydrate() + "\t");
        Label fat = new Label("Fat: " + totalMacros.getFat() + "\t");
        Label calories = new Label("Calories: " + totalCalories);
        macros.getChildren().addAll(protein, carbohydrates, fat, calories);

        Label ingredientsHeading = new Label("Ingredients");
        ingredientsHeading.getStyleClass().add("section-heading");

        VBox leftColumn = new VBox();
        leftColumn.setPadding(new Insets(15));
        leftColumn.setSpacing(8);
        leftColumn.getChildren().addAll(macros, ingredientsHeading, ingredients);

        ScrollPane leftScrollPane = new ScrollPane();
        leftScrollPane.setContent(leftColumn);

        ScrollPane rightScrollPane = new ScrollPane();
        rightScrollPane.setContent(rightColumn);

        HBox recipePage = new HBox();
        recipePage.getChildren().addAll(leftScrollPane, rightScrollPane);

        Label nameLabel = new Label(recipe.getName());
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
            recipe.setName(nameTextField.getText());
            nameLabel.setText(recipe.getName());
            nameTextField.setVisible(false);
            nameLabel.setVisible(true);
        });

        mainContent.getChildren().addAll(nameEditPane, recipePage, publishButton);

        getChildren().add(mainContent);
    }

    /**
     * Opens the ingredient add/edit form as an overlay on top of the page.
     * Pass {@code existing} as null to add a new ingredient, or an existing
     * {@link RecipeIngredient} to edit it. Either way, submitting or
     * cancelling removes the overlay; submitting also triggers
     * {@code onRecipeChanged} so the caller can rebuild the screen with the
     * updated data.
     */
    private void showIngredientForm(Recipe recipe, RecipeIngredient existing, Runnable onRecipeChanged) {
        IngredientFormOverlay[] overlayHolder = new IngredientFormOverlay[1];

        Runnable close = () -> getChildren().remove(overlayHolder[0]);

        IngredientFormOverlay overlay = new IngredientFormOverlay(
            recipe,
            existing,
            () -> { close.run(); onRecipeChanged.run(); },
            close
        );

        overlayHolder[0] = overlay;
        getChildren().add(overlay);
    }
}