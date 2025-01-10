package com.adroid.smaf.function

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex


object SharedPreferencesHelper {

    private const val PREF_NAME = "user_preferences"
    private const val KEY_USER_NAME = "user_name"

    // 获取 SharedPreferences 实例
    private fun getPreferences(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // 保存用户名
    fun saveUserName(context: Context, userName: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_USER_NAME, userName)
        editor.apply()
    }

    // 获取用户名
    fun getUserName(context: Context): String {
        return getPreferences(context).getString(KEY_USER_NAME, "默认用户名") ?: "默认用户名"
    }
}


@Composable
fun UserNameInput() {
    val context = LocalContext.current
    var userName by remember { mutableStateOf(SharedPreferencesHelper.getUserName(context)) } // 从 SharedPreferences 获取用户名
    var isEditing by remember { mutableStateOf(false) } // 是否处于编辑状态
    var tempUserName by remember { mutableStateOf(userName) } // 临时保存用户输入的用户名

    Box(
        modifier = Modifier.fillMaxSize() // 确保 Box 填满整个屏幕
    ) {
        // 主要内容
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .offset(y = 185.dp), // 整体向下偏移
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userName,
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable { isEditing = true } // 点击时进入编辑状态
                    .padding(8.dp)
            )
        }

        // 当处于编辑状态时显示输入框
        if (isEditing) {
            // 半透明背景遮罩层，覆盖所有内容
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // 半透明背景
                    .zIndex(1f) // 确保遮罩层在最上层
            )

            // 弹出输入框，居中显示
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center) // 居中显示输入框
                    .zIndex(2f) // 确保输入框在最上层
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)) // 设置背景白色并圆角
                        .padding(20.dp) // 给输入框和按钮添加内边距
                ) {
                    TextField(
                        value = tempUserName,
                        onValueChange = { tempUserName = it },
                        label = { Text("请输入用户名") },
                        modifier = Modifier.fillMaxWidth(0.8f) // 限制输入框宽度
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // 设置按钮之间的间隔
                    ) {
                        // 取消按钮
                        Button(
                            onClick = {
                                isEditing = false // 关闭输入框
                            },
                            modifier = Modifier.weight(1f) // 使按钮平分宽度
                        ) {
                            Text("取消")
                        }
                        // 保存按钮
                        Button(
                            onClick = {
                                // 如果用户名为空，则设置为默认用户名
                                val savedUserName = if (tempUserName.isEmpty()) "默认用户名" else tempUserName
                                SharedPreferencesHelper.saveUserName(context, savedUserName) // 保存到 SharedPreferences
                                userName = savedUserName // 更新当前显示的用户名
                                isEditing = false // 退出编辑状态
                            },
                            modifier = Modifier.weight(1f) // 使按钮平分宽度
                        ) {
                            Text("保存")
                        }
                    }
                }
            }
        }
    }
}
