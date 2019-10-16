package dev.lunarcoffee.blazelight.model.internal.forums

import dev.lunarcoffee.blazelight.model.internal.util.UniqueIDGenerator

class DefaultForum(override val name: String, override val categoryId: Long) : Forum {
    override val threadIds = mutableListOf<Long>()

    override val creationTime = System.currentTimeMillis()
    override val id = UniqueIDGenerator.nextId()
}
