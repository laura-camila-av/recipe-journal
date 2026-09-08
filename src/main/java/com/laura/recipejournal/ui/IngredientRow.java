package com.laura.recipejournal.ui;

import com.laura.recipejournal.model.RecipeIngredient;

import javafx.scene.control.Label;

/**
 * A single row in a recipe's ingredient list. Represents either an existing
 * {@link RecipeIngredient} (shows its details) or the trailing "add new"
 * placeholder (pass {@code null} for {@code recipeIngredient}). Either way,
 * double-clicking the row fires {@code onDoubleClick} — later this will
 * open the ingredient add/edit form, pre-filled when editing an existing
 * ingredient and blank when adding a new one.
 */
public class IngredientRow extends Label {

    private static final String ADD_PLACEHOLDER_TEXT = "Double click to add ingredient.";

    public IngredientRow(RecipeIngredient recipeIngredient, Runnable onDoubleClick) {
        super(recipeIngredient != null ? recipeIngredient.toString() : ADD_PLACEHOLDER_TEXT);

        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                onDoubleClick.run();
            }
        });
    }
}