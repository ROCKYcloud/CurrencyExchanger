package com.example.currencyexchangertesttask.api

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.currencyexchangertesttask.data.api.ApiService
import com.example.currencyexchangertesttask.data.responce.Rates
import com.example.currencyexchangertesttask.ui.models.CurrencyRates
import com.example.currencyexchangertesttask.utils.Constants
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KMutableProperty

@RunWith(AndroidJUnit4ClassRunner::class)
class CurrencyConverterTests : TestCase() {

    @Test
    fun getExchangeRatesFromApi() {
        val api = provideApi()
        val test = runBlocking {
            api.getExchangeRates()
        }
        assertEquals(test.isSuccessful, true)
    }

    fun gatRates(): Rates {
        val api = provideApi()
        val test = runBlocking {
            api.getExchangeRates()
        }
        return test.body()!!.rates
    }
    @Test
    fun convertCurrencyRatesToList() {
        val rates = gatRates()
        val properties = rates::class.members
        val list = mutableListOf<CurrencyRates>()
        properties.map {
            if (it is KMutableProperty<*>) {
                val value = it.getter.call(rates)
                val name = it.name
                list.add(CurrencyRates(currencyName = name, value = value as Double))
            }
        }
        assertEquals(list.isNotEmpty(), true)
    }
    private fun provideApi(): ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}