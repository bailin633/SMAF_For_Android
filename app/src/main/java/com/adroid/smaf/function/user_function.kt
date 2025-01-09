package com.adroid.smaf.function

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

// ProfileViewModel 负责管理与用户头像相关的逻辑和状态
class ProfileViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // 用 MutableStateFlow 保存用户头像路径，初始值从 SavedStateHandle 获取
    private val _imagePathState = MutableStateFlow<String?>(savedStateHandle["imagePath"])

    // 对外暴露的 StateFlow，只允许读取头像路径
    val imagePathState: StateFlow<String?> = _imagePathState

    // 更新头像路径
    fun updateImageUri(context: Context, uri: Uri?) {
        // 使用 ViewModelScope 在后台线程执行更新操作
        viewModelScope.launch {
            // 当 uri 不为空时，将其保存到内部存储，并返回保存路径
            val imagePath = uri?.let { saveImageToInternalStorage(context, it) }

            // 更新头像路径状态
            _imagePathState.value = imagePath

            // 同时将头像路径保存到 SavedStateHandle，以便在 ViewModel 重建时恢复
            savedStateHandle["imagePath"] = imagePath
        }
    }

    // 将图片保存到内部存储
    private fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        // 获取图片输入流
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

        // 生成一个唯一的文件名
        val fileName = "avatar_${UUID.randomUUID()}.jpg"

        // 获取内部存储路径
        val file = File(context.filesDir, fileName)

        var outputStream: FileOutputStream? = null
        try {
            // 创建输出流，准备写入文件
            outputStream = FileOutputStream(file)

            // 将输入流的数据复制到输出流（即保存到文件）
            inputStream?.copyTo(outputStream)

            // 返回保存的文件的绝对路径
            return file.absolutePath
        } catch (e: Exception) {
            // 发生异常时打印错误日志
            e.printStackTrace()
            return null
        } finally {
            // 关闭输入流和输出流
            inputStream?.close()
            outputStream?.close()
        }
    }
}
