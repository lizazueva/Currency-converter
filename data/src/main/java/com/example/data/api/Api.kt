package com.example.data.api

import com.example.domain.models.CurrencyResponse
import com.example.domain.utils.Constats
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET ("/latest")
    suspend fun getRates (
        @Query("base") base:String,
        @Query("access_key") apiKey: String = Constats.API_KEY
    ) : Response<CurrencyResponse>

}