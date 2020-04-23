package com.github.saboteur.beeline.profileservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProfileServiceApp
fun main(args: Array<String>) {
    runApplication<ProfileServiceApp>(*args)
}