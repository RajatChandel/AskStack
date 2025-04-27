package com.rchandel.askstack.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InternetBanner(isInternetAvailable: Boolean) {
    val color = if (isInternetAvailable) Color(0xFF4CAF50) else Color(0xFFF44336) // green / red
    val text = if (isInternetAvailable) "Online" else "Offline"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.bodyMedium)
    }
}

