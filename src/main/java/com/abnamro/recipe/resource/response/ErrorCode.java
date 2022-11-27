package com.abnamro.recipe.resource.response;

import lombok.Getter;

import static com.abnamro.recipe.common.Constant.CREATE_RECIPE_ERROR_MESSAGE;
import static com.abnamro.recipe.common.Constant.GET_RECIPE_ERROR_MESSAGE;
import static com.abnamro.recipe.common.Constant.RECIPE_NOT_FOUND_MESSAGE;
import static com.abnamro.recipe.common.Constant.UPDATE_RECIPE_ERROR_MESSAGE;
import static com.abnamro.recipe.common.Constant.DELETE_RECIPE_ERROR_MESSAGE;

/**
 * Enum for error codes and messages
 */
@Getter
public enum ErrorCode {
    DB_EXCEPTION_GET("R-001", GET_RECIPE_ERROR_MESSAGE),
    DB_EXCEPTION_CREATE("R-002", CREATE_RECIPE_ERROR_MESSAGE),
    DB_EXCEPTION_UPDATE("R-003", UPDATE_RECIPE_ERROR_MESSAGE),
    DB_EXCEPTION_DELETE("R-004", DELETE_RECIPE_ERROR_MESSAGE),
    RECIPE_NOT_FOUND("R-004", RECIPE_NOT_FOUND_MESSAGE);

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
