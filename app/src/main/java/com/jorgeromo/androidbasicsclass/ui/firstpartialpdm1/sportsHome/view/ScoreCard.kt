package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.model.Score
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.model.Team

/**
 * Tarjeta de un resultado, pensada para un carrusel horizontal (`LazyRow`).
 * Ancho fijo para que todas midan igual dentro del scroll.
 *
 * Reutilizable: recibe solo el [Score] y no sabe nada de navegación ni de red.
 *
 * @param score Resultado a mostrar.
 * @param modifier Modificador externo (el ancho lo fija el propio card).
 */
@Composable
fun ScoreCard(
    score: Score,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(300.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SportBadge(score.sport)
                Text(
                    text = score.status,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamSide(score.homeTeam, Modifier.weight(1f))
                Text(
                    text = "${score.score.home} - ${score.score.away}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                TeamSide(score.awayTeam, Modifier.weight(1f))
            }

            Text(
                text = formatSportDateTime(score.playedAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

/** Logo + abreviatura de un equipo, centrado en su mitad del card. */
@Composable
private fun TeamSide(team: Team, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        AsyncImage(
            model = team.logo,
            contentDescription = team.name,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Text(
            text = team.abbreviation,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = team.city,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

/** Etiqueta de color con el deporte (Soccer, Basketball, …). */
@Composable
internal fun SportBadge(sport: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = sport,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
