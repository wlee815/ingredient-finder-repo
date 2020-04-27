package com.william.example.dh;

import com.william.example.dh.model.Ingredient;
import com.william.example.dh.model.Ingredients;
import com.william.example.dh.model.Product;
import com.william.example.dh.model.Products;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class IngredientFinderTest {

    @Test
    public void testConstructIngredientIdToProductMap() {
        Products products = new Products();
        List<Product> productList = new LinkedList<>();
        Product product1 = new Product(1, "Acai + Cherry", "smoothie", Arrays.asList(1, 2, 3, 4, 5));
        Product product2 = new Product(2, "Chocolate + Blueberry", "smoothie", Arrays.asList(5));
        Product product3 = new Product(3, "Beet + Avocado Poke", "smoothie", Arrays.asList(2, 5));
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        products.setProducts(productList);

        Map<Integer, Set<Product>> mapToTest = IngredientFinder.constructIngredientIdToProductMap(products);

        assertEquals(new HashSet<>(Arrays.asList(product1)), mapToTest.get(1));
        assertEquals(new HashSet<>(Arrays.asList(product1, product3)), mapToTest.get(2));
        assertEquals(new HashSet<>(Arrays.asList(product1)), mapToTest.get(3));
        assertEquals(new HashSet<>(Arrays.asList(product1)), mapToTest.get(4));
        assertEquals(new HashSet<>(Arrays.asList(product1, product2, product3)), mapToTest.get(5));
    }


    @Test
    public void testConstructIngredientNameToIdsMap(){
        Ingredients ingredients = new Ingredients();
        List<Ingredient> ingredientList = new LinkedList<>();
        Ingredient ingredient1 = new Ingredient(1, "Organic Acai Berry", false);
        Ingredient ingredient2 = new Ingredient(2, "Organic Cherry", false);
        Ingredient ingredient3 = new Ingredient(3, "Organic Banana", false);
        Ingredient ingredient4 = new Ingredient(4, "Organic Banana", false);
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);
        ingredientList.add(ingredient4);
        ingredients.setIngredients(ingredientList);

        Map<String, Set<Integer>> mapToTest = IngredientFinder.constructIngredientNameToIdsMap(ingredients);

        assertEquals(new HashSet<>(Arrays.asList(1)), mapToTest.get("Organic Acai Berry"));
        assertEquals(new HashSet<>(Arrays.asList(2)), mapToTest.get("Organic Cherry"));
        assertEquals(new HashSet<>(Arrays.asList(3, 4)), mapToTest.get("Organic Banana"));
    }

    @Test
    public void testFindProducts(){

        Products products = new Products();
        List<Product> productList = new LinkedList<>();
        Product product1 = new Product(1, "Acai + Cherry", "smoothie", Arrays.asList(1, 2, 3, 4, 5));
        Product product2 = new Product(2, "Chocolate + Blueberry", "smoothie", Arrays.asList(5));
        Product product3 = new Product(3, "Beet + Avocado Poke", "smoothie", Arrays.asList(2, 5));
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        products.setProducts(productList);

        Ingredients ingredients = new Ingredients();
        List<Ingredient> ingredientList = new LinkedList<>();
        Ingredient ingredient1 = new Ingredient(1, "Organic Acai Berry", false);
        Ingredient ingredient2 = new Ingredient(2, "Organic Cherry", false);
        Ingredient ingredient3 = new Ingredient(3, "Organic Banana", false);
        Ingredient ingredient4 = new Ingredient(4, "Organic Banana", false);
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);
        ingredientList.add(ingredient4);
        ingredients.setIngredients(ingredientList);

        IngredientFinder ingredientFinder = new IngredientFinder(
                IngredientFinder.constructIngredientNameToIdsMap(ingredients),
                IngredientFinder.constructIngredientIdToProductMap(products)
        );

        assertEquals(new HashSet<>(Arrays.asList(product1)), ingredientFinder.findProducts("Organic Acai Berry"));
        assertEquals(new HashSet<>(Arrays.asList(product1, product3)), ingredientFinder.findProducts("Organic Cherry"));
        assertEquals(new HashSet<>(Arrays.asList(product1)), ingredientFinder.findProducts("Organic Banana"));
    }
}
