package io.assignment4.controller

import io.assignment4.client.OffersClient
import io.assignment4.domain.Offer
import io.assignment4.domain.Request
import io.assignment4.domain.RequestStatus
import io.assignment4.domain.constants.RoleType
import io.assignment4.service.RequestService
import io.assignment4.utils.UserRequest
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured

import javax.validation.Valid
import java.security.Principal

@Controller("/api")
class MainController {
    RequestService requestService

    OffersClient offersClient

    MainController(RequestService requestService, OffersClient offersClient) {
        this.requestService = requestService
        this.offersClient = offersClient
    }

    HTTPSingleResponse fail(int code, String message) {
        return new HTTPSingleResponse(success: false, errorCodes: [code], errorMessages: [message])
    }

    @Secured(RoleType.USER)
    @Post("/submit")
    HTTPSingleResponse  saveRequest(@Body @Valid UserRequest cmd, Principal principal) {

        Request request = requestService.saveRequest(cmd.getType(),cmd.getTitle(),cmd.getDetail(),
                principal.getName(),cmd.getCity(), RequestStatus.INITIATED)
        HTTPSingleResponse response = new HTTPSingleResponse(
                success: true,
                data: [
                        message: "User saved",
                        id: request.id,
                        requester: request.requesterName
                ]
        )
        return response
    }

    @Secured([RoleType.SERVICE_PROVIDER, RoleType.USER])
    @Get("/requests/{requestId}")
    Request findRequestByNo(@PathVariable(value ="requestId" ) String requestId){

        Request request = requestService.findRequestById(Integer.valueOf(requestId))
        return request
    }

    @Secured([RoleType.SERVICE_PROVIDER, RoleType.USER])
    @Get("/requests/getAll")
    List<Request> findAll(){
        return requestService.findAll();
    }

    @Secured([RoleType.SERVICE_PROVIDER, RoleType.USER])
    @Get("/requests/getRequestIn{city}")
    List<Request> findByCity(@PathVariable("city") String city){
        return requestService.findByCity(city);
    }

    @Secured(RoleType.USER)
    @Get("/requests/")
    List<Request> findAll(Principal principal){
        return requestService.findAll(principal.getName())

    }

    @Secured(RoleType.USER)
    @Get("/requests/reject/{requestId}/{offerId}")
    String rejectOffer(@PathVariable(value = "requestId") String requestId, @PathVariable(value = "offerId") String offerId, Principal principal, @Header("Authorization") String authorization){
        return offersClient.rejectOffer(requestId, offerId, authorization)
    }

    @Secured(RoleType.USER)
    @Get("/requests/accept/{requestId}/{offerId}")
    String acceptOffer(@PathVariable(value = "requestId") String requestId, @PathVariable(value = "offerId") String offerId,Principal principal,  @Header("Authorization") String authorization)
    {

        String acceptingOfferMessage =  offersClient.acceptOffer(requestId, offerId, authorization)

        if(acceptingOfferMessage.toLowerCase().contains("success"))
        {
            requestService.takeAction(Integer.valueOf(requestId), RequestStatus.DONE)
            return "success"
        }
        else
            return "failed"

    }

    @Secured(RoleType.USER)
    @Get("/offers/{requestNo}")
    List<Offer> getOffers(@PathVariable("requestNo") String requestNo, Principal principal, @Header("Authorization") String authentication)
    {
        return offersClient.findOffersByRequestNo(requestNo, authentication)
    }
}
