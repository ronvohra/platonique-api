package com.platonique.dao

import com.platonique.models.Gender
import com.platonique.models.User

interface UserDAOFacade {
    suspend fun getAll(): List<User>
    suspend fun get(id: Int): User?
    suspend fun add(firstName: String, lastName: String, email: String, gender: Gender): User?
    suspend fun update(id: Int, firstName: String, lastName: String, email: String, gender: Gender): Boolean
    suspend fun delete(id: Int): Boolean
}
