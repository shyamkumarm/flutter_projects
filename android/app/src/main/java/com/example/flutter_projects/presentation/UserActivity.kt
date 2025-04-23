package com.example.flutter_projects.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flutter_projects.presentation.screen.UserInputScreen
import com.gallery.myapplication.ui.theme.MyApplicationTheme

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Greeting(
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Greeting(modifier: Modifier = Modifier.padding(16.dp)) {
    // val viewModel = koinViewModel()
    MyApplicationTheme {
        UserInputScreen(modifier)
    }
}