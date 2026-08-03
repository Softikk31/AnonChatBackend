package dev.softikk.anonchat.backend

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Application.configureRouting() {
    val sessions = mutableSetOf<DefaultWebSocketServerSession>()
    val database = Database
    routing {
        webSocket("/chat") {
            sessions.add(this)
            sendSerialized(database.messages)

            for (frame in incoming) {
                if (frame is Frame.Text) {
                    database.messages.add(
                        Message(
                            id = Uuid.generateV4(),
                            text = frame.readText(),
                            createAt = Clock.System.now().toLocalDateTime(TimeZone.UTC)
                        )
                    )
                }

                sessions.forEach { session ->
                    session.sendSerialized<List<Message>>(database.messages.toList())
                }
            }
        }
    }
}