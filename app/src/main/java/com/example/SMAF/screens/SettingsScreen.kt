package com.example.SMAF.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val backgroundColor = Color(0xFFFFFFFF)
    // 设置页面的内容
    Box(modifier = modifier.fillMaxSize().background(backgroundColor)) {

        Text(
            text = "个人页面",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}