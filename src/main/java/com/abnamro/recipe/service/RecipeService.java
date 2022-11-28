package com.abnamro.recipe.service;

import com.abnamro.recipe.processor.RecipeProcessor;
import com.abnamro.recipe.entity.Recipe;
import com.abnamro.recipe.entity.RecipeSpecification;
import com.abnamro.recipe.exception.RecipeException;
import com.abnamro.recipe.mapper.DataMapper;
import com.abnamro.recipe.processor.ErrorProcessor;
import com.abnamro.recipe.repository.RecipeRepository;
import com.abnamro.recipe.resource.response.ErrorCode;
import com.abnamro.recipe.resource.response.ErrorSeverityLevel;
import com.abnamro.recipe.resource.response.RecipeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class for Recipe service
 *
 * @author Suresh Chenga
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeService {

    private final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;

    private final ErrorProcessor errorProcessor;

    private final RecipeProcessor recipeProcessor;

    private final DataMapper dataMapper;

    /**
     * Method used to fetch all the recipes
     *
     * @return the RecipeResponse
     */
    public RecipeResponse getAllRecipes() {
        return fetchAllRecipes(null);
    }

    /**
     * Method used to fetch all the recipes based on inputs
     *
     * @param isVegetarian        Boolean
     * @param instructionContains String
     * @param noOfServings        Integer
     * @param presentIngredients  List
     * @param absentIngredients   List
     * @return the RecipeResponse
     */
    public RecipeResponse getAllRecipes(Boolean isVegetarian, String instructionContains, Integer noOfServings, List<String> presentIngredients, List<String> absentIngredients) {
        LOGGER.info("Get all the recipes with given inputs : {} {} {} {} {}", isVegetarian, instructionContains, noOfServings, presentIngredients, absentIngredients);
        Specification<com.abnamro.recipe.entity.Recipe> specification = RecipeSpecification.getRecipes(isVegetarian,
                instructionContains,
                noOfServings,
                presentIngredients,
                absentIngredients);

        return fetchAllRecipes(specification);
    }

    /**
     * Method used to create a new recipe
     *
     * @param recipe resource request
     * @return Recipe resource response
     */
    public RecipeResponse addRecipe(com.abnamro.recipe.resource.request.Recipe recipe) {
        com.abnamro.recipe.entity.Recipe recipeEntity = dataMapper.toRecipeEntity(recipe);
        com.abnamro.recipe.entity.Recipe createdRecipe;
        try {
            createdRecipe = recipeRepository.saveAndFlush(recipeEntity);
        } catch (Exception exception) {
            LOGGER.error("Add recipe failed with exception : {}", exception.getMessage());
            throw new RecipeException(errorProcessor.createError(ErrorCode.DB_EXCEPTION_CREATE, ErrorSeverityLevel.ERROR));
        }
        return recipeProcessor.processAddOrUpdateRecipeResponse(createdRecipe);
    }

    /**
     * Method used to update recipe
     *
     * @param updateRecipe Recipe
     * @param id           Long
     * @return the updated recipe
     */
    public RecipeResponse updateRecipe(com.abnamro.recipe.resource.request.Recipe updateRecipe, Long id) {
        Optional<Recipe> toBeUpdatedRecipe = recipeRepository.findById(id);
        if (toBeUpdatedRecipe.isPresent()) {
            toBeUpdatedRecipe = toBeUpdatedRecipe.map(recipe -> {
                updateRecipe.setRecipeName(
                        Optional.ofNullable(updateRecipe.getRecipeName()).orElse(recipe.getRecipeName()));
                updateRecipe.setIngredients(
                        Optional.ofNullable(updateRecipe.getIngredients()).orElse(recipe.getIngredients()));
                updateRecipe.setInstruction(
                        Optional.ofNullable(updateRecipe.getInstruction()).orElse(recipe.getInstruction()));
                updateRecipe.setIsVegetarian(
                        Optional.ofNullable(updateRecipe.getIsVegetarian()).orElse(recipe.getIsVegetarian()));
                updateRecipe.setNoOfServings(
                        Optional.ofNullable(updateRecipe.getNoOfServings()).orElse(recipe.getNoOfServings()));
                Recipe recipeEntity = dataMapper.toRecipeEntity(updateRecipe);
                recipeEntity.setId(id);
                try {
                    return recipeRepository.saveAndFlush(recipeEntity);
                } catch (Exception exception) {
                    LOGGER.error("Update recipe failed with exception : {}", exception.getMessage());
                    throw new RecipeException(errorProcessor.createError(ErrorCode.DB_EXCEPTION_UPDATE, ErrorSeverityLevel.ERROR));
                }
            });
            return recipeProcessor.processAddOrUpdateRecipeResponse(toBeUpdatedRecipe.get());
        } else {
            LOGGER.info("Update recipe not found with id {}", id);
            throw new RecipeException(errorProcessor.createError(ErrorCode.RECIPE_NOT_FOUND, ErrorSeverityLevel.INFO));
        }
    }

    /**
     * Method used to delete the recipe by id
     *
     * @param id Long
     */
    public void deleteRecipe(Long id) {
        Optional<Recipe> toBeDeletedRecipe = recipeRepository.findById(id);
        if (toBeDeletedRecipe.isPresent()) {
            try {
                recipeRepository.deleteById(id);
            } catch (Exception exception) {
                LOGGER.error("Delete recipe failed with exception : {}", exception.getMessage());
                throw new RecipeException(errorProcessor.createError(ErrorCode.DB_EXCEPTION_DELETE, ErrorSeverityLevel.ERROR));
            }
        } else {
            LOGGER.info("Delete recipe not found with id {}", id);
            throw new RecipeException(errorProcessor.createError(ErrorCode.RECIPE_NOT_FOUND, ErrorSeverityLevel.INFO));
        }
    }

    private RecipeResponse fetchAllRecipes(Specification<com.abnamro.recipe.entity.Recipe> specification) {
        List<com.abnamro.recipe.entity.Recipe> recipeEntities;
        try {
            if (specification != null) {
                recipeEntities = recipeRepository.findAll(specification);
            } else {
                recipeEntities = recipeRepository.findAll();
            }
        } catch (Exception exception) {
            LOGGER.error("Get all recipes failed with exception : {}", exception.getMessage());
            throw new RecipeException(errorProcessor.createError(ErrorCode.DB_EXCEPTION_GET, ErrorSeverityLevel.ERROR));
        }
        if (recipeEntities.isEmpty()) {
            LOGGER.info("No recipes found");
        }
        return recipeProcessor.processFetchAllRecipesResponse(recipeEntities);
    }


}
