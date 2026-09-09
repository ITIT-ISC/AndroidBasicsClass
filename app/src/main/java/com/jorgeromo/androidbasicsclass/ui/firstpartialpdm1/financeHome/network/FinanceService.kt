package com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.network

import com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.financeHome.model.FinanceHomeResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * Endpoint del home de finanzas familiares.
 *
 * Base URL: https://androidbasics-auth-api.onrender.com
 *
 * Mismo estilo que
 * [com.jorgeromo.androidbasicsclass.ui.firstpartialpdm1.cafeteriaHome.network.CafeteriaService].
 */
interface FinanceService {

    /**
     * `GET /api/home/finance/` — público, sin token.
     *
     * @return [FinanceHomeResponse] con `summary`, `byCategory` y `members` (HTTP 200).
     */
    @GET("api/home/finance/")
    suspend fun getFinanceHome(): FinanceHomeResponse
}

/**
 * Cliente Retrofit para [FinanceService]. Timeouts de 60 s por el cold-start de
 * Render (free tier). Mismo criterio que `CafeteriaRetrofitClient`.
 */
object FinanceRetrofitClient {

    private const val BASE_URL = "https://androidbasics-auth-api.onrender.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val financeService: FinanceService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FinanceService::class.java)
}
