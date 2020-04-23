package com.github.saboteur.beeline.detailservice.mapper

interface Mapper<FROM, TO> {
    operator fun get(from: FROM): TO
}