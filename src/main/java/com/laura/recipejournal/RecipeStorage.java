package com.laura.recipejournal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laura.recipejournal.model.Recipe;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RecipeStorage {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void save(Recipe recipe, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(recipe, writer);
        } catch (IOException e) {
            System.out.println("Error saving recipe: " + e.getMessage());
        }
    }

    public static Recipe load(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Recipe.class);
        } catch (IOException e) {
            System.out.println("Error loading recipe: " + e.getMessage());
            return null;
        }
    }
}