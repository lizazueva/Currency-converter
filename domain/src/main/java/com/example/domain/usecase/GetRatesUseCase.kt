package com.example.domain.usecase

import com.example.domain.repository.TopRepository
import com.example.domain.models.CurrencyResponse
import com.example.domain.utils.Resource

class GetRatesUseCase(
    private val repository: TopRepository
) {
    suspend operator fun invoke(base: String): Resource<CurrencyResponse> {
        return repository.getRates(base)
    }
}