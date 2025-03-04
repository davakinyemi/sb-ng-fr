package com.example.spring_boot_fr.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * @author Dave AKN
 * @version 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class ExceptionResponse {
    private Integer businessErrorCode;
    private String businessExceptionDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;
}
