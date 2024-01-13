package com.example.currencyexchangertesttask.ui.screens.currencyConverter

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyexchanger.utils.StringConstant
import com.example.currencyexchangertesttask.ui.components.BalancesList
import com.example.currencyexchanger.ui.components.Header
import com.example.currencyexchanger.ui.components.Label
import androidx.compose.runtime.rememberCoroutineScope
import com.example.currencyexchangertesttask.ui.components.SellExchange
import com.example.currencyexchangertesttask.ui.components.CircularProgress
import com.example.currencyexchangertesttask.ui.components.ReceiveExchange
import com.example.currencyexchangertesttask.ui.theme.LightGray
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import com.example.currencyexchangertesttask.ui.components.AlertDialogExample

@Composable
fun CurrencyConverterScreen(viewModel: CurrencyConverterViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val allRatesCurrency = viewModel.allCurrencyRates.collectAsState()
    val userCurrency = viewModel.userCurrency.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val isError = viewModel.isError.collectAsState()
    val isValueBiggerCurrency = viewModel.isValueBiggerCurrency.collectAsState()
    val isEuroAlmostEmpty = viewModel.isEuroAlmostEmpty.collectAsState()
    when {
        isLoading.value && allRatesCurrency.value.isEmpty() -> {
            CircularProgress()
        }

        isError.value -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = StringConstant.error)
            }
        }

        else -> {
            Box {
                Column {
                    Header(text = StringConstant.currencyConverter)
                    Label(text = StringConstant.myBalances)
                    BalancesList(userCurrency = userCurrency.value)
                    Label(text = StringConstant.currencyExchange)
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        SellExchange(
                            userCurrency = userCurrency.value,
                            onValieChange = {
                                viewModel.updateNumber(it)
                            },
                            onUserCurrencyChange = {
                                scope.launch {
                                    viewModel.updateSellConverter(it)
                                }
                            },
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(start = 48.dp)
                                .background(LightGray)
                        )
                        ReceiveExchange(
                            currencyRates = allRatesCurrency.value,
                            onCurrencyChange = {
                                viewModel.updateReceiveConverter(it)
                            },
                            number = viewModel.sumMoneyToSell.collectAsState().value ?: 0.0
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isValueBiggerCurrency.value,
                            onClick = {
                                viewModel.converter()
                            }
                        ) {
                            Text(text = StringConstant.submit.uppercase())
                        }
                    }
                }
                if (isEuroAlmostEmpty.value) {
                    AlertDialogExample(
                        onDismissRequest = {
                            Toast.makeText(context, "Are you sure?)", Toast.LENGTH_SHORT).show()
                            viewModel.isEuroAlmostEmpty.value = true
                        },
                        onConfirmation = {
                            viewModel.addCurrencyToEuro(1000)
                            viewModel.isEuroAlmostEmpty.value = false
                        },
                        dialogTitle = "Add 1000 EUR to purse"
                    )
                }
            }
        }
    }
}