package com.abnamro.recipe.service;

import com.abnamro.recipe.RecipeApplicationTests;
import com.abnamro.recipe.mapper.DataMapper;
import com.abnamro.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest extends RecipeApplicationTests {

    @Mock
    DataMapper dataMapper;

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeService recipeService;

    @Test
    void getAllRecipes() {
    }

    @Test
    void testGetAllRecipes() {
    }

    @Test
    void addRecipe() {
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void deleteRecipe() {
    }
}