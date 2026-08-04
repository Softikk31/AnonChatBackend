package dev.softikk.anonchat.backend.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.softikk.anonchat.backend.database.messages.MessageEntity
import io.ktor.server.application.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun Application.configureDatabase() {
    val host = System.getenv("ps_host")
    val port = System.getenv("ps_port")
    val databaseName = System.getenv("ps_database_name")
    val username = System.getenv("ps_username")
    val password = System.getenv("ps_password")

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