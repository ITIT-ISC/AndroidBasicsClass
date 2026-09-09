package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.view

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val ES = Locale("es")

/**
 * Formatea una fecha/hora ISO-8601 UTC (ej. `2026-09-01T20:00:00Z`) a texto corto
 * en español (ej. `1 sep 2026 · 20:00`). Si no se puede parsear, devuelve el
 * string original para no romper la UI.
 *
 * Se comparte entre [ScoreCard] y [NewsCard].
 */
fun formatSportDateTime(iso: String): String = try {
    val dt = OffsetDateTime.parse(iso)
    val month = dt.month.getDisplayName(TextStyle.SHORT, ES)
    "${dt.dayOfMonth} $month ${dt.year} · ${dt.format(DateTimeFormatter.ofPattern("HH:mm"))}"
} catch (_: Exception) {
    iso
}

/**
 * Formatea una fecha sin hora (`yyyy-MM-dd`, ej. `2026-09-20`) a texto en español
 * (ej. `20 sep 2026`). Usado por [EventCard] para el rango del torneo.
 */
fun formatSportDate(date: String): String = try {
    val d = LocalDate.parse(date)
    "${d.dayOfMonth} ${d.month.getDisplayName(TextStyle.SHORT, ES)} ${d.year}"
} catch (_: Exception) {
    date
}
