package dev.softikk.anonchat.backend.models

import io.ktor.server.websocket.*
import kotlinx.serialization.Serializable

@Serializable
data class SessionModel(
    val session: DefaultWebSocketServerSession,
    val anonName: AnonName,
    val anonNameColor: Long,
)