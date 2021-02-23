package io.assignment4.service

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.assignment4.client.RequestsClient
import io.assignment4.domain.Offer
import io.assignment4.domain.OfferStatus
import io.assignment4.domain.Request
import io.assignment4.domain.RequestStatus
import io.assignment4.service.gorm.OfferGormService

import javax.inject.Singleton

@Singleton
@CompileStatic
@Transactional
@Slf4j
class OfferService {

    protected final OfferGormService offerGormService
    protected final RequestsClient requestsClient
    OfferService(OfferGormService offerGormService, RequestsClient requestsClient){
        this.offerGormService = offerGormService
        this.requestsClient = requestsClient
    }

    Offer save (String providerName, String message, String requestNumber,
                double price, OfferStatus status, String token){

        Request request = requestsClient.findRequestByNo(requestNumber, token)

        if(request.getStatus() == RequestStatus.INITIATED){

            offerGormService.save(providerName, message, requestNumber, request.requesterName, price, status)
        }

    }
    Offer findOfferById(long id){
        offerGormService.findById(id)
    }

    List<Offer>findOffersByRequestNo(String requestNumber){
        offerGormService.findOffersByRequestNumber(requestNumber)
    }

    List<Offer> findOffersByRequesterNoAndProviderName(String requestNumber, String requesterName){
        offerGormService.findOffersByRequesterNoAndProviderName(requestNumber, requesterName)
    }

    Offer findOfferByIdAndProviderName(Serializable id, String providerName){
        offerGormService.findOfferByIdAndProviderName(id,providerName)
    }

    Offer takeAction(long offerId, String requestNumber, String requesterName, OfferStatus offerStatus){
        Offer offer = offerGormService.findOfferByIdRequestNoRequesterName(offerId,
                            requestNumber,requesterName)
        offer.status = offerStatus
        offer.save()
    }
}
