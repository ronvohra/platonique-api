package com.platonique.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.slf4j.LoggerFactory
import java.net.URI
import javax.sql.DataSource

object DatabaseFactory {
    private const val defaultJdbcUrl =
        "jdbc:postgresql://localhost:5432/user?user=user&password=password&reWriteBatchedInserts=true"
    private val logger = LoggerFactory.getLogger(Application::class.simpleName)

    fun init() {
        val pool = hikari()
        Database.connect(pool)
        runFlyway(pool)

        logger.info("Initialized and migrated database")
    }

    private fun hikari() = HikariDataSource(HikariConfig().apply {
        jdbcUrl = createJdbcUrl()
    })

    private fun createJdbcUrl(): String {
        val dbUri = URI(System.getenv("DATABASE_URL") ?: return defaultJdbcUrl)
        val username = dbUri.userInfo.split(':').toTypedArray()[0]
        val password = dbUri.userInfo.split(':').toTypedArray()[1]
        return "jdbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}?requiressl=true&user=${username}&password=${password}&reWriteBatchedInserts=true"
    }

    private fun runFlyway(dataSource: DataSource) {
        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:/com/platonique/db/migration/")
            .load()
        try {
            flyway.info()
            flyway.migrate()
        } catch (e: Exception) {
            logger.error("Exception running flyway migration", e)
            throw e
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
