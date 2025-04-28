package com.rchandel.askstack.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rchandel.askstack.presentation.search.SearchScreen
import com.rchandel.askstack.ui.theme.AskStackTheme

@Composable
fun AskStackApp() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreen()
        }
}