package com.laura.recipejournal.model;

public class Quantity {
    private double amount;
    private String unit;

    public Quantity(double amount, String unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return amount + " " + unit;
    }
}

