package com.example.photogenerator.catPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogenerator.network.CatApi
import com.example.photogenerator.network.CatProperty
import kotlinx.coroutines.launch

class CatPageViewModel : ViewModel() {
    private val _photo = MutableLiveData<String>()

    val photo: LiveData<String> = _photo

    init {
        getCatPhoto()
    }

    fun getCatPhoto() {
        viewModelScope.launch {
            try {
                val listResult = CatApi.retrofitService.getCat()
                _photo.value = listResult.first().imgSrcUrl
            } catch (e: Exception) {
                _photo.value = "error"
            }
        }
    }
}