package com.abnamro.recipe.resource.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for recipe response resource model
 *
 * @author Suresh Chenga
 */
@Getter
@Setter
@NoArgsConstructor
public class Recipe {
    private Long id;
    private String recipeName;
    private Boolean isVegetarian;
    private Integer noOfServings;
    private Set<String> ingredients = new HashSet<>();
    private String instruction;
}
