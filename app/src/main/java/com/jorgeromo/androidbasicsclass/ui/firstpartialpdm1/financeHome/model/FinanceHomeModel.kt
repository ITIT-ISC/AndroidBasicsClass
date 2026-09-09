package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.model

/**
 * Datos que devuelve `GET /api/home/finance/` (endpoint público, sin token).
 *
 * Solo son datos: ni red ni UI. La red vive en `financeHome/network/FinanceService`
 * y el estado en `financeHome/viewmodel/FinanceHomeViewModel`.
 *
 * Notas del backend:
 * - `byCategory` alimenta el pie chart: `percentage` (ya en %, Float) para el
 *   ángulo; `color` es hex listo para `Color.parseColor(...)`. La suma de
 *   `percentage` puede dar 99.9–100.1 por redondeo.
 * - `members[0]` siempre es `key = "familia"` (la pestaña "Todos"); el resto son
 *   los 4 integrantes. `key` ∈ `familia | papa | mama | hijo1 | hijo2`.
 * - `expenses`: máx. 5, más reciente primero. `date` es `"YYYY-MM-DD"`.
 * - El `id` de gasto sí es único global.
 */

/**
 * Resumen del periodo.
 *
 * @property period Etiqueta del periodo (ej. `"Septiembre 2026"`).
 * @property currency Código de moneda (ej. `"MXN"`).
 * @property total Gasto total como número.
 * @property totalText Total ya formateado (ej. `"$15,645.00"`).
 * @property expenseCount Número de gastos del periodo.
 */
data class FinanceSummary(
    val period: String,
    val currency: String,
    val total: Double,
    val totalText: String,
    val expenseCount: Int
)

/**
 * Una rebanada del desglose por categoría.
 *
 * @property category Nombre de la categoría (ej. `"Educación"`).
 * @property amount Monto como número.
 * @property amountText Monto ya formateado.
 * @property percentage Porcentaje del total, ya en % (ej. `21.5`).
 * @property color Color en hex listo para `android.graphics.Color.parseColor` (ej. `"#2e7d32"`).
 */
data class CategoryBreakdown(
    val category: String,
    val amount: Double,
    val amountText: String,
    val percentage: Float,
    val color: String
)

/**
 * Un gasto individual.
 *
 * @property id Identificador único global del gasto.
 * @property concept Concepto (ej. `"Despensa semanal"`).
 * @property category Categoría a la que pertenece.
 * @property member Nombre del integrante que hizo el gasto (para mostrar).
 * @property amount Monto como número.
 * @property amountText Monto ya formateado.
 * @property date Fecha en `"YYYY-MM-DD"`.
 * @property image URL de la imagen.
 */
data class Expense(
    val id: Int,
    val concept: String,
    val category: String,
    val member: String,
    val amount: Double,
    val amountText: String,
    val date: String,
    val image: String
)

/**
 * Un integrante (o "Familia" para el total). Trae sus últimos gastos.
 *
 * @property key Clave estable: `familia | papa | mama | hijo1 | hijo2`.
 * @property name Nombre para mostrar.
 * @property avatar URL del avatar.
 * @property total Gasto total del integrante como número.
 * @property totalText Total ya formateado.
 * @property expenseCount Número de gastos del integrante en el periodo.
 * @property expenses Últimos gastos (máx. 5, más reciente primero).
 */
data class FinanceMember(
    val key: String,
    val name: String,
    val avatar: String,
    val total: Double,
    val totalText: String,
    val expenseCount: Int,
    val expenses: List<Expense>
)

/**
 * Cuerpo completo de la respuesta de `GET /api/home/finance/`.
 *
 * @property summary Resumen del periodo.
 * @property byCategory Desglose por categoría (para el pie chart).
 * @property members `[0]` es "Familia"; el resto son los integrantes.
 */
data class FinanceHomeResponse(
    val summary: FinanceSummary,
    val byCategory: List<CategoryBreakdown>,
    val members: List<FinanceMember>
)

/**
 * Estado observable de la pantalla de finanzas. Mismo patrón que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model.PetsHomeUiState].
 *
 * @property selectedMemberKey Integrante seleccionado en las pestañas (por defecto
 *   `"familia"`).
 */
data class FinanceHomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val summary: FinanceSummary? = null,
    val byCategory: List<CategoryBreakdown> = emptyList(),
    val members: List<FinanceMember> = emptyList(),
    val selectedMemberKey: String = "familia"
) {
    /** Integrante actualmente seleccionado, o el primero si la clave no existe. */
    val selectedMember: FinanceMember?
        get() = members.firstOrNull { it.key == selectedMemberKey } ?: members.firstOrNull()
}
