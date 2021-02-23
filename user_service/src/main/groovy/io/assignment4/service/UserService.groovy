package io.assignment4.service


import io.assignment4.domain.User
import io.assignment4.utils.UserDbUtil
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.assignment4.errorHandling.UserCreationException
import io.assignment4.errorHandling.UserNotFoundException
import io.assignment4.errorHandling.UserValidationException

import javax.inject.Singleton

@Singleton
@CompileStatic
@Transactional
@Slf4j
class UserService {

    UserDbUtil userDbUtil

    UserService(UserDbUtil userDbUtil1){
        this.userDbUtil = userDbUtil1
    }

    User createAndSaveUser(String firstName, String lastName, String email, String password, String role ) throws UserCreationException{
        userDbUtil.createAndSaveUser(firstName, lastName, email, password, role)
    }

    User validateUser(String email, String password) throws UserValidationException{
        userDbUtil.validateUser(email, password)
    }

    User getUser(long id) throws UserNotFoundException {
        userDbUtil.getUser(id)
    }

    User findUserByEmail(String email) throws UserNotFoundException{
        userDbUtil.findUserByEmail(email)
    }

    List<User> findAll(){
        userDbUtil.findAll()
    }

}
