package com.themrfill.chiptest.utils

import java.util.Locale

private val locale = Locale.UK

// note, this will fail horribly on an empty string, but I'm ignoring it here
fun String.caps() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }

fun String.toBreedName(): String {
    if (this.contains(" ")) {
        val parts = this.lowercase(locale).split(" ")
        return parts[1]
    } else {
        return this.lowercase(locale)
    }
}

fun String.toSubBreedName(): String {
    if (this.contains(" ")) {
        val parts = this.lowercase(locale).split(" ")
        return parts[0]
    } else {
        return ""
    }
}