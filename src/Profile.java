import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String name;
    private List<Collection> collections;

    public Profile(String name) {
        this.name = name;
        this.collections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void addCollection(Collection collection) {
        collections.add(collection);
    }

    public void removeCollection(Collection collection) {
        collections.remove(collection);
    }

    @Override
    public String toString() {
        return name + "'s Profile (" + collections.size() + " collections)";
    }
}
