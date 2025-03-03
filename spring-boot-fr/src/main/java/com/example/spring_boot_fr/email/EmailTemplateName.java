package com.example.spring_boot_fr.email;

import lombok.Getter;

/**
 * @author Dave AKN
 * @version 1.0
 */
@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account")

    ;
    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
