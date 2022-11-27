package com.abnamro.recipe.it;

import com.abnamro.recipe.RecipeApplicationTests;
import com.abnamro.recipe.resource.request.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeIT extends RecipeApplicationTests {

    @Test
    void addRecipes_happyFlow_returnIsCreated() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocolate", "sugar"));
        recipe.setIngredients(ingredients);

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$[0].id", is(1)));

    }

    @Test
    void addRecipes_recipeNameNotPresent_returnBadRequest() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocolate", "sugar"));
        recipe.setIngredients(ingredients);

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    void addRecipes_recipeNameBlank_returnBadRequest() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocla", "sugara"));
        recipe.setIngredients(ingredients);

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void addRecipes_isVegetarianNotPresent_returnBadRequest() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocla", "sugara"));
        recipe.setIngredients(ingredients);

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    void addRecipes_noOfServingsNotPresent_returnBadRequest() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocla", "sugara"));
        recipe.setIngredients(ingredients);

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void addRecipes_noOfServingsLessThanOne_returnBadRequest() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(0);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocla", "sugara"));
        recipe.setIngredients(ingredients);

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void addRecipes_instructionNotPresent_returnCreated() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocla", "sugara"));
        recipe.setIngredients(ingredients);

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void addRecipes_ingredientNotPresent_returnBadRequest() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void addRecipes_ingredientListEmpty_returnBadRequest() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>();
        recipe.setIngredients(ingredients);

        mockMvc.perform(post("/recipe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(recipe))).andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    void deleteRecipes_idNotPresent_returnNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/recipe/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void getAllRecipes_searchByIsVegetartianFalse_returnSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes").param("isVegetarian", String.valueOf(false))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isVegetarian").value(false));

    }

    @Test
    public void getAllRecipes_searchByIngredientContains_returnSuccess() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes").param("presentIngredients", "sugar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ingredients.[0]").value("sugar"));


    }

    @Test
    public void getAllRecipes_searchByIngredientNotContains_returnSuccess() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes").param("absentIngredients", "chicken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ingredients.[0]").value("sugar"))
                .andExpect(jsonPath("$[0].ingredients.[1]").value("chocolate"));

    }


    @Test
    public void getAllRecipes_searchCriteriaNotMatch_returnNotFound() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes").param("presentIngredients", "fish")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void getAllRecipes_allSearchCriteriaMatched_returnSuccess() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes").param("presentIngredients", "fish")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void updateRecipe_recipeNotFound_returnNotFound() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(false);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocla", "sugara"));
        recipe.setIngredients(ingredients);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/recipe/{id}", 123)
                        .content(objectMapper.writeValueAsString(recipe))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andDo(print());


    }

    @Test
    public void updateRecipe_updateAllFields_returnOk() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(true);
        recipe.setNoOfServings(4);
        recipe.setInstruction("put chocolate");
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocla", "sugara"));
        recipe.setIngredients(ingredients);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/recipe/{id}", 1)
                        .content(objectMapper.writeValueAsString(recipe))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andDo(print());
    }

    @Test
    public void updateRecipe_updateRecipeName_returnOk() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setRecipeName("muffin");
        recipe.setIsVegetarian(true);
        recipe.setNoOfServings(4);
        Set<String> ingredients = new HashSet<>(Arrays.asList("chocla", "sugara"));
        recipe.setIngredients(ingredients);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/recipe/{id}", 1)
                        .content(objectMapper.writeValueAsString(recipe))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andDo(print());
    }
}
