package com.jorgeromo.androidbasicsclass.ui.onboarding.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Star
import androidx.lifecycle.ViewModel
import com.jorgeromo.androidbasicsclass.ui.onboarding.model.OnboardingPage
import com.jorgeromo.androidbasicsclass.ui.onboarding.model.OnboardingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel para la pantalla de onboarding.
 * Mantiene la lista de páginas y la página actualmente seleccionada.
 */
class OnboardingViewModel : ViewModel() {

    private val pages = listOf(
        OnboardingPage(
            icon = Icons.Filled.Star,
            title = "Bienvenido a Android Basics Class",
            description = "Aprende Android paso a paso con ejemplos prácticos y sencillos."
        ),
        OnboardingPage(
            icon = Icons.Filled.Code,
            title = "Practica con ejemplos reales",
            description = "Explora ejercicios de Compose, navegación y consumo de APIs."
        ),
        OnboardingPage(
            icon = Icons.Filled.CheckCircle,
            title = "Todo listo para comenzar",
            description = "Inicia sesión y empieza a explorar la aplicación."
        )
    )

    private val _uiState = MutableStateFlow(OnboardingUiState(pages = pages))
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    /**
     * Sincroniza el estado con la página actual del [androidx.compose.foundation.pager.PagerState],
     * por ejemplo cuando el usuario desliza manualmente.
     */
    fun onPageChanged(index: Int) {
        _uiState.update { it.copy(currentPageIndex = index) }
    }

    fun goToNextPage() {
        _uiState.update { state ->
            state.copy(currentPageIndex = (state.currentPageIndex + 1).coerceAtMost(state.pages.lastIndex))
        }
    }

    fun goToPreviousPage() {
        _uiState.update { state ->
            state.copy(currentPageIndex = (state.currentPageIndex - 1).coerceAtLeast(0))
        }
    }
}
