package dev.lunarcoffee.blazelight.shared

object LanguageManager {
    fun toLanguage(str: String) = Language.values()[str.toInt()]!!
}
