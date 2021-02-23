package io.assignment4.service.gorm

import grails.gorm.services.Query
import grails.gorm.services.Service
import io.assignment4.domain.Request
import io.assignment4.domain.RequestStatus

@Service(Request)
interface RequestGormService {

    Request save(String type, String title, String detail, String requesterName, String city, RequestStatus status)

    Request findById(Serializable id)

    List<Request> findAll(String requesterName)

    @Query("""select *
    from ${Request r}
    where $r.status = INITIATED""")
    List<Request> findAll()

    @Query("""
    select *
    from ${Request r}
    where $r.status = INITIATED
    and $r.city = $city
    """)
    List<Request> findByCity(String city)

    void delete(Serializable id)

    int count()
}
