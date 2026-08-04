package dev.softikk.anonchat.backend.plugins

import io.ktor.server.application.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun Application.configureStartup() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Moscow"))
    println()
    println(
        """                                 
        ▄████▄ ▄▄  ▄▄  ▄▄▄  ▄▄  ▄▄ ▄█████ ▄▄ ▄▄  ▄▄▄ ▄▄▄▄▄▄ 
        ██▄▄██ ███▄██ ██▀██ ███▄██ ██     ██▄██ ██▀██  ██   
        ██  ██ ██ ▀██ ▀███▀ ██ ▀██ ▀█████ ██ ██ ██▀██  ██
        ___________________________________________________
                       Author - @softikk                 
        https://github.com/Softikk31 & https://t.me/softikk
        Kotlin: ${KotlinVersion.CURRENT} version
        Started: ${now.time.hour.dateFormatter()}:${now.time.minute.dateFormatter()} ${now.day.dateFormatter()}.${now.month.number.dateFormatter()}.${now.year}
        ___________________________________________________
        
        ✅ Start completed
    """.trimIndent()
    )
    println()
}

fun Int.dateFormatter(): String = if (this.toString().length == 1) "0$this" else this.toString()