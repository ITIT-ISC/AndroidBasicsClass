package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.model.SportsHomeUiState
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.network.SportsRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * ViewModel del home de deportes. Dueño del estado ([SportsHomeUiState]) y de la
 * lógica: llamar a `GET /api/home/sports/` y traducir los errores a un mensaje.
 *
 * Mismo patrón que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.viewmodel.PetsHomeViewModel]:
 * dispara la carga en el `init` y expone un único `StateFlow`.
 */
class SportsHomeViewModel : ViewModel() {

    private val sportsService = SportsRetrofitClient.sportsService

    private val _uiState = MutableStateFlow(SportsHomeUiState())
    val uiState: StateFlow<SportsHomeUiState> = _uiState.asStateFlow()

    init {
        loadSportsHome()
    }

    /**
     * Pide los datos del home. Flujo:
     * 1. `isLoading = true`, limpia error previo.
     * 2. `GET /api/home/sports/`.
     * 3. Guarda `scores` + `news` + `events` en el estado.
     * 4. Cualquier fallo se traduce a un mensaje legible.
     */
    fun loadSportsHome() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = sportsService.getSportsHome()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        scores = response.scores,
                        news = response.news,
                        events = response.events
                    )
                }
            } catch (e: HttpException) {
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudo cargar (error ${e.code()})")
                }
            } catch (e: SocketTimeoutException) {
                _uiState.update {
                    it.copy(isLoading = false, error = "El servidor está iniciando. Vuelve a intentar.")
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Sin conexión. Revisa tu internet e intenta de nuevo.")
                }
            }
        }
    }
}
