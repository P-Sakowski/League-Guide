package com.sakovsky.leagueguide.repository
data class ChampionDetailResponse(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, ChampionDetail>
)