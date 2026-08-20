package com.laura.recipejournal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TapToEditTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label myLabel = new Label("Click to edit me");
        TextField myTextField = new TextField();
        myTextField.setVisible(false);

        StackPane editablePane = new StackPane();
        editablePane.getChildren().addAll(myLabel, myTextField);

        myLabel.setOnMouseClicked(event -> {
            myTextField.setText(myLabel.getText());
            myTextField.setVisible(true);
            myLabel.setVisible(false);
        });

        myTextField.setOnAction(event -> {
            myLabel.setText(myTextField.getText());
            myTextField.setVisible(false);
            myLabel.setVisible(true);
        });

        Scene scene = new Scene(editablePane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}