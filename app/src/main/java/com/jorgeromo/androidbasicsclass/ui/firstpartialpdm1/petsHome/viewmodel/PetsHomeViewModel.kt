package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model.PetsHomeUiState
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.network.PetsRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * ViewModel del home de mascotas. Dueño del estado ([PetsHomeUiState]) y de la
 * lógica: llamar a `GET /api/home/pets/` y traducir los errores a un mensaje.
 *
 * Mismo patrón que
 * [com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.viewmodel.FirstApiRequestViewModel]:
 * dispara la carga en el `init` y expone un único `StateFlow`.
 */
class PetsHomeViewModel : ViewModel() {

    private val petsService = PetsRetrofitClient.petsService

    private val _uiState = MutableStateFlow(PetsHomeUiState())
    val uiState: StateFlow<PetsHomeUiState> = _uiState.asStateFlow()

    init {
        loadPetsHome()
    }

    /**
     * Pide los datos del home. Flujo:
     * 1. `isLoading = true`, limpia error previo.
     * 2. `GET /api/home/pets/`.
     * 3. Guarda `walks` + `tips` en el estado.
     * 4. Cualquier fallo se traduce a un mensaje legible.
     */
    fun loadPetsHome() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = petsService.getPetsHome()
                _uiState.update {
                    it.copy(isLoading = false, walks = response.walks, tips = response.tips)
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
