package com.adroid.smaf.function

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AbstractSavedStateViewModelFactory
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


//创建DataStore，用于存储imagePath

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DataStoreKeys {
    val imagePathKey = stringPreferencesKey("image_path")
}


class ProfileViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context
) : ViewModel() {

    private val _imagePathState = MutableStateFlow<String?>(savedStateHandle["imagePath"])
    val imagePathState: StateFlow<String?> = _imagePathState

    init {
        viewModelScope.launch {
            val storedImagePath = context.dataStore.data.first()[DataStoreKeys.imagePathKey]
            _imagePathState.value = storedImagePath
        }
    }

    fun updateImageUri(context: Context, uri: Uri?) {
        viewModelScope.launch {
            val imagePath = uri?.let { saveImageToInternalStorage(context, it) }
            _imagePathState.value = imagePath
            savedStateHandle["imagePath"] = imagePath
            if (imagePath != null) {
                context.dataStore.edit { settings ->
                    settings[DataStoreKeys.imagePathKey] = imagePath
                }
            }
        }
    }

    private fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileName = "avatar_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            return file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }

    companion object {
        fun provideFactory(
            context: Context
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory() {
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return ProfileViewModel(handle, context) as T
                }
            }
    }
}