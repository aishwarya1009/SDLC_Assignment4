package io.assignment4.utils

import io.micronaut.core.annotation.Introspected

import javax.validation.constraints.NotBlank

@Introspected
class UserOffer {
    @NotBlank
    String requestNumber
    @NotBlank
    String message
    double price
}
