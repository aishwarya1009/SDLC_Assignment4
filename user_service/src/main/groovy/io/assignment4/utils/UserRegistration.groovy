package io.assignment4.utils

import io.micronaut.core.annotation.Introspected

import javax.validation.constraints.NotBlank

@Introspected
class UserRegistration {
    @NotBlank
    String email
    @NotBlank
    String password
    @NotBlank
    String firstName
    @NotBlank
    String lastName
    @NotBlank
    String role
}
