package io.assignment4.client

import io.assignment4.domain.Request
import io.assignment4.domain.constants.RoleType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.CircuitBreaker
import io.micronaut.security.annotation.Secured

@Client(id="request-service", path = "/api")
public interface RequestsClient {

    @Secured([RoleType.SERVICE_PROVIDER, RoleType.USER])
    @CircuitBreaker(reset = "30s",attempts = "2")
    @Get("/requests/{requestId}")
    Request findRequestByNo(@PathVariable(value ="requestId" ) String requestNo, @Header("Authorization") String authorization)

    @Get("/requests/get")
    @CircuitBreaker(reset = "30s",attempts = "2")
    Request findAll(@Header("Authorization") String authorization)
}