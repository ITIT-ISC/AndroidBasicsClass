package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.network

import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.sportsHome.model.SportsHomeResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * Endpoint del home de deportes.
 *
 * Base URL: https://androidbasics-auth-api.onrender.com
 *
 * Mismo estilo que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.petsHome.network.PetsService].
 */
interface SportsService {

    /**
     * `GET /api/home/sports/` — público, sin token.
     *
     * @return [SportsHomeResponse] con `scores`, `news` y `events` (HTTP 200).
     */
    @GET("api/home/sports/")
    suspend fun getSportsHome(): SportsHomeResponse
}

/**
 * Cliente Retrofit para [SportsService]. Timeouts de 60 s por el cold-start de
 * Render (free tier). Mismo criterio que `PetsRetrofitClient` / `AuthRetrofitClient`.
 */
object SportsRetrofitClient {

    private const val BASE_URL = "https://androidbasics-auth-api.onrender.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val sportsService: SportsService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SportsService::class.java)
}
