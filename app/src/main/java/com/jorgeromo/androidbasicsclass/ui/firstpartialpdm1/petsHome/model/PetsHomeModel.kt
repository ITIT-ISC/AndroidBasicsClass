package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model

/**
 * Datos que devuelve `GET /api/home/pets/` (endpoint público, sin token).
 *
 * Solo son datos: ni red ni UI. La red vive en `petsHome/network/PetsService`
 * y el estado en `petsHome/viewmodel/PetsHomeViewModel`. Mismo criterio que
 * [com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.model.Student].
 */

/**
 * Un paseo registrado de una mascota.
 *
 * Ejemplo JSON:
 * ```json
 * {
 *   "id": 1,
 *   "petName": "Firulais",
 *   "date": "2026-09-03T07:15:00Z",
 *   "steps": 3800,
 *   "durationMin": 32,
 *   "durationText": "32 min",
 *   "distanceKm": 2.4,
 *   "image": "https://loremflickr.com/1200/675/dog,walk,park?lock=61"
 * }
 * ```
 *
 * @property id Identificador del paseo.
 * @property petName Nombre de la mascota que salió a pasear.
 * @property date Fecha/hora del paseo en ISO-8601 UTC (ej. `2026-09-03T07:15:00Z`).
 * @property steps Pasos contados durante el paseo.
 * @property durationMin Duración en minutos (número, para cálculos).
 * @property durationText Duración ya formateada por el servidor (ej. `"32 min"`).
 * @property distanceKm Distancia recorrida en kilómetros.
 * @property image URL de la foto del paseo.
 */
data class Walk(
    val id: Int,
    val petName: String,
    val date: String,
    val steps: Int,
    val durationMin: Int,
    val durationText: String,
    val distanceKm: Double,
    val image: String
)

/**
 * Un consejo de cuidado para mascotas.
 *
 * Ejemplo JSON:
 * ```json
 * {
 *   "id": 1,
 *   "title": "¿Cuánto y cada cuándo darle de comer a tu perro?",
 *   "category": "Nutrition",
 *   "content": "La cantidad depende del peso...",
 *   "image": "https://loremflickr.com/1200/675/dog,food,bowl?lock=71",
 *   "publishedAt": "2026-09-02T09:00:00Z"
 * }
 * ```
 *
 * @property id Identificador del tip.
 * @property title Título del consejo.
 * @property category Categoría (`Nutrition`, `Exercise`, `Health`, `Behavior`, `Grooming`).
 * @property content Texto completo del consejo.
 * @property image URL de la imagen de portada.
 * @property publishedAt Fecha de publicación en ISO-8601 UTC.
 */
data class Tip(
    val id: Int,
    val title: String,
    val category: String,
    val content: String,
    val image: String,
    val publishedAt: String
)

/**
 * Cuerpo completo de la respuesta de `GET /api/home/pets/`.
 *
 * @property walks Lista de paseos recientes.
 * @property tips Lista de consejos de cuidado.
 */
data class PetsHomeResponse(
    val walks: List<Walk>,
    val tips: List<Tip>
)

/**
 * Estado observable de la pantalla de mascotas. Mismo patrón que `LoginUiState`.
 *
 * @property isLoading true mientras se hace la petición (mostrar spinner).
 * @property error mensaje a mostrar si algo falla, o null si no hay error.
 * @property walks paseos ya cargados.
 * @property tips consejos ya cargados.
 */
data class PetsHomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val walks: List<Walk> = emptyList(),
    val tips: List<Tip> = emptyList()
)
