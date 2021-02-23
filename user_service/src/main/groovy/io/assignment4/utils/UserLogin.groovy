package io.assignment4.utils

import io.micronaut.core.annotation.Introspected

import javax.validation.constraints.NotBlank

@Introspected
class UserLogin {
    @NotBlank
    String email
    @NotBlank
    String password
}
