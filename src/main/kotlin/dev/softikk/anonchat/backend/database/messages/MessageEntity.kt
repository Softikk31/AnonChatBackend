package dev.softikk.anonchat.backend.database.messages

import dev.softikk.anonchat.backend.models.Message
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.UuidTable
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import kotlin.time.Clock

object MessageEntity : UuidTable("messages") {
    val text = varchar("text", 400)
    val createAt = datetime("create_at")

    suspend fun addMessage(textParam: String) {
        suspendTransaction {
            MessageDao.new {
                text = textParam
                createAt = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            }
        }
    }

    suspend fun getMessages(): List<Message> {
        return suspendTransaction {
            MessageDao.all().map { messageDao ->
                Message(
                    id = messageDao.id.value, text = messageDao.text, createAt = messageDao.createAt
                )
            }
        }
    }
}