package com.example.SMAF


import com.example.SMAF.screens.SettingsScreen
import com.example.SMAF.screens.MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.SMAF.ui.theme.Practice1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practice1Theme {
                // 使用 Scaffold 包裹主内容
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    // 初始化导航控制器
    val navController = rememberNavController()

    // 设置导航主机
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            // 首页（GreetingScreen）
            MainScreen()
        }
        composable("settings") {
            // 设置页面（SettingsScreen）
            SettingsScreen()
        }
        composable("profile") {
            // 我的页面（ProfileScreen）

        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Practice1Theme {
        MainContent()
    }
}
