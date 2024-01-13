package com.example.currencyexchangertesttask.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyexchangertesttask.ui.models.UserCurrency

@Composable
fun BalancesList(userCurrency: List<UserCurrency>) {
    LazyRow {
        items(userCurrency) {
            val text = "${it.currency} ${it.currencyName}"
            Text(
                text = text.uppercase(),
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 20.sp,
            )
        }
    }
}