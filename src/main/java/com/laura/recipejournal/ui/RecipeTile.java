package com.laura.recipejournal.ui;

import com.laura.recipejournal.model.Recipe;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A clickable tile representing a single recipe: cover image + name.
 * Used in the home screen's recipe grid.
 */
public class RecipeTile extends VBox {

    public RecipeTile(Recipe recipe, Runnable onClick) {
        setAlignment(Pos.CENTER);
        setSpacing(4);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        String coverPhoto = recipe.getCoverPhoto();
        if (coverPhoto != null) {
            imageView.setImage(new Image(coverPhoto));
        }

        Label nameLabel = new Label(recipe.getName());

        getChildren().addAll(imageView, nameLabel);

        setOnMouseClicked(event -> onClick.run());
    }
}