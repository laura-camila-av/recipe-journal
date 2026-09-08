package com.laura.recipejournal.ui;

import com.laura.recipejournal.model.Ingredient;
import com.laura.recipejournal.model.Macros;
import com.laura.recipejournal.model.Quantity;
import com.laura.recipejournal.model.Recipe;
import com.laura.recipejournal.model.RecipeIngredient;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

/**
 * The floating "add/edit ingredient" form. Pass {@code existing} as
 * {@code null} to add a new ingredient to {@code recipe}, or pass an
 * existing {@link RecipeIngredient} to edit it in place — the same form is
 * reused for both, pre-filled in the edit case.
 * <p>
 * On submit, changes are written directly onto the model (either mutating
 * {@code existing}, or constructing and adding a new
 * {@link RecipeIngredient} to {@code recipe}), and {@code onSubmitted} is
 * invoked so the caller can refresh the screen and close the overlay.
 * {@code onCancel} is invoked if the user backs out via the X button
 * instead, with no changes made.
 */
public class IngredientFormOverlay extends VBox {

    private static final String[] UNITS = { "g", "ml", "tbsp", "tsp", "cup", "unit" };

    public IngredientFormOverlay(Recipe recipe, RecipeIngredient existing,
                                  Runnable onSubmitted, Runnable onCancel) {
        setSpacing(10);
        setPadding(new Insets(15));
        setMaxWidth(420);
        setMaxHeight(Region.USE_PREF_SIZE);
        getStyleClass().add("ingredient-form");

        Button closeButton = new Button("X");
        closeButton.setOnAction(event -> onCancel.run());

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.getChildren().add(closeButton);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter ingredient");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Qty");
        quantityField.setPrefWidth(60);

        ComboBox<String> unitCombo = new ComboBox<>();
        unitCombo.getItems().addAll(UNITS);

        HBox nameRow = new HBox(10, nameField, quantityField, unitCombo);
        HBox.setHgrow(nameField, javafx.scene.layout.Priority.ALWAYS);

        TextField proteinField = new TextField();
        TextField carbsField = new TextField();
        TextField fatField = new TextField();
        TextField caloriesField = new TextField();
        caloriesField.setEditable(false);
        caloriesField.setFocusTraversable(false);

        VBox proteinBox = labeledField("Protein", proteinField);
        VBox carbsBox = labeledField("Carbs", carbsField);
        VBox fatBox = labeledField("Fat", fatField);
        VBox caloriesBox = labeledField("Calories", caloriesField);

        HBox macroRow = new HBox(10, proteinBox, carbsBox, fatBox, caloriesBox);

        Button addButton = new Button("Add");
        HBox addRow = new HBox(addButton);
        addRow.setAlignment(Pos.CENTER_RIGHT);

        // Pre-fill fields when editing an existing ingredient.
        if (existing != null) {
            nameField.setText(existing.getIngredient().getName());
            quantityField.setText(String.valueOf(existing.getQuantity().getAmount()));
            unitCombo.setValue(existing.getQuantity().getUnit());
            Macros existingMacros = existing.getIngredient().getMacros();
            proteinField.setText(String.valueOf(existingMacros.getProtein()));
            carbsField.setText(String.valueOf(existingMacros.getCarbohydrate()));
            fatField.setText(String.valueOf(existingMacros.getFat()));
            addButton.setText("Save");
        }

        // Live calorie preview + Add-button validation, recalculated on
        // every keystroke in the macro fields.
        Runnable recalculate = () -> {
            Double protein = parsePositiveDouble(proteinField.getText());
            Double carbs = parsePositiveDouble(carbsField.getText());
            Double fat = parsePositiveDouble(fatField.getText());

            if (protein != null && carbs != null && fat != null) {
                Macros previewMacros = new Macros(fat, protein, carbs);
                caloriesField.setText(String.valueOf(previewMacros.calculateCaloriesPerUnit()));
            } else {
                caloriesField.setText("");
            }

            boolean nameValid = !nameField.getText().trim().isEmpty();
            boolean quantityValid = parsePositiveDouble(quantityField.getText()) != null;
            boolean unitValid = unitCombo.getValue() != null;
            boolean macrosValid = protein != null && carbs != null && fat != null;

            addButton.setDisable(!(nameValid && quantityValid && unitValid && macrosValid));
        };

        nameField.textProperty().addListener((obs, oldVal, newVal) -> recalculate.run());
        quantityField.textProperty().addListener((obs, oldVal, newVal) -> recalculate.run());
        unitCombo.valueProperty().addListener((obs, oldVal, newVal) -> recalculate.run());
        proteinField.textProperty().addListener((obs, oldVal, newVal) -> recalculate.run());
        carbsField.textProperty().addListener((obs, oldVal, newVal) -> recalculate.run());
        fatField.textProperty().addListener((obs, oldVal, newVal) -> recalculate.run());

        recalculate.run(); // set initial state (Add disabled unless editing with valid data)

        addButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            double quantityAmount = Double.parseDouble(quantityField.getText());
            String unit = unitCombo.getValue();
            double protein = Double.parseDouble(proteinField.getText());
            double carbs = Double.parseDouble(carbsField.getText());
            double fat = Double.parseDouble(fatField.getText());

            Macros macros = new Macros(fat, protein, carbs);

            if (existing != null) {
                existing.getIngredient().setName(name);
                existing.getIngredient().setMacros(macros);
                existing.getQuantity().setAmount(quantityAmount);
                existing.getQuantity().setUnit(unit);
            } else {
                Ingredient ingredient = new Ingredient(name, macros);
                Quantity quantity = new Quantity(quantityAmount, unit);
                recipe.addRecipeIngredient(new RecipeIngredient(ingredient, quantity));
            }

            onSubmitted.run();
        });

        getChildren().addAll(topBar, nameRow, macroRow, addRow);
    }

    private VBox labeledField(String labelText, TextField field) {
        field.setPrefWidth(70);
        javafx.scene.control.Label label = new javafx.scene.control.Label(labelText);
        VBox box = new VBox(4, field, label);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /** Parses a string as a non-negative double, or returns null if invalid. */
    private Double parsePositiveDouble(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        try {
            double value = Double.parseDouble(text.trim());
            return value >= 0 ? value : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}