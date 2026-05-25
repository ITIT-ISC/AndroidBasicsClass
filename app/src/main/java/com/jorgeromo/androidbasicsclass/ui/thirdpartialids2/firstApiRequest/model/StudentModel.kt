package com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.model

/**
 * Representa un estudiante individual con sus atributos principales.
 *
 * Ejemplo JSON:
 * ```json
 * {
 *   "name": "Juan Pérez",
 *   "studentId": "STU001",
 *   "hobby": "Programación",
 *   "photoUrl": "https://example.com/photos/juan.jpg"
 * }
 * ```
 *
 * @property name Nombre completo del estudiante
 * @property studentId Identificador único del estudiante en el sistema
 * @property hobby Hobby o actividad favorita del estudiante
 * @property photoUrl URL de la foto de perfil del estudiante
 */
data class Student(
    val name: String,
    val studentId: String,
    val hobby: String,
    val photoUrl: String
)

/**
 * Wrapper para la respuesta de la API que contiene una lista de estudiantes.
 * Se utiliza para deserializar la respuesta JSON del servidor.
 *
 * Ejemplo JSON:
 * ```json
 * {
 *   "students": [
 *     {
 *       "name": "Juan Pérez",
 *       "studentId": "STU001",
 *       "hobby": "Programación",
 *       "photoUrl": "https://example.com/photos/juan.jpg"
 *     },
 *     {
 *       "name": "María García",
 *       "studentId": "STU002",
 *       "hobby": "Diseño",
 *       "photoUrl": "https://example.com/photos/maria.jpg"
 *     }
 *   ]
 * }
 * ```
 *
 * @property students Lista de estudiantes obtenida del servidor
 */
data class StudentsResponse(
    val students: List<Student>
)
