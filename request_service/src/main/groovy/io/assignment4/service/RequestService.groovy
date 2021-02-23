package io.assignment4.service

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.assignment4.domain.Request
import io.assignment4.domain.RequestStatus
import io.assignment4.service.gorm.RequestGormService

import javax.inject.Singleton

@Singleton
@CompileStatic
@Transactional
@Slf4j
class RequestService {

    protected final RequestGormService requestGormService

    RequestService(RequestGormService requestGormService){
        this.requestGormService = requestGormService
    }

    Request saveRequest(String type, String title, String detail, String requesterName, String city, RequestStatus status){
        requestGormService.save(type,title,detail,requesterName,city, status)
    }

    List<Request> findAll(String requesterName){
        requestGormService.findAll(requesterName)
    }

    Request findRequestById(long id){
        requestGormService.findById(id)
    }

    List<Request>findByCity(String city){
        requestGormService.findByCity(city)
    }

    Request takeAction(long requestId, RequestStatus done){
        Request request = findRequestById(requestId)
        request.status = done
        request.save()
    }
}
