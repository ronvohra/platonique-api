package com.platonique.plugins

import com.platonique.di.repositoryModule
import org.koin.core.context.GlobalContext.startKoin

fun configureDependencyInjection() {
    startKoin {
        modules(
//            databaseModule,
            repositoryModule,
//            clientModule,
//            controllersModule,
//            authenticationModule,
        )
    }
}
