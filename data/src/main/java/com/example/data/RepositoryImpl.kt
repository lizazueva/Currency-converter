package com.example.data

import com.example.data.api.Api
import com.example.domain.repository.TopRepository
import com.example.domain.models.CurrencyResponse
import com.example.domain.utils.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api
) : TopRepository {
    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        val resp = api.getRates(base)
        return try {
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null) {
                    Resource.Success(body)
                } else {
                    Resource.Error("Ошибка при получении списка")
                }
            } else {
                val errorBody = resp.errorBody()?.toString()
                Resource.Error(errorBody ?: "Ошибка при получении списка")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Неизвестная ошибка")
        }
    }
}