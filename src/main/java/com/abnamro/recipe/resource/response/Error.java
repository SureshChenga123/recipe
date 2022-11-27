package com.abnamro.recipe.resource.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Null;

/**
 * Class for error
 *
 * @author Suresh Chenga
 */
@AllArgsConstructor
@Getter
public class Error {
    private String code;
    private String name;
    private String description;
    private ErrorSeverityLevel severityLevel;
    private String followUpUrl;

    public Error(String code, String name, String message, ErrorSeverityLevel severityLevel) {
        this.code = code;
        this.name = name;
        this.description = message;
        this.severityLevel = severityLevel;
    }
}
