package com.laura.recipejournal;
import com.laura.recipejournal.model.*;

public class Main {
    public static void main(String[] args) {

        Recipe porridge = new Recipe("porridge", "2026-08-19");
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

        porridge.addRecipeIngredient(bananaEntry);
        porridge.addRecipeIngredient(oatsEntry);
        porridge.addRecipeIngredient(milkEntry);

        Macros totalMacros = porridge.calculateTotalMacros();
        System.out.println(totalMacros);

        double totalCalories = porridge.calculateTotalCalories();
        System.out.println(totalCalories);


    
    }
}