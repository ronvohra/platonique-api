package com.platonique.models


/**
 * Used for okta oauth
 */
data class UserSession(
    val username: String,
    val idToken: String
)
