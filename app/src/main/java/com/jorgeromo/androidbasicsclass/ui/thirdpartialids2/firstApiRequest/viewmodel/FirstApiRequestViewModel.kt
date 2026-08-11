package com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.model.Student
import com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar la obtención y presentación de datos de estudiantes
 * desde una API REST utilizando Retrofit.
 *
 * Este ViewModel es responsable de:
 * - Recuperar la lista de estudiantes desde el servicio API
 * - Gestionar los estados de carga (loading)
 * - Manejar errores de red y excepciones
 * - Exponer los datos a través de StateFlow para observación reactiva
 *
 * @see Student
 * @see RetrofitClient
 */
class FirstApiRequestViewModel : ViewModel() {

    /**
     * MutableStateFlow privado que contiene la lista de estudiantes.
     * Solo el ViewModel puede modificarlo.
     */
    private val _students = MutableStateFlow<List<Student>>(emptyList())

    /**
     * StateFlow público que expone la lista de estudiantes de solo lectura.
     * Los observadores pueden recopilar cambios reactivamente.
     */
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    /**
     * Indica si se está cargando la información de estudiantes.
     * true durante la petición API, false cuando finaliza.
     */
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /**
     * Contiene el mensaje de error si ocurre una excepción.
     * null si no hay error, o el mensaje de la excepción si hay fallo.
     */
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * Inicializa el ViewModel y dispara la carga de estudiantes automáticamente.
     */
    init {
        fetchStudents()
    }

    /**
     * Realiza la petición HTTP para obtener la lista de estudiantes.
     *
     * Flujo de ejecución:
     * 1. Establece isLoading en true
     * 2. Limpia errores previos
     * 3. Realiza petición GET al endpoint de estudiantes
     * 4. Almacena la lista en _students
     * 5. Captura excepciones y las almacena en _error
     * 6. Finalmente, establece isLoading en false
     *
     * Se ejecuta en viewModelScope para respetar el ciclo de vida del Fragment/Activity.
     */
    private fun fetchStudents() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _students.value = RetrofitClient.gistService.getStudents().students
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}