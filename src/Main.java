public class Main {
    public static void main(String[] args) {
        Macros flourMacros = new Macros(1, 10, 76);
        Ingredient flour = new Ingredient("Flour", flourMacros);
        Quantity amount = new Quantity(2, "units");
        RecipeIngredient recipeFlour = new RecipeIngredient(flour, amount);

        Recipe pancakes = new Recipe("Pancakes", "2026-08-19");
        pancakes.addInstruction("Mix dry ingredients.");
        pancakes.addInstruction("Add wet ingredients and whisk.");
        pancakes.addRecipeIngredient(recipeFlour);

        System.out.println(pancakes);
        System.out.println(pancakes.getInstructions());
        System.out.println("Total calories: " + pancakes.calculateTotalCalories());
    
        Collection desserts = new Collection("Desserts");
        desserts.addRecipe(pancakes);
        System.out.println(desserts);

        Profile laura = new Profile("Laura");
        laura.addCollection(desserts);
        System.out.println(laura);
    
    }
}