package dev.softikk.anonchat.backend

import dev.softikk.anonchat.backend.plugins.configureDatabase
import dev.softikk.anonchat.backend.plugins.configureRouting
import dev.softikk.anonchat.backend.plugins.configureSerialization
import dev.softikk.anonchat.backend.plugins.configureStartup
import dev.softikk.anonchat.backend.plugins.configureWebsockets
import io.ktor.server.application.Application
import io.ktor.server.cio.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureStartup()
    configureDatabase()
    configureSerialization()
    configureWebsockets()
    configureRouting()
}