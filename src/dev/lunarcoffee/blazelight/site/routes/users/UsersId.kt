package dev.lunarcoffee.blazelight.site.routes.users

import dev.lunarcoffee.blazelight.model.api.users.getUser
import dev.lunarcoffee.blazelight.site.std.breadcrumbs.breadcrumbs
import dev.lunarcoffee.blazelight.site.std.padding
import dev.lunarcoffee.blazelight.site.std.sessions.UserSession
import dev.lunarcoffee.blazelight.site.std.toTimeDay
import dev.lunarcoffee.blazelight.site.templates.HeaderBarTemplate
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import kotlinx.html.*

fun Routing.usersIdRoute() = get("/users/{id}") {
    val user = call.parameters["id"]?.toLongOrNull()?.getUser()
        ?: return@get call.respond(HttpStatusCode.NotFound)

    call.respondHtmlTemplate(HeaderBarTemplate(user.username, call)) {
        content {
            breadcrumbs {
                crumb("/users", "Users")
                thisCrumb(call, user.username)
            }
            br()

            h3(classes = "title") { b { +user.username } }
            hr()
            p {
                +"Posts: ${user.commentIds.size}"
                br()
                +"Joined: ${user.creationTime.toTimeDay(user)}"
            }
            p { +user.username }
            p { +(user.realName ?: "(unset)") }
            p { +(user.description ?: "(unset)") }
            p { +(user.settings.zoneId.id ?: "(unset)") }
            p { +(user.settings.language.name) }
            p { +(user.settings.theme) }

            // Show a settings button if the viewer is viewing their own profile page.
            if (user.id == call.sessions.get<UserSession>()?.getUser()?.id) {
                padding(16)
                a(href = "/users/${user.id}/settings", classes = "button-1") { +"Settings" }
                padding(8)
            }
        }
    }
}