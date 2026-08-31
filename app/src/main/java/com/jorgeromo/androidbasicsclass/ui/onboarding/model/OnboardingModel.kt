package com.jorgeromo.androidbasicsclass.ui.onboarding.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Datos de una página individual del onboarding (icono, título y descripción).
 */
data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val description: String
)

/**
 * Estado de la pantalla de onboarding.
 * @property pages Lista de páginas a mostrar.
 * @property currentPageIndex Índice de la página actualmente visible.
 */
data class OnboardingUiState(
    val pages: List<OnboardingPage> = emptyList(),
    val currentPageIndex: Int = 0
) {
    val isLastPage: Boolean
        get() = currentPageIndex == pages.lastIndex
}
