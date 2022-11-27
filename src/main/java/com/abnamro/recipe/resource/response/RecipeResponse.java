package com.abnamro.recipe.resource.response;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for RecipeResponse.
 *
 * @author Suresh Chenga
 */
@Getter
@NoArgsConstructor
public class RecipeResponse extends ErrorResponse {
    @JsonValue
    private final List<Recipe> recipes = new ArrayList<>();
}
