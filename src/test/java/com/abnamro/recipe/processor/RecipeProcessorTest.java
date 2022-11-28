package com.abnamro.recipe.processor;

import com.abnamro.recipe.RecipeApplicationTests;
import com.abnamro.recipe.entity.Recipe;
import com.abnamro.recipe.mapper.DataMapper;
import com.abnamro.recipe.resource.response.RecipeResponse;
import com.abnamro.recipe.util.RecipeMockData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RecipeProcessorTest extends RecipeApplicationTests {

    @InjectMocks
    RecipeProcessor recipeProcessor;

    @Mock
    DataMapper dataMapper;

    @Test
    void processFetchAllRecipesResponse() {
        List<Recipe> recipeEntities = new ArrayList<>();
        recipeEntities.add(RecipeMockData.createRecipeEntity());
        when(dataMapper.toRecipeResponse(any())).thenReturn(RecipeMockData.createRecipe());
        RecipeResponse recipeResponse = recipeProcessor.processFetchAllRecipesResponse(recipeEntities);
        assertEquals(recipeResponse.getRecipes().get(0).getRecipeName(), recipeEntities.get(0).getRecipeName());
    }

    @Test
    void processAddOrUpdateRecipeResponse() {
        when(dataMapper.toRecipeResponse(any())).thenReturn(RecipeMockData.createRecipe());
        RecipeResponse recipeResponse = recipeProcessor.processAddOrUpdateRecipeResponse(RecipeMockData.createRecipeEntity());
        assertEquals(recipeResponse.getRecipes().get(0).getRecipeName(), RecipeMockData.createRecipe().getRecipeName());
    }
}