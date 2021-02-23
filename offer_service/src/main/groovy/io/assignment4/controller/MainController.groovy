package io.assignment4.controller

import io.assignment4.client.RequestsClient
import io.assignment4.domain.Offer
import io.assignment4.domain.OfferStatus
import io.assignment4.domain.Request
import io.assignment4.domain.constants.RoleType
import io.assignment4.service.OfferService
import io.assignment4.utils.UserOffer
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured

import javax.validation.Valid
import java.security.Principal

@Controller("/api")
class MainController {
    OfferService offerService
    RequestsClient requestsClient

    MainController(OfferService offerService, RequestsClient requestsClient) {
        this.offerService = offerService
        this.requestsClient = requestsClient
    }

    HTTPSingleResponse fail(int code, String message) {
        return new HTTPSingleResponse(success: false, errorCodes: [code], errorMessages: [message])
    }

    @Secured(RoleType.SERVICE_PROVIDER)
    @Post("/submit")
    HTTPSingleResponse  saveRequest(@Body @Valid UserOffer cmd, Principal principal,
                                    @Header("Authorization") String authorization) {

        Offer offer = offerService.save(principal.getName(),cmd.getMessage(),
        cmd.getRequestNumber(),cmd.getPrice(), OfferStatus.SENT, authorization)
        HTTPSingleResponse response = new HTTPSingleResponse(
                success: true,
                data: [
                        message: "Offer Saved",
                        id: offer.id,
                        requester: offer.requesterName,
                        provider: offer.providerName
                ]
        )
        return response
    }

    @Secured(RoleType.SERVICE_PROVIDER)
    @Get("/requests/get")
    List<Request> findAll(@Header("Authorization") String authorization){
        return requestsClient.findAll(authorization)

    }

    @Secured([RoleType.SERVICE_PROVIDER, RoleType.USER])
    @Get("/offers/{requestId}")
    List<Offer> findOffersByRequestNo(@PathVariable(value = "requestId") String requestId)
    {
        return offerService.findOffersByRequestNo(requestId)
    }

    @Secured([RoleType.SERVICE_PROVIDER, RoleType.USER])
    @Get("/offers/reject/{requestId}/{offerId}")
    String rejectOffer(@PathVariable(value = "requestId") String requestId, @PathVariable(value = "offerId") String offerId, Principal principal)
    {
        return offerService.takeAction(Integer.valueOf(offerId), requestId, principal.getName(), OfferStatus.REJECTED)
    }

    @Secured(RoleType.USER)
    @Get("/offers/accept/{requestId}/{offerId}")
    String acceptOffer(@PathVariable(value = "requestId") String requestId, @PathVariable(value = "offerId") String offerId, Principal principal){
        return offerService.takeAction(Integer.valueOf(offerId), requestId, principal.getName(), OfferStatus.ACCEPTED)

    }

}
