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

    public double calculateTotalCalories() {
        double caloriesPerUnit = ingredient.getMacrosPerUnit().calculateCaloriesPerUnit();
        return caloriesPerUnit * quantity.getAmount();
    }

    @Override
    public String toString() {
        return quantity + " " + ingredient.getName();
    }
}