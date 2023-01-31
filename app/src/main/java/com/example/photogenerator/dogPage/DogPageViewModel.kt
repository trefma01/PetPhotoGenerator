package com.example.photogenerator.dogPage

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogenerator.network.DogApi
import kotlinx.coroutines.launch

class DogPageViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    private val _photo = MutableLiveData<String>()

    val photo: LiveData<String> = _photo

    init {
        getDogPhoto()
    }

    fun getDogPhoto() {
        viewModelScope.launch {
            try {
                val listResult = DogApi.retrofitService.getDogs()
                _photo.value = listResult.imgSrcUrl
                _status.value = listResult.status
                Log.d(TAG, listResult.status);
            } catch (e: Exception) {
                _status.value = "failed"
            }
        }
    }
}