package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.pickarooHome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.pickarooHome.model.PickarooHomeUiState
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.pickarooHome.network.PickarooRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * ViewModel del home de Pickaroo. Dueño del estado ([PickarooHomeUiState]) y de la
 * lógica: llamar a `GET /api/home/pickaroo/` y traducir los errores a un mensaje.
 *
 * Mismo patrón que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.viewmodel.PetsHomeViewModel]:
 * dispara la carga en el `init` y expone un único `StateFlow`.
 */
class PickarooHomeViewModel : ViewModel() {

    private val pickarooService = PickarooRetrofitClient.pickarooService

    private val _uiState = MutableStateFlow(PickarooHomeUiState())
    val uiState: StateFlow<PickarooHomeUiState> = _uiState.asStateFlow()

    init {
        loadPickarooHome()
    }

    /**
     * Pide los datos del home. Flujo:
     * 1. `isLoading = true`, limpia error previo.
     * 2. `GET /api/home/pickaroo/`.
     * 3. Guarda `bundles` + `sections` en el estado.
     * 4. Cualquier fallo se traduce a un mensaje legible.
     */
    fun loadPickarooHome() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = pickarooService.getPickarooHome()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        bundles = response.bundles,
                        sections = response.sections
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
