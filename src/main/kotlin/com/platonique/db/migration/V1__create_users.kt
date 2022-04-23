package com.platonique.db.migration

import com.platonique.models.Gender
import com.platonique.models.Users
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class V1__create_users: BaseJavaMigration() {
    override fun migrate(context: Context?) {
//        val genderStrList = Gender.values().joinToString(prefix = "'", postfix = "'") { it.toString() }
        transaction {
//            exec("CREATE TYPE genderEnum AS ENUM (${genderStrList});")
            SchemaUtils.create(Users)

            Users.insert {
                it[firstName] = "John"
                it[lastName] = "Deer"
                it[email] = "john.deer@email.com"
                it[gender] = Gender.MALE
            }
            Users.insert {
                it[firstName] = "Jane"
                it[lastName] = "Doe"
                it[email] = "jane.doe@email.com"
                it[gender] = Gender.FEMALE
            }
        }
    }
}
