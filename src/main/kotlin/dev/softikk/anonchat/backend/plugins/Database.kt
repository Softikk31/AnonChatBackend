package dev.softikk.anonchat.backend.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.softikk.anonchat.backend.database.messages.MessageEntity
import io.ktor.server.application.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun Application.configureDatabase() {
    val host = System.getenv("PS_HOST")
    val port = System.getenv("PS_PORT")
    val databaseName = System.getenv("PS_DATABASE_NAME")
    val username = System.getenv("PS_USERNAME")
    val password = System.getenv("PS_PASSWORD")

    val config = HikariConfig().apply {
        this.jdbcUrl = "jdbc:postgresql://$host:$port/$databaseName"
        this.driverClassName = "org.postgresql.Driver"
        this.username = username
        this.password = password
    }
    Database.connect(HikariDataSource(config))
    transaction {
        SchemaUtils.create(MessageEntity)
    }
}