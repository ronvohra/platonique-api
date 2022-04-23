package com.platonique.routes

import com.platonique.dao.userDao
import com.platonique.models.Gender
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.userRouting() {
    route("/user") {
        get {
            val users = userDao.getAll()
            if (users.isNotEmpty()) {
                call.respond(users)
            } else {
                call.respondText("No users found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val user =
                userDao.get(id.toInt()) ?: return@get call.respondText(
                    "No user with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(user)
        }
        post {
            val firstName = call.parameters.getOrFail("firstName")
            val lastName = call.parameters.getOrFail("lastName")
            val email = call.parameters.getOrFail("email")
            val gender = enumValueOf<Gender>(call.parameters.getOrFail("gender"))

            userDao.add(firstName, lastName, email, gender)
            call.respondText("User stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            userDao.delete(id.toInt())
            call.respondText("User removed correctly", status = HttpStatusCode.Accepted)
        }
    }
}
