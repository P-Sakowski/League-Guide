package com.sakovsky.leagueguide.repository

data class ChampionImage(
    val full: String,
    val sprite: String,
    val group: String,
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int
)