package dev.softikk.anonchat.backend.database.messages

import dev.softikk.anonchat.backend.IS_DEBUG
import dev.softikk.anonchat.backend.models.AnonName
import dev.softikk.anonchat.backend.models.MessageModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.UuidTable
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import kotlin.time.Clock

object MessageEntity : UuidTable("messages") {
    val text = varchar("text", 400)
    val createAt = datetime("create_at")
    val anonName = varchar("anon_name", 100)
    val anonNameColor = long("anon_name_color")

    suspend fun addMessage(textParam: String, anonNameParam: AnonName, anonNameColorParam: Long) {
        suspendTransaction {
            if (IS_DEBUG) println("Новое сообщение: $textParam")
            MessageDao.new {
                text = textParam
                createAt = Clock.System.now().toLocalDateTime(TimeZone.UTC)
                anonName = anonNameParam.name + "-" + anonNameParam.index.toString()
                anonNameColor = anonNameColorParam
            }
        }
    }

    suspend fun getMessages(): List<MessageModel> {
        return suspendTransaction {
            if (IS_DEBUG) println("Получение списка сообщений")
            MessageDao.all().map { messageDao ->
                MessageModel(
                    id = messageDao.id.value,
                    text = messageDao.text,
                    createAt = messageDao.createAt,
                    anonName = messageDao.anonName,
                    anonNameColor = messageDao.anonNameColor
                )
            }
        }
    }
}