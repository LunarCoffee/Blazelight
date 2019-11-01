package dev.lunarcoffee.blazelight.site.routes

import dev.lunarcoffee.blazelight.shared.language.s
import dev.lunarcoffee.blazelight.site.std.breadcrumbs.breadcrumbs
import dev.lunarcoffee.blazelight.site.templates.HeaderBarTemplate
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.*

fun Routing.loginRoute() = get("/login") {
    val specialMessages = listOf(s.invalidUsernameOrPassword, s.loginToContinue)
    val messageIndex = call.parameters["a"]?.toIntOrNull()

    call.respondHtmlTemplate(HeaderBarTemplate(s.login, call, s)) {
        content {
            breadcrumbs { thisCrumb(call, s.login) }
            br()

            h3 { b { +s.loginHeading } }
            hr()
            form(action = "/login", method = FormMethod.post) {
                input(type = InputType.text, name = "username", classes = "fi-text fi-top") {
                    placeholder = s.username
                }
                br()
                input(type = InputType.password, name = "password", classes = "fi-text") {
                    placeholder = s.password
                }
                hr()
                input(type = InputType.submit, classes = "button-1") { value = s.login }

                // This message will be displayed upon a special login event. The possible events
                // include a permission redirection (user must log in to perform action) and in the
                // case of invalid credentials.
                if (messageIndex in specialMessages.indices)
                    span(classes = "red") { +specialMessages[messageIndex!!] }
            }
        }
    }
}
