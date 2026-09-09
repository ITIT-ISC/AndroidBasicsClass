package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.model

/**
 * Datos que devuelve `GET /api/home/sports/` (endpoint público, sin token).
 *
 * Solo son datos: ni red ni UI. La red vive en `sportsHome/network/SportsService`
 * y el estado en `sportsHome/viewmodel/SportsHomeViewModel`. Mismo criterio que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model.Walk].
 *
 * El servidor ya entrega todo ordenado:
 * - `scores`: máx. 5, por fecha desc.
 * - `news`: máx. 10, por fecha desc.
 * - `events`: todos los torneos, por `startDate` asc.
 */

/**
 * Un equipo que participa en un marcador.
 *
 * @property id Identificador del equipo.
 * @property name Nombre completo (ej. `"Toros CUU"`).
 * @property city Ciudad sede.
 * @property abbreviation Abreviatura de 3 letras (ej. `"CUU"`).
 * @property logo URL del escudo/logo.
 */
data class Team(
    val id: Int,
    val name: String,
    val city: String,
    val abbreviation: String,
    val logo: String
)

/**
 * Marcador de un partido (`home` vs `away`).
 *
 * @property home Puntos/goles del local.
 * @property away Puntos/goles del visitante.
 */
data class ScoreDetail(
    val home: Int,
    val away: Int
)

/**
 * Resultado de un partido ya jugado.
 *
 * @property id Identificador del marcador.
 * @property sport Deporte (`Soccer`, `Basketball`, `Baseball`, `Football`, `Volleyball`).
 * @property status Estado del partido (ej. `"Finished"`).
 * @property playedAt Fecha/hora del partido en ISO-8601 UTC.
 * @property homeTeam Equipo local.
 * @property awayTeam Equipo visitante.
 * @property score Marcador final.
 * @property summary Resumen ya formateado por el servidor (ej. `"Toros CUU 2 - 1 Águilas CDMX"`).
 */
data class Score(
    val id: Int,
    val sport: String,
    val status: String,
    val playedAt: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val score: ScoreDetail,
    val summary: String
)

/**
 * Una noticia deportiva.
 *
 * @property id Identificador de la nota.
 * @property title Titular.
 * @property shortDescription Entradilla / resumen corto.
 * @property content Cuerpo completo de la nota.
 * @property image URL de la imagen de portada.
 * @property author Autor o mesa de redacción.
 * @property publishedAt Fecha de publicación en ISO-8601 UTC.
 */
data class NewsArticle(
    val id: Int,
    val title: String,
    val shortDescription: String,
    val content: String,
    val image: String,
    val author: String,
    val publishedAt: String
)

/**
 * Un torneo / evento próximo.
 *
 * @property id Identificador del evento.
 * @property title Nombre del torneo.
 * @property sport Deporte del torneo.
 * @property image URL de la imagen.
 * @property startDate Fecha de inicio (`yyyy-MM-dd`, sin hora).
 * @property endDate Fecha de fin (`yyyy-MM-dd`, sin hora).
 * @property venue Sede.
 * @property announcement Texto de la convocatoria.
 * @property rules Reglas del torneo (lista de puntos).
 */
data class SportEvent(
    val id: Int,
    val title: String,
    val sport: String,
    val image: String,
    val startDate: String,
    val endDate: String,
    val venue: String,
    val announcement: String,
    val rules: List<String>
)

/**
 * Cuerpo completo de la respuesta de `GET /api/home/sports/`.
 *
 * @property scores Resultados recientes.
 * @property news Noticias.
 * @property events Torneos próximos.
 */
data class SportsHomeResponse(
    val scores: List<Score>,
    val news: List<NewsArticle>,
    val events: List<SportEvent>
)

/**
 * Estado observable de la pantalla de deportes. Mismo patrón que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model.PetsHomeUiState].
 *
 * @property isLoading true mientras se hace la petición.
 * @property error mensaje a mostrar si algo falla, o null.
 * @property scores resultados ya cargados.
 * @property news noticias ya cargadas.
 * @property events torneos ya cargados.
 */
data class SportsHomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val scores: List<Score> = emptyList(),
    val news: List<NewsArticle> = emptyList(),
    val events: List<SportEvent> = emptyList()
)
