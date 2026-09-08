package com.laura.recipejournal.model;

public class Ingredient {
    private String name;
    
    /**
    * The macro breakdown for this ingredient, expressed per 100 units of its
    * natural measure (i.e. per 100g for solids, per 100ml for liquids),
    * matching standard nutrition-label convention.
    * <p>
    * When calculating totals, this value must be scaled by
    * {@code quantity.getAmount() / 100.0}, not multiplied directly by the
    * quantity — see {@link RecipeIngredient#calculateTotalMacros()}.
    */
    private Macros macrosPerUnit;

    /**
    * Creates a new ingredient.
    *
    * @param name          the ingredient's display name
    * @param macrosPerUnit the macro breakdown per 100 units of this
    *                      ingredient's natural measure (per 100g for solids,
    *                      per 100ml for liquids) — not per single unit
    */
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

    /**
    * @return the macro breakdown per 100 units of this ingredient's natural
    *         measure (per 100g/100ml), not per single unit
    */
    public Macros getMacrosPerUnit() {
        return macrosPerUnit;
    }


    /**
    * @param macrosPerUnit the macro breakdown per 100 units of this
    *                       ingredient's natural measure (per 100g/100ml),
    *                       not per single unit
    */
    public void setMacrosPerUnit(Macros macrosPerUnit) {
        this.macrosPerUnit = macrosPerUnit;
    }

    @Override
    public String toString() {
        return name + " (" + macrosPerUnit + " per unit)";
    }
}