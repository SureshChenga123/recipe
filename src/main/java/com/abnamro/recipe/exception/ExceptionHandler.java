package com.abnamro.recipe.exception;

import com.abnamro.recipe.resource.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Global exception handler
 *
 * @author Suresh Chenga
 */

@ControllerAdvice
public class ExceptionHandler {

    /**
     * Method used to handle recipe exception
     *
     * @param recipeException RecipeException
     * @return the error response
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(RecipeException.class)
    public ResponseEntity<ErrorResponse> handleRecipeException(RecipeException recipeException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.getErrors().add(recipeException.getError());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
