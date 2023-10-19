package com.eth.refiq.ui.add.content

import CoroutineDispatcherProvider
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.domain.CreateContentRepository
import com.eth.refiq.domain.Post
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AddContentViewModel constructor(
    private val contentRepository: CreateContentRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private var videoUri: String? = null
    private var imageUri: String? = null
    val enableCreateContent: LiveData<Boolean>
        get() = _enableCreateContent

    private val _enableCreateContent =
        MutableLiveData<Boolean>(false)

    fun onVideoUriSelected(uri: String) {
        _enableCreateContent.value = true

        videoUri = uri
        imageUri = null
    }

    fun onImageUriSelected(uri: String) {
        _enableCreateContent.value = true

        imageUri = uri
        videoUri = null
    }

    fun onContentTextChanged(text: String) {
        _enableCreateContent.value = !(text.isEmpty() && imageUri == null && videoUri == null)
    }

    fun createContent(text: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()){
                    contentRepository.createContent(text, imageUri, videoUri)
                }
            }.fold({
                   println(it)
            },{
                it.printStackTrace()
            })
        }
    }

}