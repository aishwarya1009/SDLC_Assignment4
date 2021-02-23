package io.assignment4.utils


import grails.gorm.transactions.Transactional
import io.assignment4.domain.ErrorType
import io.assignment4.domain.User
import io.assignment4.errorHandling.UserCreationException
import io.assignment4.errorHandling.UserNotFoundException
import io.assignment4.errorHandling.UserValidationException
import io.assignment4.security.PasswordEncoder
import io.assignment4.service.gorm.UserGormService
import org.grails.datastore.mapping.validation.ValidationException

import javax.inject.Singleton

@Singleton
@Transactional
class UserDbUtil {
    protected final UserGormService userGormService
    protected final PasswordEncoder passwordEncoder

    UserDbUtil(UserGormService userGormService, PasswordEncoder passwordEncoder){
        this.userGormService = userGormService
        this.passwordEncoder = passwordEncoder
    }

    User createAndSaveUser(String firstName, String lastName, String email, String password, String role ){

        if (userGormService.findByEmail(email)) {
            throw new UserCreationException(ErrorType.DUPLICATE_USER.ordinal(),
                                            ErrorType.DUPLICATE_USER.getDescription())
        }

        final String encodedPassword = passwordEncoder.encode(password)

        try {
            userGormService.save(email, encodedPassword, firstName, lastName, role)
        } catch (ValidationException ignored) {
            throw new UserCreationException(ErrorType.USER_SAVE_ERROR.ordinal(),
                                            ErrorType.USER_SAVE_ERROR.getDescription())
        }
    }

    User validateUser(String email, String password) {
        User user = userGormService.findByEmail(email)
        if (Objects.isNull(user)) {
            throw new UserValidationException(ErrorType.INVALID_USER.ordinal(),
                                              ErrorType.INVALID_USER.getDescription())
        }
        if (!passwordEncoder.matches(password,user.password)) {
            throw new UserValidationException(ErrorType.INVALID_USER.ordinal(),
                                              ErrorType.INVALID_USER.getDescription())
        }
        return user
    }

    User getUser(long id){
        User user = userGormService.findById(id)
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(ErrorType.USER_NOT_FOUND.ordinal(),
                                            ErrorType.USER_NOT_FOUND.getDescription())
        }
        return user
    }

    User findUserByEmail(String email){
        User user = userGormService.findByEmail(email)
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(ErrorType.USER_NOT_FOUND.ordinal(),
                                            ErrorType.USER_NOT_FOUND.getDescription())
        }
        return user
    }

    @Transactional
    List<User> findAll(){
        userGormService.findAll()
    }
}

