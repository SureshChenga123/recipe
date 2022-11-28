package com.abnamro.recipe.exception;

import com.abnamro.recipe.RecipeApplicationTests;
import com.abnamro.recipe.resource.response.Error;
import com.abnamro.recipe.resource.response.ErrorResponse;
import com.abnamro.recipe.resource.response.ErrorSeverityLevel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerTest extends RecipeApplicationTests {

    @InjectMocks
    ExceptionHandler exceptionHandler;

    @Test
    void handleRecipeExceptionBADREQUEST() {
        Error error = new Error("R-001", "DB_EXCEPTION_GET", "Get recipe failed", ErrorSeverityLevel.ERROR);
        RecipeException recipeException = new RecipeException(error);
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleRecipeException(recipeException);
        assertEquals(responseEntity.getBody().getErrors().get(0).getName(), "DB_EXCEPTION_GET");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleRecipeExceptionNOT_FOUND() {
        Error error = new Error("R-005", "RECIPE_NOT_FOUND", "Recipe not found", ErrorSeverityLevel.ERROR);
        RecipeException recipeException = new RecipeException(error);
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleRecipeException(recipeException);
        assertEquals(responseEntity.getBody().getErrors().get(0).getName(), "RECIPE_NOT_FOUND");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}