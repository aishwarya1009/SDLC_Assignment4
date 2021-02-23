package io.assignment4.service.gorm


import io.assignment4.domain.User
import grails.gorm.services.Service

@Service(User)
interface UserGormService {

    User save(String email, String password, String firstName, String lastName, String role)

    User findByEmail(String email)

    User findById(Serializable id)

    List<User> findAll()

    void delete(Serializable id)

    int count()
}
