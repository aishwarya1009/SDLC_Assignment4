package io.assignment4.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Offer implements GormEntity<Offer> {
    String providerName, message, requestNumber, requesterName;
    double price;
    OfferStatus status = OfferStatus.SENT;

    static constraints = {
        providerName nullable: false, blank: false
        message nullable: false, blank: false
        requestNumber nullable: false, blank: false
        requesterName nullable: false, blank: false
        price nullable: false, blank: false

    }

}
