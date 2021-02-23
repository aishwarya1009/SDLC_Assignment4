package io.assignment4.security

import io.assignment4.domain.User
import io.micronaut.security.authentication.UserDetails

class DelegatedUserDetails extends UserDetails{

    String sampleData
    Long userId

    DelegatedUserDetails(String username, Collection<String> roles) {
        super(username, roles)
    }

    DelegatedUserDetails(User user, Collection<String> roles, String data) {
        this(user.email, roles)
        userId = user.id
        sampleData = data
    }
}
