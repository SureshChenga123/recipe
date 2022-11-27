package com.abnamro.recipe.entity;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static com.abnamro.recipe.common.Constant.INSTRUCTION;
import static com.abnamro.recipe.common.Constant.IS_VEGETARIAN;
import static com.abnamro.recipe.common.Constant.NO_OF_SERVINGS;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Class for recipe specification
 *
 * @author Suresh Chenga
 */
public class RecipeSpecification {

    /**
     * Get recipe specification.
     *
     * @param isVegetarian        isVegetarian
     * @param instructionContains String
     * @param noOfServings        noOfServings
     * @param presentIngredients  presentIngredients
     * @param absentIngredients   absentIngredients
     * @return the specification
     */
    public static Specification<Recipe> getRecipes(Boolean isVegetarian,
                                                   String instructionContains,
                                                   Integer noOfServings,
                                                   List<String> presentIngredients,
                                                   List<String> absentIngredients) {

        Specification<Recipe> specification = where(isVegetarian(isVegetarian))
                .and(instructionContains(instructionContains))
                .and(equalsNoOfServings(noOfServings));

        if (presentIngredients != null) {
            for (String ingredient : presentIngredients) {
                specification = specification.and(where(containIngredients(ingredient)));
            }
        }

        if (absentIngredients != null) {
            for (String ingredient : absentIngredients) {
                specification = specification.and(where(notContainIngredients(ingredient)));
            }
        }
        return specification;
    }

    static Specification<Recipe> isVegetarian(Boolean isVegetarian) {
        if (isVegetarian != null) {
            return (recipe, cq, cb) -> cb.equal(recipe.get(IS_VEGETARIAN), isVegetarian);
        } else {
            return null;
        }
    }

    static Specification<Recipe> instructionContains(String instructionContains) {
        if (instructionContains != null) {
            return (recipe, cq, cb) -> cb.like(cb.lower(recipe.get(INSTRUCTION)),
                    "%" + instructionContains.toLowerCase() + "%");
        } else {
            return null;
        }
    }


    static Specification<Recipe> equalsNoOfServings(Integer noOfServings) {
        if (noOfServings != null) {
            return (recipe, cq, cb) -> cb.equal(recipe.get(NO_OF_SERVINGS), noOfServings);
        } else {
            return null;
        }
    }

    static Specification<Recipe> containIngredients(String ingredient) {
        return (recipe, cq, cb) -> cb.isMember(ingredient, recipe.get(Recipe_.ingredients));
    }

    static Specification<Recipe> notContainIngredients(String ingredient) {
        return (recipe, cq, cb) -> cb.isNotMember(ingredient, recipe.get(Recipe_.ingredients));
    }
}
