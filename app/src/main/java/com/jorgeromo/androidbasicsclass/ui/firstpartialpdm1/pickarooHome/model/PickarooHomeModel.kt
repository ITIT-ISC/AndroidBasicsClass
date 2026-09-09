package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.pickarooHome.model

/**
 * Datos que devuelve `GET /api/home/pickaroo/` (endpoint público, sin token).
 *
 * Solo son datos: ni red ni UI. La red vive en `pickarooHome/network/PickarooService`
 * y el estado en `pickarooHome/viewmodel/PickarooHomeViewModel`. Mismo criterio que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model.Walk].
 */

/**
 * Un "bundle": una canasta con varios productos a precio cerrado.
 *
 * @property id Identificador del bundle.
 * @property name Nombre comercial (ej. `"Pa' la carne asada"`).
 * @property description Descripción corta.
 * @property items Lista de ingredientes/productos incluidos.
 * @property itemsText Los mismos `items` ya unidos en un solo string por el servidor.
 * @property price Precio como número, para cálculos.
 * @property priceText Precio ya formateado por el servidor (ej. `"$300.00"`).
 * @property image URL de la imagen.
 */
data class Bundle(
    val id: Int,
    val name: String,
    val description: String,
    val items: List<String>,
    val itemsText: String,
    val price: Double,
    val priceText: String,
    val image: String
)

/**
 * Un producto individual del catálogo.
 *
 * @property id Identificador del producto.
 * @property name Nombre (ej. `"Aguacate Hass"`).
 * @property section Sección a la que pertenece (ej. `"Frutas y verduras"`).
 * @property unit Unidad de venta (`kg`, `pieza`).
 * @property price Precio unitario como número.
 * @property priceText Precio ya formateado por el servidor (ej. `"$89.00 / kg"`).
 * @property image URL de la imagen.
 */
data class Product(
    val id: Int,
    val name: String,
    val section: String,
    val unit: String,
    val price: Double,
    val priceText: String,
    val image: String
)

/**
 * Una sección del catálogo con su lista de productos.
 *
 * @property section Nombre de la sección (ej. `"Carnes"`).
 * @property products Productos de esa sección.
 */
data class PickarooSection(
    val section: String,
    val products: List<Product>
)

/**
 * Cuerpo completo de la respuesta de `GET /api/home/pickaroo/`.
 *
 * @property bundles Canastas a precio cerrado.
 * @property sections Catálogo de productos agrupado por sección.
 */
data class PickarooHomeResponse(
    val bundles: List<Bundle>,
    val sections: List<PickarooSection>
)

/**
 * Estado observable de la pantalla de Pickaroo. Mismo patrón que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model.PetsHomeUiState].
 *
 * @property isLoading true mientras se hace la petición.
 * @property error mensaje a mostrar si algo falla, o null.
 * @property bundles bundles ya cargados.
 * @property sections secciones ya cargadas.
 */
data class PickarooHomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val bundles: List<Bundle> = emptyList(),
    val sections: List<PickarooSection> = emptyList()
)
