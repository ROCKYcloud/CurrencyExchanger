package com.example.currencyexchangertesttask.ui.screens.currencyConverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangertesttask.data.repository.Repository
import com.example.currencyexchangertesttask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.currencyexchangertesttask.ui.models.CurrencyRates
import com.example.currencyexchangertesttask.ui.models.UserCurrency
import com.example.currencyexchangertesttask.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow


@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val allCurrencyRates = MutableStateFlow<MutableList<CurrencyRates>>(mutableListOf())
    val userCurrency = MutableStateFlow<MutableList<UserCurrency>>(mutableListOf())
    val sumMoneyToSell = MutableStateFlow<Double?>(null)
    val isLoading = MutableStateFlow(false)
    val isError = MutableStateFlow(false)
    val isValueBiggerCurrency = MutableStateFlow(false)
    private val newSellConverter =
        MutableStateFlow(UserCurrency(currency = 0.0, currencyName = "", rate = 0.0))
    private val newReceiveConverter =
        MutableStateFlow(CurrencyRates(currencyName = "", value = 0.0))
    private val eurRates = MutableStateFlow(0.0)
    val isEuroAlmostEmpty = MutableStateFlow(false)

    init {
        getCurrency()
    }

    fun updateNumber(number: String) {
        val userCurrencyObj = userCurrency.value.find {
            newSellConverter.value.currencyName == it.currencyName
        }
        if (userCurrencyObj != null) {
            if (number.isNotEmpty()) {
                if (number.toDouble() <= userCurrencyObj.currency) {
                    isValueBiggerCurrency.value = true
                    sumMoneyToSell.value = number.toDouble()
                } else {
                    isValueBiggerCurrency.value = false
                }

            } else {
                isValueBiggerCurrency.value = false
                sumMoneyToSell.value = 0.0
            }
        }
    }

    fun updateReceiveConverter(userCurrency: CurrencyRates) {
        newReceiveConverter.value = userCurrency
    }

    fun converter() {
        subtractCurrencyFromSale()

        val isContain = userCurrency.value.find { it.currencyName == newReceiveConverter.value.currencyName }

        if (isContain == null) {
            var calculateCurrency = sumMoneyToSell.value!! * eurRates.value * newReceiveConverter.value.value
            calculateCurrency = formatForCurrency(calculateCurrency)
            saveConverted(calculateCurrency)
        } else {
            userCurrency.value.map {
                if (it.currencyName == Constants.EUR) {
                    if (it.currencyName == newReceiveConverter.value.currencyName) {
                        val calculateCurrency = sumMoneyToSell.value!! * newReceiveConverter.value.value
                        it.currency += formatForCurrency(calculateCurrency)
                    }
                } else {
                    if (it.currencyName == newReceiveConverter.value.currencyName) {
                        val calculateCurrency = sumMoneyToSell.value!! * eurRates.value * newReceiveConverter.value.value
                        it.currency += formatForCurrency(calculateCurrency)
                    }
                }
            }
        }
        sumMoneyToSell.value = 0.0
        isValueBiggerCurrency.value = false
        checkEuro()
    }


   private fun checkEuro(){
       val eurCurrency = userCurrency.value.find { it.currencyName == Constants.EUR }
        isEuroAlmostEmpty.value = eurCurrency!!.currency <= 100.0
    }

    fun addCurrencyToEuro(number:Int){
        userCurrency.value.map {
            if (it.currencyName == Constants.EUR){
                it.currency += number.toDouble()
            }
        }
    }

    private fun saveConverted(calculateCurrency: Double) {
        userCurrency.value.add(
            UserCurrency(
                currencyName = newReceiveConverter.value.currencyName,
                currency = calculateCurrency,
                rate = newReceiveConverter.value.value
            )
        )
    }

    suspend fun updateSellConverter(userCurrency: UserCurrency) {
        newSellConverter.emit(userCurrency)
    }

    private fun getCurrency() {
        viewModelScope.launch {
            if (!isLoading.value) {
                isLoading.value = true
                when (val result = repository.getExchangeInEUR()) {
                    is Resource.Loading -> loading()
                    is Resource.Success -> {
                        result.data?.let { success(result = it) }
                    }

                    is Resource.Error -> error()
                }
            }
        }
    }

    private fun subtractCurrencyFromSale() {
        userCurrency.value.map {
            if (newSellConverter.value.currencyName == it.currencyName) {
                it.currency = it.currency - sumMoneyToSell.value!!
            }
        }
    }

    private fun formatForCurrency(value: Double): Double {
        return String.format("%.2f", (value)).toDouble()
    }

    private suspend fun success(result: List<CurrencyRates>) {
        eurRates.emit(result.find { it.currencyName == Constants.EUR }?.value ?: 0.0)
        val evrCurrency = mutableListOf(
            UserCurrency(
                currencyName = Constants.EUR,
                currency = 1000.0,
                rate = eurRates.value
            )
        )
        userCurrency.emit(evrCurrency)
        allCurrencyRates.emit(result as MutableList)

        isLoading.value = false
        isError.value = false
    }

    private fun loading() {
        isLoading.value = true
        isError.value = false
    }

    private fun error() {
        isLoading.value = false
        isError.value = true
    }
}