package com.laura.recipejournal.model;

public class RecipeIngredient {
    private Ingredient ingredient;
    private Quantity quantity;

    public RecipeIngredient(Ingredient ingredient, Quantity quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    /**
    * Returns this ingredient entry's total macros. Since {@link Ingredient}
    * stores the total macros for the amount actually used (not a reusable
    * per-unit rate), this is simply the ingredient's macros as entered — no
    * scaling by {@link Quantity} is needed or performed.
     */
    public Macros calculateTotalMacros() {
        return ingredient.getMacros();
    }

    public double calculateTotalCalories() {
        return calculateTotalMacros().calculateCaloriesPerUnit();
    }

    @Override
    public String toString() {
        return quantity + " " + ingredient.getName();
    }
}