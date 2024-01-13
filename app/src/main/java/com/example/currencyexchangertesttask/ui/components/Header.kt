package com.example.currencyexchanger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.currencyexchangertesttask.ui.theme.LightBlue40

@Composable
fun Header(text: String) {
    Text(
        text = text, modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(color = LightBlue40)
            .padding(vertical = 16.dp),
        textAlign = TextAlign.Center,
        color = Color.White
    )
}