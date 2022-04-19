package com.platonique.dao

import com.platonique.models.User

interface UserDAOFacade {
    suspend fun getAll(): List<User>
    suspend fun get(id: Int): User?
    suspend fun add(firstName: String, lastName: String, email: String): User?
    suspend fun update(id: Int, firstName: String, lastName: String, email: String): Boolean
    suspend fun delete(id: Int): Boolean
}
