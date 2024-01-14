package com.sakovsky.leagueguide.repository

import retrofit2.Response
class ChampionRepository {
    suspend fun getChampionResponse(): Response<ChampionResponse> =
        ChampionService.championService.getChampions()
    suspend fun getChampionDetailResponse(id : String): Response<ChampionDetailResponse> =
        ChampionService.championService.getChampionDetails(id)
}