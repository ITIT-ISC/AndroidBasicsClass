package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.view

import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Convierte un color en hex del backend (ej. `"#2e7d32"`) a un [Color] de Compose.
 * Si el string es inválido, devuelve gris para no reventar el pie chart.
 *
 * Se comparte entre [CategoryPieChart] y [ExpenseCard].
 */
fun parseHexColor(hex: String): Color = try {
    Color(android.graphics.Color.parseColor(hex))
} catch (_: Exception) {
    Color(0xFF9E9E9E)
}

/**
 * Formatea una fecha `"YYYY-MM-DD"` a texto corto en español (ej. `7 sep 2026`).
 * Si no se puede parsear, devuelve el string original.
 */
fun formatExpenseDate(date: String): String = try {
    val d = LocalDate.parse(date)
    "${d.dayOfMonth} ${d.month.getDisplayName(TextStyle.SHORT, Locale("es"))} ${d.year}"
} catch (_: Exception) {
    date
}
