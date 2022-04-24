package com.platonique.di

import com.platonique.dao.UserRepository
import com.platonique.dao.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl() }
}
