public class Ingredient {
    private String name;
    private Macros macrosPerUnit;

    public Ingredient(String name, Macros macrosPerUnit) {
        this.name = name;
        this.macrosPerUnit = macrosPerUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Macros getMacrosPerUnit() {
        return macrosPerUnit;
    }

    public void setMacrosPerUnit(Macros macrosPerUnit) {
        this.macrosPerUnit = macrosPerUnit;
    }

    @Override
    public String toString() {
        return name + " (" + macrosPerUnit + " per unit)";
    }
}