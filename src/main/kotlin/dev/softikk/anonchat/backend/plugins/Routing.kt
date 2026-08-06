package dev.softikk.anonchat.backend.plugins

import dev.softikk.anonchat.backend.AnonNameColors
import dev.softikk.anonchat.backend.IS_DEBUG
import dev.softikk.anonchat.backend.database.messages.MessageEntity
import dev.softikk.anonchat.backend.dto.ChatRespondDto
import dev.softikk.anonchat.backend.exception.SessionNotFound
import dev.softikk.anonchat.backend.models.AnonName
import dev.softikk.anonchat.backend.models.SessionModel
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Application.configureRouting() {
    val sessions = mutableSetOf<SessionModel>()
    val messageEntity = MessageEntity
    routing {
        webSocket("/chat") {
            if (IS_DEBUG) println("Новый анон!")
            try {
                sessions.add(
                    SessionModel(
                        session = this, anonName = AnonName(
                            name = "Anon", index = sessions.size
                        ), anonNameColor = AnonNameColors.random()
                    )
                )
                sendSerialized<ChatRespondDto>(
                    ChatRespondDto(
                        messages = messageEntity.getMessages(), online = sessions.size
                    )
                )

                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val sessionModel = sessions.singleOrNull { it.session == this } ?: throw SessionNotFound()
                            if (frame.readText().isNotBlank()) {
                                messageEntity.addMessage(
                                    textParam = frame.readText(),
                                    anonNameParam = sessionModel.anonName,
                                    anonNameColorParam = sessionModel.anonNameColor
                                )

                                sessions.forEach { sessionModel ->
                                    sessionModel.session.sendSerialized<ChatRespondDto>(
                                        ChatRespondDto(
                                            messages = messageEntity.getMessages(), online = sessions.size
                                        )
                                    )
                                }
                            }
                        }

                        is Frame.Close -> {
                            sessions.remove(sessions.singleOrNull { it.session == this })
                            this.close()
                        }

                        else -> {}
                    }
                }

            } finally {
                sessions.remove(sessions.singleOrNull { it.session == this })
                this.close()
            }
        }
    }
}