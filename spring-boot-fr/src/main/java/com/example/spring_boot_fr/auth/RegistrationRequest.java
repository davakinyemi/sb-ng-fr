package com.example.spring_boot_fr.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dave AKN
 * @version 1.0
 */
@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstname;

    @NotEmpty(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastname;

    @Email(message = "Email is not correctly formatted")
    @NotEmpty(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password should be at least 8 character long")
    private String password;
}
