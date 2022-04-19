package com.platonique

import com.platonique.db.DatabaseFactory
import com.platonique.plugins.configureMonitoring
import com.platonique.plugins.configureRouting
import com.platonique.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    configureRouting()
    configureSerialization()
    configureMonitoring()
}
