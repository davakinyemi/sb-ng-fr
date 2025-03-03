package com.example.spring_boot_fr.auth;

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
public class AuthenticationResponse {
    private String token;
}
