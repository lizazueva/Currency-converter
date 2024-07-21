package com.example.domain.repository

import com.example.domain.models.CurrencyResponse
import com.example.domain.utils.Resource

interface TopRepository {
    suspend fun getRates(base: String): Resource<CurrencyResponse>
}