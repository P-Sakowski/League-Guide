package com.sakovsky.leagueguide

data class UiState<T>(
    val values: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)