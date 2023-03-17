package com.app.retrogrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.app.retrogrid.configuration.RetrogridConfiguration
import com.app.retrogrid.intercepter.HeaderInterceptor
import com.app.retrogrid.model.NewsErrorResponse
import com.app.retrogrid.ui.component.MainView
import com.app.retrogrid.ui.theme.RetrogridAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrogridConfiguration.enableLog(true)
            .setErrorResponseClass(NewsErrorResponse::class)
            .addInterceptor(HeaderInterceptor())
        setContent {
            RetrogridAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView()
                }
            }
        }
    }
}
