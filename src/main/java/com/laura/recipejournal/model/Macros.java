package com.laura.recipejournal.model;

public class Macros {
    private double fat;
    private double protein;
    private double carbohydrate;

    public Macros(double fat, double protein, double carbohydrate) {
        this.fat = fat;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double calculateCaloriesPerUnit() {
        return (fat * 9) + (protein * 4) + (carbohydrate * 4);
    }

    @Override
    public String toString() {
        return "Fat: " + fat + "g, Protein: " + protein + "g, Carbs: " + carbohydrate + "g ("
                + calculateCaloriesPerUnit() + " cal)";
    }
}