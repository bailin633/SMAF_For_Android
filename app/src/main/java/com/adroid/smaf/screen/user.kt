package com.adroid.smaf.screen


import com.adroid.smaf.R
import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.adroid.smaf.function.ProfileViewModel
import java.io.File

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Companion.provideFactory(LocalContext.current.applicationContext as Application))) {
    val context = LocalContext.current
    // 从 ViewModel 中获取 imagePathState
    val imagePath by viewModel.imagePathState.collectAsState()

    // ActivityResultLauncher 来启动图片选择器
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // 在这里处理用户选择的图片 URI
        uri?.let {
            //更新选中的Uri图片
            viewModel.updateImageUri(it)
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
            imagePath = imagePath // 传递图片路径
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
    imagePath: String? = null // 修改为 imagePath: String? = null
) {

    Box(
        modifier = modifier
            .size(size) // 设置组件大小
            .clip(CircleShape) // 裁剪成圆形
            .border(1.dp, Color.Gray, CircleShape) // 边框
            .clickable { onClick() } // 为头像框添加点击事件
    ) {
        //显示图片，若imagePath不为空
        if(imagePath!=null){
            val request = ImageRequest.Builder(LocalContext.current)
                .data(File(imagePath))
                .size(Size(size.value.toInt(), size.value.toInt()))
                .scale(Scale.FILL)
                .build()
            val painter = rememberImagePainter(
                request = request
            )
            Image(
                painter = painter,
                contentDescription = "User Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .clip(CircleShape) // 填充整个圆形框并裁剪
            )
        }else{
            //如果没有图片显示自定义图标上传
            val  request = ImageRequest.Builder(LocalContext.current)
                .data(File(""))   //自定义的图片路径
                // 设置组件的大小，将传入的 size 参数值减半后，转为整型，再用其创建一个新的 Size 对象
                .size(Size((size.value / 2).toInt(), (size.value / 2).toInt()))
                .build()
            Image(
                painter = painterResource(id = R.drawable.upload_user),
                contentDescription = "Upload Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.Center)  //图标居中显示
                    .size(80.dp)

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