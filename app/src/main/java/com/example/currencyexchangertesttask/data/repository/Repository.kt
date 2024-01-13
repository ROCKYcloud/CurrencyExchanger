package com.example.currencyexchangertesttask.data.repository

import com.example.currencyexchangertesttask.data.api.ApiService
import com.example.currencyexchangertesttask.ui.models.CurrencyRates
import com.example.currencyexchangertesttask.utils.Resource
import javax.inject.Inject
import kotlin.reflect.KMutableProperty


class Repository @Inject constructor(
    private val api: ApiService,
) {
    suspend fun getExchangeInEUR(): Resource<List<CurrencyRates>> {
        try {
            val resp = api.getExchangeRates()
            if (resp.isSuccessful) {
                val rates = resp.body()!!.rates
                val properties = rates::class.members
                val list = mutableListOf<CurrencyRates>()
                properties.map {
                    if (it is KMutableProperty<*>) {
                        val value = it.getter.call(rates)
                        val name = it.name
                        list.add(CurrencyRates(currencyName = name, value = value as Double))
                    }
                }
                return Resource.Success(list)
            }
        } catch (e: Exception) {
            return Resource.Error("$e")
        }
        return Resource.Error("")
    }
}