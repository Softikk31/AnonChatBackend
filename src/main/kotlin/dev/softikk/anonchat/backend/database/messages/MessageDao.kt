package dev.softikk.anonchat.backend.database.messages

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.UuidEntity
import org.jetbrains.exposed.v1.dao.UuidEntityClass
import kotlin.uuid.Uuid


class MessageDao(id: EntityID<Uuid>) : UuidEntity(id) {
    companion object : UuidEntityClass<MessageDao>(MessageEntity)

    var text by MessageEntity.text
    var createAt by MessageEntity.createAt
    var anonName by MessageEntity.anonName
    var anonNameColor by MessageEntity.anonNameColor
}