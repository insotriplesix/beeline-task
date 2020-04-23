package com.github.saboteur.beeline.profileservice.mapper

interface Mapper<FROM, TO> {
    operator fun get(from: FROM): TO
}