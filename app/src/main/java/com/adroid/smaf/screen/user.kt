package com.adroid.smaf.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale


@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    //保存选中的图片Uri
    val imageUriState: MutableState<Uri?> = remember { mutableStateOf(null) }


    // ActivityResultLauncher 来启动图片选择器
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // 在这里处理用户选择的图片 URI
        uri?.let {
            //更新选中的Uri图片
            imageUriState.value = it
        }
    }

    Box(
        modifier = Modifier.fillMaxSize() // 使整个屏幕填充
            .background(Color.White)
    ) {
        // 头像框，传递图片 URI
        UserAvatar(
            size = 120.dp,
            onClick = {
                //启动图片选择器
                launcher.launch("image/*")
            },
            modifier = Modifier
                .align(Alignment.Center) // 居中显示
                .offset(y = (-280).dp), // 向上移动
            imageUri = imageUriState.value  //传递图片Uri
        )

        // 使用 Column 来排列卡片
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 320.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // 卡片水平居中
            verticalArrangement = Arrangement.spacedBy(30.dp)  // 卡片之间的垂直间隔
        ) {
            // 第一个Card
            Profile_Card(
                modifier = Modifier.offset(y = (-10).dp), // 直接使用 offset 来调整第一个卡片的位置
                backgroundColor = Color(0xFFF4F4F4),
                text = "测试111111111111111111111111111111111"
            )

            // 第二个Card
            Profile_Card(
                modifier = Modifier, // 不使用额外的 padding，只依赖 verticalArrangement 来调整位置
                text = "测试222222222222222222222222222222222",
                height = 83.dp
            )

            // 第三个Card
            Profile_Card(
                modifier = Modifier, // 不使用 padding，只依赖 verticalArrangement 来调整位置
                text = "测试33333333333333333333333333333333",
                height = 83.dp
            )
        }


    }
}

// 头像组件，创建一个简单的圆形
@Composable
fun UserAvatar(
    size: Dp = 100.dp,
    modifier: Modifier = Modifier,
    onClick:() -> Unit= {},  //点击事件回调
    imageUri: Uri? = null

) {

    Box(
        modifier = modifier
            .size(size) // 设置组件大小
            .clip(CircleShape) // 裁剪成圆形
            .background(Color.Gray) // 默认背景色
            .border(2.dp, Color.Gray, CircleShape) // 边框
            .clickable { onClick() } // 为头像框添加点击事件
    ) {
        //显示图片，若imageUri不为空
        imageUri?.let {
            val painter = rememberImagePainter(
                data = it,
                builder = {scale(Scale.FILL)}
            )
            Image(
                painter = painter,
                contentDescription = "User Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .clip(CircleShape) // 填充整个圆形框并裁剪
            )
        }
    }
}

@Composable
fun Profile_Card(
    modifier: Modifier,
    text: String = "默认内容",
    backgroundColor: Color = Color(0xFFF4F4F4),
    elevation: Float = 0f,
    width: Dp = 330.dp,
    height: Dp = 148.dp
) {
    Card(
        modifier = modifier
            .width(width)
            .height(height),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = backgroundColor // 使用 containerColor 设置背景色
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = elevation.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(text = text)
        }
    }
}