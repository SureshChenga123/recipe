package com.abnamro.recipe.resource.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

/**
 * Recipe resource model class
 * Represent resource model class for API request
 *
 * @author Suresh Chenga
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Recipe {
    @NotBlank
    private String recipeName;
    @NotNull
    private Boolean isVegetarian;
    @NotNull
    @Positive
    private Integer noOfServings;
    @NotNull
    @NotEmpty
    private Set<String> ingredients = new HashSet<>();
    private String instruction;
}
