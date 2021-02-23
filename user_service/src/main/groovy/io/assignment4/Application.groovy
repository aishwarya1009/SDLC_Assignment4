package io.assignment4

import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic

@CompileStatic
class Application {
    static void main(String[] args) {
        Micronaut.build(args)
                .packages("io.assignment4.domain") // Necessary for hibernate gorm to find entities
                .mainClass(Application.class)
                .start()
    }
}
