package io.assignment4.utils

import io.micronaut.core.annotation.Introspected

import javax.validation.constraints.NotBlank

@Introspected
class UserRequest {
    @NotBlank
    String type
    @NotBlank
    String title
    @NotBlank
    String detail
    @NotBlank
    String city
}
