package io.assignment4.client

import io.assignment4.domain.Offer
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.CircuitBreaker

@Client(id="offer-service", path = "/api")
public interface OffersClient {

    @Get("/offers/request/{requestId}")
    @CircuitBreaker(reset = "30s",attempts = "2")
    List<Offer> findOffersByRequestNo(@PathVariable(value="requestId") String requestId, @Header("Authorization") String authorization)

    @Get("/offers/offer/{offerNo}")
    @CircuitBreaker(reset = "30s",attempts = "2")
    Offer findOfferById(@PathVariable(value = "offerNo") String offerNo, @Header("Authorization") String authorization)


    @CircuitBreaker(reset = "30s",attempts = "2")
    @Get("/offers/reject/{requestId}/{offerId}")
    String rejectOffer(@PathVariable(name = "requestId") String requestId,
                                      @PathVariable(name = "offerId") String offerId, @Header("Authorization") String authorization)

    @CircuitBreaker(reset = "30s",attempts = "2")
    @Get("/offers/accept/{requestId}/{offerId}")
    String acceptOffer(@PathVariable(name = "requestId") String requestId,
                                      @PathVariable(name = "offerId") String offerId, @Header("Authorization") String authorization)

}
