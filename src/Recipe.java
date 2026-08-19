import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private String instructions;
    private String date;
    private String coverPhoto;
    private List<RecipeIngredient> recipeIngredients;

    public Recipe(String name, String date) {
        this.name = name;
        this.date = date;
        this.instructions = "";
        this.coverPhoto = null;
        this.recipeIngredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void addInstruction(String instruction) {
        this.instructions += instruction + "\n";
    }

    public void editInstruction(String newInstructions) {
        this.instructions = newInstructions;
    }

    public String getDate() {
        return date;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.add(recipeIngredient);
    }

    public void removeRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.remove(recipeIngredient);
    }

    public double calculateTotalCalories() {
        double total = 0;
        for (RecipeIngredient ri : recipeIngredients) {
            total += ri.calculateTotalCalories();
        }
        return total;
    }

    @Override
    public String toString() {
        return name + " (" + date + ") - " + recipeIngredients.size() + " ingredients";
    }
}
