package com.platonique.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(val id: Int, val firstName: String, val lastName: String, val email: String)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)
    val email = varchar("email", 256)

    override val primaryKey = PrimaryKey(id)
}
