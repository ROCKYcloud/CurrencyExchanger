package com.example.currencyexchangertesttask.data.api

import com.example.currencyexchangertesttask.data.responce.CurrencyExchangeRatesModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
   @GET("currency-exchange-rates")
   suspend fun getExchangeRates() : Response<CurrencyExchangeRatesModel>
}