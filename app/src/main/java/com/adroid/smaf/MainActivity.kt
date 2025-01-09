package com.adroid.smaf

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .systemBarsPadding(),  //防止内容被状态导航栏覆盖
        bottomBar = {
            // 显示底部导航栏，传递当前选中的项和点击后的处理方法
            CustomNavigationBar(selectedItem = selectedItem, onItemSelected = { selectedItem = it })
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
fun CustomNavigationBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    // 定义导航栏选项
    val navItems = listOf(
        NavItem("图库", Icons.Default.PlayArrow),
        NavItem("主页", Icons.Default.Home),
        NavItem("个人", Icons.Default.Person)
    )

    // 自定义高度，调整背景颜色，去除多余的内边距
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()  // 填充整个宽度
            .offset(y = 5.dp) // 可选的微小偏移
            .height(56.dp)   // 设置导航栏的高度
            .clip(RoundedCornerShape(0.dp)) // 保证不影响宽度，去掉圆角或者使用较小圆角
            .padding(0.dp),  // 去除导航栏的默认内边距
        containerColor = Color.White,  // 设置背景色
        tonalElevation = 4.dp  // 阴影效果
    ) {
        // 动态生成每个 NavigationBarItem
        navItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedItem == index,  // 判断当前是否选中
                onClick = { onItemSelected(index) },  // 点击时选择对应的项
                label = {
                    Text(
                        text = navItem.label,
                        style = MaterialTheme.typography.bodySmall.copy( // 调整文字大小
                            color = if (selectedItem == index) Color(0xFF6200EE) else Color.Gray
                        )
                    )
                },  // 显示选项的文字标签
                icon = {
                    Icon(
                        navItem.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp) // 调整图标大小
                            .padding(0.dp), // 去除图标的额外内边距
                        tint = if (selectedItem == index) Color(0xFF6200EE) else Color.Gray
                    )
                },  // 使用图标
                modifier = Modifier.padding(horizontal = 8.dp) // 控制图标之间的间隔
            )
        }
    }
}



// 用于封装导航项的信息
data class NavItem(val label: String, val icon: ImageVector)

@Composable
fun GalleryScreen(modifier: Modifier = Modifier) {
    // 图库页面的内容
    Box(modifier = modifier.fillMaxSize()) {
        // 居中显示文本“图库”
        Text(text = "图库", modifier = Modifier.align(Alignment.Center))
    }
}
