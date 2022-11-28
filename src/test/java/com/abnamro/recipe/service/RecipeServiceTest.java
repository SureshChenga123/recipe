package com.abnamro.recipe.service;

import com.abnamro.recipe.RecipeApplicationTests;
import com.abnamro.recipe.entity.Recipe;
import com.abnamro.recipe.exception.RecipeException;
import com.abnamro.recipe.mapper.DataMapper;
import com.abnamro.recipe.processor.ErrorProcessor;
import com.abnamro.recipe.processor.RecipeProcessor;
import com.abnamro.recipe.repository.RecipeRepository;
import com.abnamro.recipe.resource.response.Error;
import com.abnamro.recipe.resource.response.ErrorSeverityLevel;
import com.abnamro.recipe.resource.response.RecipeResponse;
import com.abnamro.recipe.util.RecipeMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class RecipeServiceTest extends RecipeApplicationTests {

    @Mock
    DataMapper dataMapper;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeProcessor recipeProcessor;

    @Mock
    ErrorProcessor errorProcessor;

    @InjectMocks
    RecipeService recipeService;

    @Test
    void testGetAllRecipes() {
        List<Recipe> recipeEntities = new ArrayList<>();
        recipeEntities.add(RecipeMockData.createRecipeEntity());
        when(recipeRepository.findAll(any(Specification.class))).thenReturn(recipeEntities);
        when(recipeProcessor.processFetchAllRecipesResponse(any())).thenReturn(RecipeMockData.createRecipeResponse());
        RecipeResponse recipeResponse = recipeService.getAllRecipes(null,
                null, null,
                null, null);
        assertEquals(recipeResponse.getRecipes().size(), 1);
    }

    @Test
    void testGetAllRecipesThrowsException() {
        List<Recipe> recipeEntities = new ArrayList<>();
        recipeEntities.add(RecipeMockData.createRecipeEntity());
        when(recipeRepository.findAll(any(Specification.class))).thenThrow(new DataIntegrityViolationException(""));
        when(errorProcessor.createError(any(), any())).thenReturn(new Error("R-001", "DB_EXCEPTION_GET", "Get recipe failed", ErrorSeverityLevel.ERROR));
        Assertions.assertThrows(RecipeException.class,
                () -> recipeService.getAllRecipes(null,
                        null, null,
                        null, null));
    }

    @Test
    void addRecipe() {
        Recipe recipeEntity = RecipeMockData.createRecipeEntity();
        when(dataMapper.toRecipeEntity(any())).thenReturn(recipeEntity);
        when(recipeRepository.saveAndFlush(recipeEntity)).thenReturn(recipeEntity);
        when(recipeProcessor.processAddOrUpdateRecipeResponse(recipeEntity)).thenReturn(RecipeMockData.createRecipeResponse());
        RecipeResponse recipeResponse = recipeService.addRecipe(RecipeMockData.createRequestRecipe());
        assertEquals(recipeResponse.getRecipes().get(0).getId(), RecipeMockData.createRecipeEntity().getId());
    }

    @Test
    void addRecipeThrowException() {
        Recipe recipeEntity = RecipeMockData.createRecipeEntity();
        when(dataMapper.toRecipeEntity(any())).thenReturn(recipeEntity);
        when(recipeRepository.saveAndFlush(recipeEntity)).thenThrow(new DataIntegrityViolationException(""));
        when(errorProcessor.createError(any(), any())).thenReturn(new Error("R-002", "DB_EXCEPTION_CREATE", "Add recipe failed", ErrorSeverityLevel.ERROR));
        Assertions.assertThrows(RecipeException.class,
                () -> recipeService.addRecipe(RecipeMockData.createRequestRecipe()));
    }

    @Test
    void updateRecipe() {
        Recipe recipeEntity = RecipeMockData.createRecipeEntity();
        when(dataMapper.toRecipeEntity(any())).thenReturn(recipeEntity);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipeEntity));
        when(recipeRepository.saveAndFlush(recipeEntity)).thenReturn(recipeEntity);
        when(recipeProcessor.processAddOrUpdateRecipeResponse(recipeEntity)).thenReturn(RecipeMockData.createRecipeResponse());
        RecipeResponse recipeResponse = recipeService.updateRecipe(RecipeMockData.createRequestRecipe(), 1L);
        assertEquals(recipeResponse.getRecipes().get(0).getId(), RecipeMockData.createRecipeEntity().getId());
    }

    @Test
    void updateRecipeThrowsException() {
        Recipe recipeEntity = RecipeMockData.createRecipeEntity();
        when(dataMapper.toRecipeEntity(any())).thenReturn(recipeEntity);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipeEntity));
        when(recipeRepository.saveAndFlush(recipeEntity)).thenThrow(new DataIntegrityViolationException(""));
        when(errorProcessor.createError(any(), any())).thenReturn(new Error("R-003", "DB_EXCEPTION_UPDATE", "Update recipe failed", ErrorSeverityLevel.ERROR));
        Assertions.assertThrows(RecipeException.class,
                () -> recipeService.updateRecipe(RecipeMockData.createRequestRecipe(), 1L));
    }

    @Test
    void updateRecipeNotFound() {
        Recipe recipeEntity = RecipeMockData.createRecipeEntity();
        when(dataMapper.toRecipeEntity(any())).thenReturn(recipeEntity);
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        when(errorProcessor.createError(any(), any())).thenReturn(new Error("R-005", "RECIPE_NOT_FOUND", "Recipe not found", ErrorSeverityLevel.INFO));
        Assertions.assertThrows(RecipeException.class,
                () -> recipeService.updateRecipe(RecipeMockData.createRequestRecipe(), 1L));
    }

    @Test
    void deleteRecipe() {
        Recipe recipeEntity = RecipeMockData.createRecipeEntity();
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipeEntity));
        recipeService.deleteRecipe(1L);
        assertDoesNotThrow(() -> recipeService.deleteRecipe(1L));
    }

    @Test
    void deleteRecipeThrowsException() {
        Recipe recipeEntity = RecipeMockData.createRecipeEntity();
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipeEntity));
        when(errorProcessor.createError(any(), any())).thenReturn(new Error("R-004", "DB_EXCEPTION_DELETE", "delete recipe failed", ErrorSeverityLevel.ERROR));
        doThrow(new DataIntegrityViolationException("")).when(recipeRepository).deleteById(1L);
        assertThrows(RecipeException.class, () -> recipeService.deleteRecipe(1L));
    }

    @Test
    void deleteRecipeNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        when(errorProcessor.createError(any(), any())).thenReturn(new Error("R-005", "RECIPE_NOT_FOUND", "Recipe not found", ErrorSeverityLevel.INFO));
        assertThrows(RecipeException.class, () -> recipeService.deleteRecipe(1L));
    }
}