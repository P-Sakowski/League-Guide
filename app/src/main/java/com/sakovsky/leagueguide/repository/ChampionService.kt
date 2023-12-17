package com.sakovsky.leagueguide.repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
interface ChampionService {
    @GET("champion.json")
    suspend fun getChampions(): Response<ChampionResponse>
    companion object {
        private const val RIOT_API_URL = "https://ddragon.leagueoflegends.com/cdn/13.24.1/data/en_US/"

        private val logger =
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY }

        private val okHttp = OkHttpClient.Builder().apply {
            this.addInterceptor(logger) }.build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(RIOT_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()
        }

        val championService: ChampionService by lazy {
            retrofit.create(ChampionService::class.java)
        }
    }
}