package com.abnamro.recipe.util;

import com.abnamro.recipe.entity.Recipe;
import com.abnamro.recipe.resource.response.RecipeResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RecipeMockData {

    public static Recipe createRecipeEntity() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocolate", "sugar"));
        recipe.setIngredients(ingredients);
        return recipe;
    }

    public static com.abnamro.recipe.resource.response.Recipe createRecipe() {
        com.abnamro.recipe.resource.response.Recipe recipe = new com.abnamro.recipe.resource.response.Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocolate", "sugar"));
        recipe.setIngredients(ingredients);
        return recipe;
    }

    public static com.abnamro.recipe.resource.request.Recipe createRequestRecipe() {
        com.abnamro.recipe.resource.request.Recipe recipe = new com.abnamro.recipe.resource.request.Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocolate", "sugar"));
        recipe.setIngredients(ingredients);
        return recipe;
    }

    public static RecipeResponse createRecipeResponse() {
        RecipeResponse recipeResponse = new RecipeResponse();
        com.abnamro.recipe.resource.response.Recipe recipe = createRecipe();
        recipe.setId(1L);
        recipeResponse.getRecipes().add(recipe);
        return recipeResponse;
    }

}
