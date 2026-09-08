package com.laura.recipejournal.model;

public class Ingredient {
    private String name;
    private Macros macros;

    /**
     * Creates a new ingredient entry.
     *
     * @param name   the ingredient's display name (e.g. "Rice")
     * @param macros the total macros for the amount of this ingredient
     *               actually used in the recipe (e.g. the macros for the
     *               150g of rice you're adding) — not a per-unit rate.
     *               There is currently no shared ingredient library, so
     *               these values are entered fresh for each recipe rather
     *               than looked up from a reusable source.
     */
    public Ingredient(String name, Macros macros) {
        this.name = name;
        this.macros = macros;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the total macros for the amount of this ingredient used in
     *         the recipe — not a per-unit/per-100 rate.
     */
    public Macros getMacros() {
        return macros;
    }

    /**
     * @param macros the total macros for the amount of this ingredient
     *               used in the recipe — not a per-unit/per-100 rate.
     */
    public void setMacros(Macros macros) {
        this.macros = macros;
    }

    @Override
    public String toString() {
        return name + " (" + macros + ")";
    }
}