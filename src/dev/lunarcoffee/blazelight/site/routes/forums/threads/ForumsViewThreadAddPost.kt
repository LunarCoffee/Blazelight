package dev.lunarcoffee.blazelight.site.routes.forums.threads

import dev.lunarcoffee.blazelight.model.api.comments.CommentAddManager
import dev.lunarcoffee.blazelight.model.api.comments.CommentAddResult
import dev.lunarcoffee.blazelight.model.api.users.getUser
import dev.lunarcoffee.blazelight.model.internal.forums.UserComment
import dev.lunarcoffee.blazelight.site.std.sessions.UserSession
import io.ktor.application.call
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.forumsViewThreadAddPost() = post("/forums/view/{forumId}/{threadId}/add") {
    val params = call.parameters

    val content = call.receiveParameters()["content"]!!
    val forumId = params["forumId"]!!.toLong()
    val threadId = params["threadId"]!!.toLong()
    val user = call.sessions.get<UserSession>()!!.getUser()!!

    val comment = UserComment(content, user.id, threadId)
    call.respondRedirect(
        if (CommentAddManager.add(comment, threadId) == CommentAddResult.INVALID_CONTENT)
            "/forums/view/$forumId/$threadId/add?a=0"
        else
            "/forums/view/$forumId/$threadId"
    )
}
