package com.laura.recipejournal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.laura.recipejournal.model.Recipe;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class RecipeStorage {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type RECIPE_LIST_TYPE = new TypeToken<List<Recipe>>() {}.getType();

    /**
     * Saves the given recipes to {@code filePath}, writing to a temporary
     * file first and atomically renaming it into place. This ensures the
     * target file is never left in a partially-written state if the app
     * crashes or loses power mid-save — readers will always see either the
     * previous complete version or the new complete version, never a
     * truncated/corrupt one.
     */
    public static void save(List<Recipe> recipes, String filePath) {
        Path target = Path.of(filePath);
        Path temp = Path.of(filePath + ".tmp");

        try (FileWriter writer = new FileWriter(temp.toFile())) {
            gson.toJson(recipes, RECIPE_LIST_TYPE, writer);
        } catch (IOException e) {
            System.out.println("Error saving recipes: " + e.getMessage());
            return;
        }

        try {
            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.out.println("Error finalizing save: " + e.getMessage());
        }
    }

    /**
     * Loads recipes from {@code filePath}. Returns an empty list if the
     * file doesn't exist, is empty, or fails to parse — callers can always
     * safely iterate or add to the result without a null check.
     */
    public static List<Recipe> load(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            List<Recipe> recipes = gson.fromJson(reader, RECIPE_LIST_TYPE);
            return recipes != null ? recipes : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error loading recipes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}