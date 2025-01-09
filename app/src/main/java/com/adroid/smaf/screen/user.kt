package com.adroid.smaf.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize() // 使整个屏幕填充
    ) {
        // 头像框
        UserAvatar(
            size = 120.dp,
            modifier = Modifier
                .align(Alignment.Center) // 居中显示
                .offset(y = (-280).dp) // 向上移动
        )
        Card_Setting_one(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 50.dp) // 向下偏移一些，避免与头像框重叠
        )
    }
}

// 头像组件，创建一个简单的圆形
@Composable
fun UserAvatar(size: Dp = 100.dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size) // 设置组件大小
            .clip(CircleShape) // 裁剪成圆形
            .background(Color.Gray) // 默认背景色
            .border(2.dp, Color.Gray, CircleShape) // 边框
    )
}

@Composable
fun Card_Setting_one(modifier: Modifier) {
    Card(
        modifier = modifier
            .width(370.dp)
            .height(148.dp)
            .background(Color(0x00F4F4F4)) // 设置背景颜色
    ) {
        Text(
            text = "测试测试测试测试测试",
            modifier = Modifier.padding(10.dp) // 给文本添加内边距
        )
    }
}
