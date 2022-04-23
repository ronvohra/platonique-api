package com.platonique.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(val id: Int, val firstName: String, val lastName: String, val email: String, val gender: Gender)

enum class Gender { MALE, FEMALE, NON_BINARY }

object Users : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)
    val email = varchar("email", 256).uniqueIndex()
    val gender = enumeration("gender", Gender::class)

    override val primaryKey = PrimaryKey(id)
}
