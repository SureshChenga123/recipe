package com.abnamro.recipe.mapper;

import com.abnamro.recipe.resource.response.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DataMapper {

    @Named("toLowerCase")
    static Set<String> toLowerCaseSetOfStrings(Set<String> ingredients) {
        return ingredients.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @Named("toLowerCaseString")
    static String toLowerCaseString(String value) {
        return value.toLowerCase();
    }

    Recipe toRecipeResponse(com.abnamro.recipe.entity.Recipe recipe);

    @Mapping(source = "ingredients", target = "ingredients", qualifiedByName = "toLowerCase")
    @Mapping(source = "recipeName", target = "recipeName", qualifiedByName = "toLowerCaseString")
    @Mapping(source = "instruction", target = "instruction", qualifiedByName = "toLowerCaseString")
    com.abnamro.recipe.entity.Recipe toRecipeEntity(com.abnamro.recipe.resource.request.Recipe recipe);

}
