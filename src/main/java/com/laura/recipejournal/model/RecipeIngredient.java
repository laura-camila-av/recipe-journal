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

    public Macros calculateTotalMacros() {
        Macros perUnit = ingredient.getMacrosPerUnit();
        double qty = quantity.getAmount();
        double scale = qty / 100.0; // macros are defined per 100g/100ml

        double totalFat = perUnit.getFat() * scale;
        double totalProtein = perUnit.getProtein() * scale;
        double totalCarbohydrate = perUnit.getCarbohydrate() * scale;

        return new Macros(totalFat, totalProtein, totalCarbohydrate);
    }

    public double calculateTotalCalories() {
        return calculateTotalMacros().calculateCaloriesPerUnit();
    }

    @Override
    public String toString() {
        return quantity + " " + ingredient.getName();
    }
}