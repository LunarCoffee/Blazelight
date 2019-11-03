package dev.lunarcoffee.blazelight.site.std.bbcode

// Reads BBCode formatting tokens from some raw post content.
class BBCodeLexer(val text: String) {
    private var pos = 0
    private var curChar = text[pos]

    fun nextToken(prevIsBracket: Boolean = false): BBCodeToken {
        return if (prevIsBracket) {
            when (curChar) {
                '/' -> {
                    readNext() // Skip slash.
                    val tagName = readWhile("""[^]]""".toRegex())
                    if (tagName in TAG_NAMES)
                        BcTCloseTag(tagName).also { readNext() }
                    else
                        BcTText("[/$tagName")
                }
                ']' -> {
                    readNext()
                    BcTText("[]")
                }
                else -> {
                    val tagContent = readWhile("""[^]]""".toRegex())
                    val tagName = tagContent.substringBefore("=")
                    val tagArg = tagContent.substringAfter("=", "")

                    if (tagName in TAG_NAMES) {
                        if (tagName == "url")
                            // TODO: we need some kind of "peekToken" to see the next token
                            BcTOpenTag(tagName, tagArg.ifEmpty { tagContent })
                                .also { readNext() }
                        else
                            BcTOpenTag(tagName, tagArg.ifEmpty { null }).also { readNext() }
                    } else {
                        BcTText("[$tagContent")
                    }
                }
            }
        } else {
            when (curChar) {
                '[' -> {
                    readNext()
                    nextToken(true)
                }
                '\u0000' -> BcTEof
                else -> BcTText(readWhile("""[^\[]""".toRegex()))
            }
        }
    }

    private fun readWhile(regex: Regex): String {
        var string = ""
        while (curChar.toString() matches regex && curChar != '\u0000') {
            string += curChar
            readNext()
        }
        return string
    }

    private fun readNext() {
        pos++
        curChar = if (pos > text.lastIndex) '\u0000' else text[pos]
    }

    companion object {
        private val TAG_NAMES = setOf("b", "i", "u", "s", "url")
    }
}
