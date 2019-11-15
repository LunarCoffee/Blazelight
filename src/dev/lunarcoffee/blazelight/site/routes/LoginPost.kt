package dev.lunarcoffee.blazelight.site.routes

import dev.lunarcoffee.blazelight.model.api.users.registrar.UserLoginManager
import dev.lunarcoffee.blazelight.model.api.users.registrar.UserLoginSuccess
import dev.lunarcoffee.blazelight.site.std.sessions.UserSession
import io.ktor.application.call
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.sessions.sessions
import io.ktor.sessions.set

fun Routing.loginPost() = post("/login") {
    val params = call.receiveParameters()

    val username = params["username"]!!
    val password = params["password"]!!

    val loginResult = UserLoginManager.tryLogin(username, password)
    if (loginResult is UserLoginSuccess) {
        call.sessions.set(UserSession(loginResult.id))
        call.respondRedirect("/")
    } else {
        return@post call.respondRedirect("/login?a=0")
    }
}
