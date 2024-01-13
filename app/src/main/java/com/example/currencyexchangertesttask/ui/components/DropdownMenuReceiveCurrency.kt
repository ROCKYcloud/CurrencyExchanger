package com.example.currencyexchangertesttask.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material.icons.twotone.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.currencyexchangertesttask.ui.models.CurrencyRates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuReceiveCurrency(
    currencyRates: List<CurrencyRates>,
    onChangeCurrency: (CurrencyRates) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(currencyRates.first()) }

    Row(modifier = Modifier.width(50.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            Row {
                onChangeCurrency(selectedText)
                Text(text = selectedText.currencyName)
                Icon(
                    imageVector = if (!expanded) Icons.TwoTone.KeyboardArrowDown else Icons.TwoTone.KeyboardArrowUp,
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp),
                    tint = Color.Black,
                )

            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = true },
                modifier = Modifier.width(60.dp)
            ) {
                currencyRates.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.currencyName) },
                        onClick = {
                            onChangeCurrency(item)
                            selectedText = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
