package io.assignment4.domain

enum RequestStatus {
    INITIATED('Initiated'),
    DONE('Done'),
    CANCELLED('Cancelled')

    String name

    RequestStatus(String name) {
        this.name = name
    }

    String getName(){
        return name
    }
}