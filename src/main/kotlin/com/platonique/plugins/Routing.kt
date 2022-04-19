package com.platonique.plugins

import com.platonique.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        userRouting()
    }
}
