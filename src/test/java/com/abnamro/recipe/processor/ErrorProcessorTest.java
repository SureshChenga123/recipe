package com.abnamro.recipe.processor;

import com.abnamro.recipe.RecipeApplicationTests;
import com.abnamro.recipe.resource.response.Error;
import com.abnamro.recipe.resource.response.ErrorCode;
import com.abnamro.recipe.resource.response.ErrorSeverityLevel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class ErrorProcessorTest extends RecipeApplicationTests {

    @InjectMocks
    ErrorProcessor errorProcessor;

    @Test
    void createError() {
        Error error = errorProcessor.createError(ErrorCode.DB_EXCEPTION_CREATE, ErrorSeverityLevel.ERROR);
        assertEquals("DB_EXCEPTION_CREATE", error.getName());
    }
}