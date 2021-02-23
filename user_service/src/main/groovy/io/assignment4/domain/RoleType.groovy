package io.assignment4.domain

enum RoleType {
    USER('User'),
    SERVICE_PROVIDER('Service provider')

    String name

    RoleType(String name) {
        this.name = name
    }

    String getName(){
        return name
    }

    @Override
    String toString(){
        return name;
    }
}