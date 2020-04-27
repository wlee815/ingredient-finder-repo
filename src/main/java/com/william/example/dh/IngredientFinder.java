package com.william.example.dh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.william.example.dh.model.Ingredient;
import com.william.example.dh.model.Ingredients;
import com.william.example.dh.model.Product;
import com.william.example.dh.model.Products;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class IngredientFinder {

    private Map<String, Set<Integer>> ingredientNameToId;
    private Map<Integer, Set<Product>> ingredientIdToProducts;

    public IngredientFinder(Map<String, Set<Integer>> ingredientNameToId, Map<Integer, Set<Product>> ingredientIdToProducts) {
        this.ingredientNameToId = ingredientNameToId;
        this.ingredientIdToProducts = ingredientIdToProducts;
    }

    /**
     *
     * @param ingredientName An ingredient that you're interested in.
     * @return All Product objects that contain the provided ingredient.
     */
    public Set<Product> findProducts(String ingredientName) {
        return ingredientNameToId.entrySet().stream()
                .filter(entry -> entry.getKey().equals(ingredientName))
                .flatMap(entry -> entry.getValue().stream())
                .map(ingredientId -> ingredientIdToProducts.get(ingredientId))
                .flatMap(productSet -> productSet.stream())
                .collect(Collectors.toSet());
    }

    /**
     *
     * @param ingredients The universe of ingredients
     * @return A map where key=ingredientName, value=all id's that correspond to that ingredient
     */
    public static Map<String, Set<Integer>> constructIngredientNameToIdsMap(Ingredients ingredients) {
        Map<String, Set<Integer>> ingredientNameToIds = new HashMap<>();

        for (Ingredient ingredient : ingredients.getIngredients()) {
            if (ingredientNameToIds.keySet().contains(ingredient.getName())) {
                Set<Integer> ids = ingredientNameToIds.get(ingredient.getName());
                ids.add(ingredient.getId());
            } else {
                Set<Integer> ids = new HashSet<>();
                ids.add(ingredient.getId());
                ingredientNameToIds.put(ingredient.getName(), ids);
            }
        }

        return ingredientNameToIds;
    }

    /**
     *
     * @param products The universe of products.
     * @return A map where key=ingredientId, value=all products that contain said ingredient
     */
    public static Map<Integer, Set<Product>> constructIngredientIdToProductMap(Products products) {
        Map<Integer, Set<Product>> ingredientIdToProducts = new HashMap<>();
        for (Product product : products.getProducts()) {
            for (Integer id : product.getIngredientIds()) {
                if (ingredientIdToProducts.keySet().contains(id)) {
                    Set<Product> matchingProducts = ingredientIdToProducts.get(id);
                    matchingProducts.add(product);
                } else {
                    Set<Product> matchingProducts = new HashSet<>();
                    matchingProducts.add(product);
                    ingredientIdToProducts.put(id, matchingProducts);
                }
            }
        }
        return ingredientIdToProducts;
    }

    /**
     *
     * @param args args[0] is the input file root. Example:
     *             /Users/williamlee815/Documents/repos/ingredient-finder/src/main/resources/
     *             args[1] is the ingredient you're interested in
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String documentRoot = args[0];
        String targetIngredient = args[1];

        ObjectMapper mapper = new ObjectMapper();

        //ingest raw JSON files and express as POJOs
        Ingredients ingredients = mapper.readValue(new File(documentRoot + "ingredients.json"), Ingredients.class);
        Products products = mapper.readValue(new File(documentRoot + "products.json"), Products.class);

        //organize POJOs to facilitate lookups
        Map<String, Set<Integer>> ingredientNameToIds = IngredientFinder.constructIngredientNameToIdsMap(ingredients);
        Map<Integer, Set<Product>> ingredientIdToProducts = IngredientFinder.constructIngredientIdToProductMap(products);

        IngredientFinder ingredientFinder = new IngredientFinder(ingredientNameToIds, ingredientIdToProducts);
        System.out.println(mapper.writeValueAsString(ingredientFinder.findProducts(targetIngredient)));
    }
}
