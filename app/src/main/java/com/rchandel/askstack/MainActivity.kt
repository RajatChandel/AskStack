package com.rchandel.askstack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rchandel.askstack.core.components.InternetBanner
import com.rchandel.askstack.core.network.ConnectivityObserver
import com.rchandel.askstack.core.network.NetworkConnectivityObserver
import com.rchandel.askstack.presentation.AskStackApp
import com.rchandel.askstack.presentation.theme.AskStackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            AskStackTheme {
                val connectivityState by connectivityObserver.observe()
                    .collectAsState(initial = ConnectivityObserver.Status.Available)
                Scaffold(
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .padding(paddingValues)
                    ) {
                        InternetBanner(connectivityState == ConnectivityObserver.Status.Available)
                        AskStackApp()
                    }
                }
            }


        }
    }
}