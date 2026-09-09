package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.pickarooHome.network

import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.pickarooHome.model.PickarooHomeResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * Endpoint del home de Pickaroo.
 *
 * Base URL: https://androidbasics-auth-api.onrender.com
 *
 * Mismo estilo que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.network.PetsService].
 */
interface PickarooService {

    /**
     * `GET /api/home/pickaroo/` — público, sin token.
     *
     * @return [PickarooHomeResponse] con `bundles` y `sections` (HTTP 200).
     */
    @GET("api/home/pickaroo/")
    suspend fun getPickarooHome(): PickarooHomeResponse
}

/**
 * Cliente Retrofit para [PickarooService]. Timeouts de 60 s por el cold-start de
 * Render (free tier). Mismo criterio que `PetsRetrofitClient` / `AuthRetrofitClient`.
 */
object PickarooRetrofitClient {

    private const val BASE_URL = "https://androidbasics-auth-api.onrender.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val pickarooService: PickarooService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PickarooService::class.java)
}
