package com.abnamro.recipe.controller;

import com.abnamro.recipe.resource.response.Recipe;
import com.abnamro.recipe.resource.response.RecipeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BaseController {

    default ResponseEntity<?> created(RecipeResponse recipeResponse) {
        return new ResponseEntity<>(recipeResponse, HttpStatus.CREATED);
    }

    default ResponseEntity<?> success(RecipeResponse recipeResponse) {
        return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
    }
}
