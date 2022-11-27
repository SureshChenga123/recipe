package com.abnamro.recipe.controller;

import com.abnamro.recipe.resource.request.Recipe;
import com.abnamro.recipe.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for all recipe endpoints
 *
 * @author Suresh Chenga
 */
@RestController
public class RecipeController implements BaseController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Fetch all the recipes
     *
     * @return the list of recipes
     */
    @GetMapping(value = "allRecipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllRecipes() {
        return success(recipeService.getAllRecipes());
    }

    /**
     * Fetch all the recipes based on user inputs
     *
     * @param isVegetarian        Boolean
     * @param instructionContains String
     * @param noOfServings        Integer
     * @param presentIngredients  List
     * @param absentIngredients   List
     * @return the list of recipes
     */
    @GetMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchRecipes(@RequestParam(value = "isVegetarian", required = false) Boolean isVegetarian,
                                        @RequestParam(value = "instructionContains", required = false)
                                        String instructionContains,
                                        @RequestParam(value = "noOfServings", required = false) Integer noOfServings,
                                        @RequestParam(value = "presentIngredients", required = false)
                                        List<String> presentIngredients,
                                        @RequestParam(value = "absentIngredients", required = false)
                                        List<String> absentIngredients
    ) {
        return success(recipeService.getAllRecipes(isVegetarian, instructionContains, noOfServings, presentIngredients, absentIngredients));
    }

    /**
     * Create a new recipe
     *
     * @param recipe Recipe
     * @return the Recipe response
     */
    @PostMapping(value = "/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRecipe(@Valid @RequestBody Recipe recipe) {
        return created(recipeService.addRecipe(recipe));
    }

    /**
     * Create a new recipe
     *
     * @param recipe Recipe
     * @return the Recipe response
     */
    @PutMapping(value = "/recipe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id) {
        return created(recipeService.updateRecipe(recipe, id));
    }

    /**
     * Delete recipe by id
     *
     * @param id Long
     * @return the list of recipes
     */
    @DeleteMapping(value = "recipe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return success(recipeService.getAllRecipes());
    }
}
