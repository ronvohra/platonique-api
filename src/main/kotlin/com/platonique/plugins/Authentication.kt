package com.platonique.plugins

import com.auth0.jwk.JwkProviderBuilder
import com.platonique.utils.validateCreds
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.concurrent.TimeUnit

fun Application.configureAuthentication() {
    val jwkProvider = JwkProviderBuilder(System.getenv("ISSUER"))
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    install(Authentication) {
        jwt("auth0") {
            verifier(jwkProvider, java.lang.System.getenv("ISSUER"))
            validate { credential -> validateCreds(credential) }
        }
    }
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowHeader("authorization")
        allowCredentials = true
        allowNonSimpleContentTypes = true

    }

    routing {
        get("/api/messages/public") {
            call.respondText(
                """{"message": "The API doesn't require an access token to share this message."}""",
                contentType = ContentType.Application.Json
            )
        }
    }

    routing {
        authenticate("auth0") {
            get("/api/messages/protected") {
                call.respondText(
                    """{"message": "The API successfully validated your access token."}""",
                    contentType = ContentType.Application.Json
                )
            }
        }
    }

    routing {
        authenticate("auth0") {
            get("/api/messages/admin") {
                call.respondText(
                    """{"message": "The API successfully recognized you as an admin."}""",
                    contentType = ContentType.Application.Json
                )
            }
        }
    }
}
