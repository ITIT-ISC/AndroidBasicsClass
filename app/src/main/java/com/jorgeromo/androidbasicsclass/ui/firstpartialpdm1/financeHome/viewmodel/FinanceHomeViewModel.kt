package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.model.FinanceHomeUiState
import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.network.FinanceRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * ViewModel del home de finanzas. Dueño del estado ([FinanceHomeUiState]) y de la
 * lógica: llamar a `GET /api/home/finance/`, traducir errores y recordar qué
 * integrante está seleccionado en las pestañas.
 *
 * Mismo patrón que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.cafeteriaHome.viewmodel.CafeteriaHomeViewModel].
 */
class FinanceHomeViewModel : ViewModel() {

    private val financeService = FinanceRetrofitClient.financeService

    private val _uiState = MutableStateFlow(FinanceHomeUiState())
    val uiState: StateFlow<FinanceHomeUiState> = _uiState.asStateFlow()

    init {
        loadFinanceHome()
    }

    /** Cambia el integrante seleccionado en las pestañas. */
    fun selectMember(key: String) {
        _uiState.update { it.copy(selectedMemberKey = key) }
    }

    /**
     * Pide los datos del home y los vuelca al estado. Cualquier fallo se traduce
     * a un mensaje legible.
     */
    fun loadFinanceHome() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = financeService.getFinanceHome()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        summary = response.summary,
                        byCategory = response.byCategory,
                        members = response.members
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
