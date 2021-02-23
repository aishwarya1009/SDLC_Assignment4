package io.assignment4.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Request implements GormEntity<Request> {
    String type, title, detail, requesterName, city

    RequestStatus status = RequestStatus.INITIATED

    static constraints = {
        type nullable: false, blank: false
        title nullable: false, blank: false
        detail nullable: false, blank: false
        requesterName nullable: false, blank: false
        city nullable: false, blank: false
    }

}
