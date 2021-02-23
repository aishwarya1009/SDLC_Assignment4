package io.assignment4.service.gorm

import grails.gorm.services.Query
import grails.gorm.services.Service
import io.assignment4.domain.Offer
import io.assignment4.domain.OfferStatus
import io.assignment4.domain.Request
import io.assignment4.domain.RequestStatus

@Service(Offer)
interface OfferGormService {

    Offer save(String providerName, String message, String requestNumber, String requesterName, double price, OfferStatus status)

    Offer findById(Serializable id)

    List<Offer> findOffersByRequestNumber(String requestNumber)

    List<Offer> findOffersByRequesterNoAndProviderName(String requestNumber, String requesterName)

    Offer findOfferByIdAndProviderName(Serializable id, String providerName)

    Offer findOfferByIdRequestNoRequesterName(Serializable id,String requestNumber, String requesterName)

    void delete(Serializable id)

    int count()
}
