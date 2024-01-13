package com.example.currencyexchangertesttask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencyexchanger.utils.StringConstant
import com.example.currencyexchangertesttask.ui.models.CurrencyRates
import com.example.currencyexchangertesttask.ui.theme.Green

@Composable
fun ReceiveExchange(
    currencyRates: List<CurrencyRates>,
    onCurrencyChange:(CurrencyRates)->Unit,
    number: Double
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Green),
                tint = Color.White,
            )
            Text(text = StringConstant.receive, modifier = Modifier.padding(start = 8.dp))
        }
        Row {
             Text(text = "$number", modifier = Modifier.padding(end = 16.dp))
            DropdownMenuReceiveCurrency(currencyRates = currencyRates, onChangeCurrency = {
                onCurrencyChange(it)
            })
        }
    }
}