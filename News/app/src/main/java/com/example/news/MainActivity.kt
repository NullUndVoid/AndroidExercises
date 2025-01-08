package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.news.ui.theme.NewsTheme
import androidx.navigation.compose.rememberNavController
import com.example.news.ui.ArticleReader
import com.example.news.ui.NewsViewStart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home"){
                        composable(route = "home"){
                            NewsViewStart(modifier = Modifier.padding(innerPadding), navController = navController)
                        }

                        composable(route = "articleReader/{articleUrl}") {
                            val url = it.arguments?.getString("articleUrl")
                            ArticleReader(modifier = Modifier.padding(innerPadding), url = url?: "")
                        }
                    }
                }
            }
        }
    }
}