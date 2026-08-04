package dev.softikk.anonchat.backend.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class Message(
    val id: Uuid, val text: String, val createAt: LocalDateTime
)