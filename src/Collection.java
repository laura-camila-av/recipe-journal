import java.util.ArrayList;
import java.util.List;

public class Collection {
    private String collectionName;
    private List<Recipe> recipes;

    public Collection(String collectionName) {
        this.collectionName = collectionName;
        this.recipes = new ArrayList<>();
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }

    @Override
    public String toString() {
        return collectionName + " (" + recipes.size() + " recipes)";
    }
}