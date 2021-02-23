package io.assignment4.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class User implements GormEntity<User>{

    String email
    String password
    String firstName
    String lastName
    String role

    static constraints = {
        email nullable: false, blank: false, email: true
        password nullable: false, blank: false, password: true
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
        role nullable: false, blank: false
    }

    static mapping = {
        password column: '`password`'
    }

    @Override
    public String toString() {
        return "First: $firstName, Last: $lastName, Email: $email, ID: $id".toString()
    }
}