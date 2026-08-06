package dev.softikk.anonchat.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class AnonName(
    val name: String = "Anon",
    val index: Int
)