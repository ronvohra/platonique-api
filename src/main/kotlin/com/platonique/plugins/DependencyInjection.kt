package com.platonique.plugins

import com.platonique.di.repositoryModule
import io.ktor.server.application.*

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules = arrayListOf(
//            databaseModule,
            repositoryModule,
//            clientModule,
//            controllersModule,
//            authenticationModule,
//            emailModule,
//            bettingTipsModule,
//            gsonModule
        )
    }
}
