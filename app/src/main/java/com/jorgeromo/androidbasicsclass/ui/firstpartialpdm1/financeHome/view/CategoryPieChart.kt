package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.model.CategoryBreakdown

/**
 * Gráfica de dona (pie chart hueco) del gasto por categoría, con su leyenda.
 *
 * Reutilizable: recibe solo la lista de [CategoryBreakdown] y la dibuja con
 * `Canvas` usando `percentage` para el ángulo de cada rebanada (la suma puede no
 * dar exactamente 100 por redondeo; se normaliza).
 *
 * @param data Desglose por categoría (ya ordenado por el servidor).
 * @param modifier Modificador externo.
 */
@Composable
fun CategoryPieChart(
    data: List<CategoryBreakdown>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    // Normaliza para que las rebanadas siempre cierren el círculo completo.
    val totalPercentage = remember(data) { data.sumOf { it.percentage.toDouble() }.toFloat() }
    val slices = remember(data, totalPercentage) {
        data.map { it to (it.percentage / totalPercentage) * 360f }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            var startAngle = -90f
            val stroke = Stroke(width = 46f)
            val inset = stroke.width / 2
            slices.forEach { (item, sweep) ->
                drawArc(
                    color = parseHexColor(item.color),
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(inset, inset),
                    size = Size(size.width - stroke.width, size.height - stroke.width),
                    style = stroke
                )
                startAngle += sweep
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            data.forEach { item -> LegendRow(item) }
        }
    }
}

/** Un renglón de la leyenda: cuadro de color + categoría + monto + porcentaje. */
@Composable
private fun LegendRow(item: CategoryBreakdown) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(parseHexColor(item.color))
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = item.category,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = item.amountText,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "${item.percentage}%",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
