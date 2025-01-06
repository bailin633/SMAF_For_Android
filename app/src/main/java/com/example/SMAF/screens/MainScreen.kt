package com.example.SMAF.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.SMAF.R


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val selectedIndex = remember { mutableIntStateOf(0) } // 记录当前选中的项

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White, // 设置背景颜色
                tonalElevation = 0.dp, // 去掉阴影效果
                modifier = Modifier
                    .background(Color(0xFFf1f5f8))
                   // .clip(RoundedCornerShape(30.dp)) // 设置圆角效果
                    .height(75.dp)
            ) {
                NavigationBarItem(
                    selected = selectedIndex.intValue == 0, // 判断当前是否为首页
                    onClick = {
                        selectedIndex.intValue = 0 // 更新选中项为首页
                        // 添加 launchSingleTop 和 popUpTo
                        navController.navigate("home") {
                            launchSingleTop = true // 避免重复导航
                            popUpTo("home") { inclusive = true } // 确保 "home" 页面唯一
                        }
                    },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "首页") },
                    label = { Text("首页") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedIndex.intValue == 1, // 判断当前是否为设置
                    onClick = {
                        selectedIndex.intValue = 1 // 更新选中项为设置
                        // 添加 launchSingleTop 和 popUpTo
                        navController.navigate("settings") {
                            launchSingleTop = true // 避免重复导航
                            popUpTo("settings") { inclusive = true } // 确保 "settings" 页面唯一
                        }
                    },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "设置") },
                    label = { Text("我的") },
                    alwaysShowLabel = true
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}




@Composable
fun HomeScreen() {
    ////////////////////////自适应系统背景色
    val backgroundColor = Color(0xFFf1f5f8)
    SetStatusBarColor(color = backgroundColor, darkIcons = true)
    ////////////////////////
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 500.dp), // 偏移 500.dp
            horizontalAlignment = Alignment.CenterHorizontally // 子元素水平居中
        ) {
            MyCard(
                text = "",
                backgroundColor = Color.White, // 定义样式
                size = Modifier.size(379.dp, 182.dp),
                showIcon = true,
                imageOffset = DpOffset((-70).dp, 8.dp),
                showHorizontalDivider = true
            )
        }

        Column(
            modifier = Modifier.padding(top = 220.dp), // 第二个Card的偏移
        ) {
            MyCard(
                text = "Data List -Upload!",
                backgroundColor = Color.White, // 第二个Card的背景颜色不同
                size = Modifier.size(276.dp, 120.dp),
            )
        }
        Column(
            modifier = Modifier.padding(top = 60.dp), // 第三个Card的偏移
        ) {
            MyCard(
                text = "Data List",
                backgroundColor = Color.White, // 第三个Card的背景颜色不同
                size = Modifier.size(276.dp, 120.dp), // 调整尺寸
            )
        }
        Column(modifier = Modifier.padding(top = 100.dp, start = 300.dp)) {
            MyButton(
                text = "测试",
                backgroundColor = Color.Black,
                onClick = {},
                modifier = Modifier.size(80.dp, 40.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = 215.dp, start = 23.dp)
                .width(265.dp)
        ) {
            HorizontalDivider(thickness = 2.dp)
        }
    }
}

@Composable
fun MyCard(
    text: String,
    backgroundColor: Color,
    size: Modifier,
    showIcon: Boolean = false, // 是否显示图标
    showHorizontalDivider: Boolean = false, // 是否显示分割线
    imageAlignment: Alignment.Horizontal = Alignment.CenterHorizontally, // 默认水平居中对齐
    imageOffset: DpOffset = DpOffset(0.dp, 0.dp) // 用于微调图标位置
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .padding(16.dp)
            .then(size), // 使用传入的尺寸参数
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor // 使用传入的背景颜色
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 设置垂直居中
            modifier = Modifier
                .fillMaxSize() // 填满整个 Card
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // 设置水平居中
                verticalArrangement = Arrangement.Top, // 设置垂直对齐方式
                modifier = Modifier
                    .weight(1f) // 占据剩余空间
                    .padding(16.dp) // 添加内边距
            ) {
                // 如果 showIcon 为 true，则显示图标
                if (showIcon) {
                    Box(
                        modifier = Modifier
                            .align(imageAlignment) // 使用传入的水平对齐方式
                            .offset(x = imageOffset.x, y = imageOffset.y) // 微调图标的位置
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.android_phone), // 引用 drawable 文件夹中的 PNG 图像
                            contentDescription = "Android Phone Icon", // 内容描述
                            modifier = Modifier.size(32.dp) // 设置图像大小
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // 添加间距
                }
                // 添加文本
                Text(
                    text = text,
                    textAlign = TextAlign.Center, // 设置文本对齐方式
                    modifier = Modifier.padding(16.dp) // 添加内边距
                )
            }

            // 如果需要显示分割线
            if (showHorizontalDivider) {
                VerticalDivider(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxHeight() // 占满父容器的高度
                        .width(1.dp) // 设置分割线宽度
                        .padding(0.dp) // 移除多余的内边距
                        .offset(x = (-178).dp)
                )
            }
        }
    }
}








@Composable
fun MyButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit, // 添加 onClick 参数
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick, // 设置点击事件
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, // 设置背景颜色
            contentColor = Color.White // 设置文本颜色
        ),
        shape = RoundedCornerShape(8.dp) // 设置按钮形状
    ) {
        Text(text = text)
    }
}

//自适应系统状态栏背景色

@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val context = LocalContext.current
    val view = LocalView.current
    val window = (view.context as? androidx.activity.ComponentActivity)?.window

    window?.let {
        // 设置状态栏背景颜色
        it.statusBarColor = color.toArgb()

        // 设置状态栏图标颜色
        WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = darkIcons
    }
}