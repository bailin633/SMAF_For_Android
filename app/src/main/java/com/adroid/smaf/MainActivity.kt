package com.adroid.smaf

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.adroid.smaf.screen.HomeScreen
import com.adroid.smaf.screen.ProfileScreen
import com.adroid.smaf.ui.theme.SMAF_AndroidTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 禁用状态栏的自动背景颜色变化
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 设置状态栏透明（避免渐变背景覆盖）
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val controller = WindowInsetsControllerCompat(window, window.decorView)
            controller.isAppearanceLightStatusBars = true // 设置状态栏图标为深色
            window.statusBarColor = Color.Transparent.toArgb() // 设置状态栏为透明
        } else {
            // 对于较低版本，仍然使用原先的方法
            window.statusBarColor = Color.Transparent.toArgb()
        }

        enableEdgeToEdge()  // 启用边缘到边缘布局，去除状态栏和导航栏的空白
        setContent {
            SMAF_AndroidTheme {
                // 设置主屏幕的内容视图
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    // 状态管理：控制当前选中的底部导航栏项，默认为 0 (图库)
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // 显示底部导航栏，传递当前选中的项和点击后的处理方法
            NavigationBar(selectedItem = selectedItem, onItemSelected = { selectedItem = it })
        }
    ) { innerPadding ->
        // 根据 selectedItem 的值显示不同的屏幕内容
        when (selectedItem) {
            0 -> GalleryScreen(modifier = Modifier.padding(innerPadding))  // 显示图库页面
            1 -> HomeScreen(modifier = Modifier.padding(innerPadding))     // 显示主页页面
            2 -> ProfileScreen(modifier = Modifier.padding(innerPadding))  // 显示个人页面
        }
    }
}

@Composable
fun NavigationBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    // 使用新的 NavigationBar 来替代旧的 BottomNavigation
    NavigationBar(
        modifier = Modifier.fillMaxWidth()  // 填充整个宽度
    ) {
        // 图库选项
        NavigationBarItem(
            selected = selectedItem == 0,  // 判断当前是否选中
            onClick = { onItemSelected(0) },  // 点击时选择对应的项
            label = { Text("图库") },  // 显示选项的文字标签
            icon = { Icon(Icons.Default.Home, contentDescription = null) }  // 使用 PhotoLibrary 图标


        )

        // 主页选项
        NavigationBarItem(
            selected = selectedItem == 1,  // 判断当前是否选中
            onClick = { onItemSelected(1) },  // 点击时选择对应的项
            label = { Text("主页") },  // 显示选项的文字标签
            icon = { Icon(Icons.Default.Home, contentDescription = null) }  // 显示主页图标
        )

        // 个人选项
        NavigationBarItem(
            selected = selectedItem == 2,  // 判断当前是否选中
            onClick = { onItemSelected(2) },  // 点击时选择对应的项
            label = { Text("个人") },  // 显示选项的文字标签
            icon = { Icon(Icons.Default.Person, contentDescription = null) }  // 显示个人图标
        )
    }
}

@Composable
fun GalleryScreen(modifier: Modifier = Modifier) {
    // 图库页面的内容
    Box(modifier = modifier.fillMaxSize()) {
        // 居中显示文本“图库”
        Text(text = "图库", modifier = Modifier.align(Alignment.Center))
    }
}



