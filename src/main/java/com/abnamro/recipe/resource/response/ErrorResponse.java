package com.abnamro.recipe.resource.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for error response
 *
 * @author Suresh Chenga
 */
@Getter
public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<Error> errors = new ArrayList<>();
}
