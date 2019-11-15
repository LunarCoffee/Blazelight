package dev.lunarcoffee.blazelight.model.internal.forums

import dev.lunarcoffee.blazelight.model.internal.std.Dateable
import dev.lunarcoffee.blazelight.model.internal.std.Identifiable

interface Comment : Dateable, Identifiable {
    var contentRaw: String
    val authorId: Long
    val threadId: Long
}
