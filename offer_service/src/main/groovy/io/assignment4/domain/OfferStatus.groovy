package io.assignment4.domain

enum OfferStatus {
    ACCEPTED('Accepted'),
    REJECTED('Rejected'),
    SENT('Sent')

    String name

    OfferStatus(String name) {
        this.name = name
    }

    String getName(){
        return name
    }
}