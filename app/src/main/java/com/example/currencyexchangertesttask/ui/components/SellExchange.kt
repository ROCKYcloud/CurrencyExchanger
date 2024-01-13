package com.example.currencyexchangertesttask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.currencyexchanger.utils.StringConstant
import com.example.currencyexchangertesttask.ui.components.DropdownMenuSellCurrency
import com.example.currencyexchangertesttask.ui.models.UserCurrency
import com.example.currencyexchangertesttask.ui.theme.LightBlue40
import com.example.currencyexchangertesttask.ui.theme.Red
import com.example.currencyexchangertesttask.ui.theme.PurpleGrey80

@Composable
fun SellExchange(
    title: String = StringConstant.sell,
    userCurrency: List<UserCurrency>,
    onValieChange: (String) -> Unit,
    onUserCurrencyChange: (UserCurrency) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("" ) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Red),
                tint = Color.White,
            )
            Text(text = title, modifier = Modifier.padding(start = 8.dp))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = text,
                placeholder = {
                    Text(text = "0.0")
                },
                onValueChange = {
                    onValieChange(it)
                    text = it
                },
                singleLine = true,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .width(100.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            )
            DropdownMenuSellCurrency(
                userCurrency = userCurrency,
                onUserCurrencyChange = {
                    onUserCurrencyChange(it)
                })
        }
    }
}

