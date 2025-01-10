package com.adroid.smaf.function

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// 定义一个 Context 扩展属性 dataStore，用于保存 DataStore 实例
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// 定义 DataStore 键的对象，用于存储键值对的键
object DataStoreKeys {
    val imagePathKey = stringPreferencesKey("image_path") // 存储图片路径的键
}

// ProfileViewModel 用于管理用户头像相关的数据和状态
class ProfileViewModel(
    private val savedStateHandle: SavedStateHandle, // 用于保存和恢复状态
    application: Application
) : AndroidViewModel(application) {

    // 内部状态流，用于存储当前图片路径
    private val _imagePathState = MutableStateFlow<String?>(savedStateHandle["imagePath"])
    val imagePathState: StateFlow<String?> = _imagePathState // 公共状态流，供观察

    init {
        // 初始化时从 DataStore 获取存储的图片路径，并更新状态流
        viewModelScope.launch {
            val storedImagePath = getApplication<Application>().dataStore.data.first()[DataStoreKeys.imagePathKey]
            _imagePathState.value = storedImagePath
        }
    }

    // 更新图片 URI 并保存到内部存储和 DataStore
    fun updateImageUri(uri: Uri?) {
        viewModelScope.launch {
            // 将图片保存到内部存储，并获取保存的路径
            val imagePath = uri?.let { saveImageToInternalStorage(getApplication(), it) }
            _imagePathState.value = imagePath // 更新状态流
            savedStateHandle["imagePath"] = imagePath // 保存状态以便恢复
            if (imagePath != null) {
                // 将图片路径保存到 DataStore
                getApplication<Application>().dataStore.edit { settings ->
                    settings[DataStoreKeys.imagePathKey] = imagePath
                }
            }
        }
    }

    // 将图片保存到内部存储，并返回保存的文件路径
    private fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileName = "avatar_${UUID.randomUUID()}.jpg" // 随机生成文件名
        val file = File(context.filesDir, fileName) // 创建文件对象
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream) // 将输入流内容复制到输出流
            return file.absolutePath // 返回文件路径
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            inputStream?.close() // 关闭输入流
            outputStream?.close() // 关闭输出流
        }
    }

    companion object {
        // 提供一个工厂方法，用于创建 ViewModel 实例
        fun provideFactory(application: Application): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory() {
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    @Suppress("UNCHECKED_CAST")
                    return ProfileViewModel(handle, application) as T
                }
            }
    }
}
