package com.abnamro.recipe.processor;

import com.abnamro.recipe.resource.response.ErrorCode;
import com.abnamro.recipe.resource.response.Error;
import com.abnamro.recipe.resource.response.ErrorSeverityLevel;
import org.springframework.stereotype.Component;

/**
 * Class for error process
 *
 * @author Suresh Chenga
 */
@Component
public class ErrorProcessor {

    /**
     * Method used to creat error
     *
     * @param errorCode
     * @param severityLevel
     * @return the Error
     */
    public Error createError(ErrorCode errorCode, ErrorSeverityLevel severityLevel) {
        return new Error(errorCode.getCode(), errorCode.name(), errorCode.getMessage(), severityLevel);
    }
}
