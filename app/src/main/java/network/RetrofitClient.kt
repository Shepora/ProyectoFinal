package network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080" // localhost desde emulador

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val huespedApiService: HuespedApiService by lazy {
        retrofit.create(HuespedApiService::class.java)
    }

    val habitacionApiService: HabitacionApiService by lazy {
        retrofit.create(HabitacionApiService::class.java)
    }
    val reservaApiService: ReservaApiService by lazy {
        retrofit.create(ReservaApiService::class.java)
    }
    val parqueaderoApiService : ParqueaderoApiService by lazy {
        retrofit.create(ParqueaderoApiService::class.java)
    }
}
