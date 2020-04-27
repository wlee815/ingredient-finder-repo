package com.william.example.dh.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {
    private int id;
    private String name;
    private boolean isAllergen;

    public Ingredient(){

    }

    public Ingredient(int id, String name, boolean isAllergen) {
        this.id = id;
        this.name = name;
        this.isAllergen = isAllergen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("is_allergen")
    public boolean isAllergen() {
        return isAllergen;
    }

    public void setAllergen(boolean allergen) {
        isAllergen = allergen;
    }
}
