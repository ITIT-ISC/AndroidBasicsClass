package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model.Walk

/**
 * Tarjeta de un paseo, pensada para un carrusel horizontal (`LazyRow`).
 * Ancho fijo para que todas midan igual dentro del scroll.
 *
 * Reutilizable: recibe solo el [Walk] y no sabe nada de navegación ni de red.
 *
 * @param walk Datos del paseo a mostrar.
 * @param modifier Modificador externo (el ancho lo fija el propio card).
 */
@Composable
fun WalkCard(
    walk: Walk,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(260.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = walk.image,
                    contentDescription = "Paseo de ${walk.petName}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    contentScale = ContentScale.Crop
                )
                // Pastilla con el nombre de la mascota sobre la foto.
                Text(
                    text = walk.petName,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.Black.copy(alpha = 0.55f))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }

            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = formatPetDate(walk.date, withTime = true),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${walk.distanceKm} km",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    WalkStat(Icons.Filled.DirectionsWalk, "${walk.steps} pasos")
                    WalkStat(Icons.Filled.Timer, walk.durationText)
                }
            }
        }
    }
}

/** Ícono + texto de una métrica del paseo (pasos, duración). */
@Composable
private fun WalkStat(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(18.dp)
        )
        Text(text, style = MaterialTheme.typography.bodySmall)
    }
}
