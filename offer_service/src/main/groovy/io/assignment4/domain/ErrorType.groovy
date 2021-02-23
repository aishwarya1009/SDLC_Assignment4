package io.assignment4.domain

enum ErrorType {
    USER_NOT_FOUND('User does not exist'),
    INVALID_USER('Incorrect username or password'),
    DUPLICATE_USER('Email already in use'),
    USER_SAVE_ERROR('Failed to save user to database')

    String description

    ErrorType(String description) {
        this.description = description
    }

    String getDescription(){
        return description
    }

    @Override
    String toString() {
        return description;
    }
}