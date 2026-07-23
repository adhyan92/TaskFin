package com.example.taskfin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.taskfin.ui.theme.TaskFinTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFinTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    NavApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
