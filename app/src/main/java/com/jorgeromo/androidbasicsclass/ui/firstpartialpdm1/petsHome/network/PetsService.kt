package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.network

import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.model.PetsHomeResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * Endpoint del home de mascotas.
 *
 * Base URL: https://androidbasics-auth-api.onrender.com
 *
 * Mismo estilo que
 * [com.jorgeromo.androidbasicsclass.ui.auth.network.AuthService] y
 * [com.jorgeromo.androidbasicsclass.ui.thirdpartialids2.firstApiRequest.network.GistService].
 */
interface PetsService {

    /**
     * `GET /api/home/pets/` — público, sin token.
     *
     * @return [PetsHomeResponse] con `walks` y `tips` (HTTP 200).
     */
    @GET("api/home/pets/")
    suspend fun getPetsHome(): PetsHomeResponse
}

/**
 * Cliente Retrofit para [PetsService]. Timeouts de 60 s porque el servicio corre
 * en Render (free tier): duerme tras ~15 min sin tráfico y el primer request tras
 * eso puede tardar 30–50 s (cold start). Mismo criterio que `AuthRetrofitClient`.
 */
object PetsRetrofitClient {

    private const val BASE_URL = "https://androidbasics-auth-api.onrender.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val petsService: PetsService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PetsService::class.java)
}
