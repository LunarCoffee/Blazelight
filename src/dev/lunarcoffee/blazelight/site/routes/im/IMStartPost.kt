package dev.lunarcoffee.blazelight.site.routes.im

import dev.lunarcoffee.blazelight.model.api.imdatalist.IMDataListManager
import dev.lunarcoffee.blazelight.model.api.imdatalist.getIMDataList
import dev.lunarcoffee.blazelight.model.api.users.getUser
import dev.lunarcoffee.blazelight.model.internal.users.im.UserIMData
import dev.lunarcoffee.blazelight.site.std.sessions.UserSession
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Route.imStartPost() = post("/im/start") {
    val user = call.sessions.get<UserSession>()!!.getUser()!!
    val params = call.receiveParameters()

    val recipientName = params["username"] ?: return@post call.respond(HttpStatusCode.NotFound)
    if (user.username == recipientName)
        return@post call.respondRedirect("/im?a=1")

    val recipient = recipientName.getUser() ?: return@post call.respondRedirect("/im?a=0")
    val imDataAuthor = UserIMData(user.id, recipient.id)
    val imDataRecipient = UserIMData(recipient.id, user.id)

    IMDataListManager.run {
        update(user.imDataListId.getIMDataList()!!.apply { data += imDataAuthor })
        update(recipient.imDataListId.getIMDataList()!!.apply { data += imDataRecipient })
    }
    call.respondRedirect("/im") // TODO: /im/{imDataAuthor.id}/
}
