package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.model.FinanceMember
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.model.FinanceSummary
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.viewmodel.FinanceHomeViewModel

/**
 * Pantalla "home" de finanzas familiares. Solo dibuja: los datos vienen de
 * [FinanceHomeViewModel] (que llama a `GET /api/home/finance/`).
 *
 * Estructura:
 * - Tarjeta de resumen del periodo (total y número de gastos).
 * - "Gasto por categoría" → [CategoryPieChart] (dona + leyenda).
 * - Pestañas de integrantes (`Familia` + los 4 miembros).
 * - Últimos gastos del integrante seleccionado → lista de [ExpenseCard].
 *
 * @param onBack Se dispara al tocar la flecha de la barra superior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceHomeView(
    onBack: () -> Unit = {},
    viewModel: FinanceHomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finanzas familiares") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                uiState.error != null -> Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(uiState.error!!, color = MaterialTheme.colorScheme.error)
                    Button(onClick = viewModel::loadFinanceHome) { Text("Reintentar") }
                }

                else -> {
                    val selected = uiState.selectedMember
                    val selectedIndex = uiState.members.indexOfFirst {
                        it.key == uiState.selectedMemberKey
                    }.coerceAtLeast(0)

                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        uiState.summary?.let { summary ->
                            item { SummaryCard(summary, Modifier.padding(horizontal = 16.dp)) }
                        }

                        if (uiState.byCategory.isNotEmpty()) {
                            item { SectionTitle("Gasto por categoría") }
                            item {
                                CategoryPieChart(
                                    data = uiState.byCategory,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }

                        if (uiState.members.isNotEmpty()) {
                            item {
                                ScrollableTabRow(
                                    selectedTabIndex = selectedIndex,
                                    edgePadding = 16.dp
                                ) {
                                    uiState.members.forEachIndexed { index, member ->
                                        Tab(
                                            selected = index == selectedIndex,
                                            onClick = { viewModel.selectMember(member.key) },
                                            text = { Text(member.name) }
                                        )
                                    }
                                }
                            }

                            selected?.let { member ->
                                item {
                                    MemberHeader(member, Modifier.padding(horizontal = 16.dp))
                                }
                                items(member.expenses, key = { it.id }) { expense ->
                                    ExpenseCard(
                                        expense = expense,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/** Tarjeta grande con el total gastado en el periodo. */
@Composable
private fun SummaryCard(summary: FinanceSummary, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = summary.period,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = summary.totalText,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = "${summary.expenseCount} gastos · ${summary.currency}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

/** Encabezado del integrante seleccionado: avatar, total y número de gastos. */
@Composable
private fun MemberHeader(member: FinanceMember, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = member.avatar,
            contentDescription = member.name,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = member.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${member.totalText} · ${member.expenseCount} gastos",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Encabezado de sección dentro del scroll. */
@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}
