package dev.softikk.anonchat.backend

import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureWebsockets()
    configureRouting()
}