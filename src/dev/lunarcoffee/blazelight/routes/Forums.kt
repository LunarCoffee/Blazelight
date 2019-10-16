package dev.lunarcoffee.blazelight.routes

import dev.lunarcoffee.blazelight.routes.templates.HeaderBarTemplate
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.p

fun Routing.forumsRoute() = get("/forums") {
    call.respondHtmlTemplate(HeaderBarTemplate("Forums", call)) {
        content {
            p { +"vous etes cringe" }
        }
    }
}
