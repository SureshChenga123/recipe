package com.abnamro.recipe.exception;

import com.abnamro.recipe.resource.response.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class for recipe exception
 *
 * @author Suresh Chenga
 */
@Getter
@AllArgsConstructor
public class RecipeException extends RuntimeException {
    private Error error;
}
