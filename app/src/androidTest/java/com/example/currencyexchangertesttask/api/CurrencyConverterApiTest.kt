package com.example.currencyexchangertesttask.api

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.currencyexchangertesttask.data.api.ApiService
import com.example.currencyexchangertesttask.utils.Constants
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4ClassRunner::class)
class CurrencyConverterApiTest : TestCase() {

    @Test
    fun getDevicesFromApi() {
        val api = provideApi()
        val test = runBlocking {
            api.getExchangeRates()
        }
        assertEquals(test.isSuccessful, true)
    }


    private fun provideApi(): ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}