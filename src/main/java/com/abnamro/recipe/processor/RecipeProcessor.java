package com.abnamro.recipe.processor;

import com.abnamro.recipe.entity.Recipe;
import com.abnamro.recipe.mapper.DataMapper;
import com.abnamro.recipe.resource.response.RecipeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class for responses process
 *
 * @author Suresh Chenga
 */
@Component
@RequiredArgsConstructor
public class RecipeProcessor {

    private final DataMapper dataMapper;

    /**
     * Method used to process get all recipes
     *
     * @param recipeEntities List
     * @return the RecipeResponse
     */
    public RecipeResponse processFetchAllRecipesResponse(List<Recipe> recipeEntities) {
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeEntities.forEach(recipeEntity -> {
            com.abnamro.recipe.resource.response.Recipe response = dataMapper.toRecipeResponse(recipeEntity);
            recipeResponse.getRecipes().add(response);
        });
        return recipeResponse;
    }

    /**
     * Method used to process add or update recipe responses
     *
     * @param createdRecipe Recipe
     * @return the RecipeResponse
     */
    public RecipeResponse processAddOrUpdateRecipeResponse(Recipe createdRecipe) {
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.getRecipes().add(dataMapper.toRecipeResponse(createdRecipe));
        return recipeResponse;
    }
}
