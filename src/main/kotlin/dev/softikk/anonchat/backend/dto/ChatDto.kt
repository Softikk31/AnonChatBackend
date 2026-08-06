package dev.softikk.anonchat.backend.dto

import dev.softikk.anonchat.backend.models.MessageModel
import kotlinx.serialization.Serializable

@Serializable
data class ChatRespondDto(
    val messages: List<MessageModel>,
    val online: Int
)