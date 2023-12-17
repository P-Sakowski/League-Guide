package com.sakovsky.leagueguide.repository
data class ChampionResponse(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, Champion>
)