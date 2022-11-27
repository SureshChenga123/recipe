package com.abnamro.recipe.exception;

import com.abnamro.recipe.resource.response.Error;
import com.abnamro.recipe.resource.response.ErrorCode;
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
        Error error = recipeException.getError();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.getErrors().add(error);
        HttpStatus httpStatus  = HttpStatus.INTERNAL_SERVER_ERROR;
        if(error.getName().equals(ErrorCode.RECIPE_NOT_FOUND.name())) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
