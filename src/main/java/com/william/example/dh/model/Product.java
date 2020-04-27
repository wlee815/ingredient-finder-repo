package com.william.example.dh.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class Product {
    private int id;
    private String name;
    private String collection;
    private List<Integer> ingredientIds;

    public Product() {
    }

    public Product(int id, String name, String collection, List<Integer> ingredientIds) {
        this.id = id;
        this.name = name;
        this.collection = collection;
        this.ingredientIds = ingredientIds;
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

    public String getCollection() {
        return collection;
    }

    @JsonProperty("ingredient_ids")
    public List<Integer> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(List<Integer> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", collection='" + collection + '\'' +
                ", ingredientIds=" + ingredientIds +
                '}';
    }

}

