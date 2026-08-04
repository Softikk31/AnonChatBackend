package dev.softikk.anonchat.backend.plugins

import dev.softikk.anonchat.backend.database.messages.MessageEntity
import dev.softikk.anonchat.backend.models.Message
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Application.configureRouting() {
    val sessions = mutableSetOf<DefaultWebSocketServerSession>()
    val messageEntity = MessageEntity
    routing {
        webSocket("/chat") {
            try {
                sessions.add(this)
                sendSerialized(messageEntity.getMessages())

                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            messageEntity.addMessage(frame.readText())

                            sessions.forEach { session ->
                                session.sendSerialized<List<Message>>(messageEntity.getMessages())
                            }
                        }

                        is Frame.Close -> {
                            sessions.remove(this)
                            this.close()
                        }

                        else -> {}
                    }
                }

            } finally {
                sessions.remove(this)
                this.close()
            }
        }
    }
}