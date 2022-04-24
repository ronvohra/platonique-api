package com.platonique.dao

import com.platonique.db.DatabaseFactory.dbQuery
import com.platonique.models.Gender
import com.platonique.models.User
import com.platonique.models.Users
import org.jetbrains.exposed.sql.*

class UserRepositoryImpl : UserRepository {
    override suspend fun getAll(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }
    override suspend fun get(id: Int): User? = dbQuery {
        Users
            .select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun add(firstName: String, lastName: String, email: String, gender: Gender): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.firstName] = firstName
            it[Users.lastName] = lastName
            it[Users.email] = email
            it[Users.gender] = gender
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun update(id: Int, firstName: String, lastName: String, email: String, gender: Gender): Boolean = dbQuery {
        Users.update({ Users.id eq id }) {
            it[Users.firstName] = firstName
            it[Users.lastName] = lastName
            it[Users.email] = email
            it[Users.gender] = gender
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        firstName = row[Users.firstName],
        lastName = row[Users.lastName],
        email = row[Users.email],
        gender = row[Users.gender],
    )
}
