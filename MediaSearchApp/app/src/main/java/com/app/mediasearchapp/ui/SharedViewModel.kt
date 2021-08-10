package com.app.mediasearchapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mediasearchapp.model.Result
import com.app.mediasearchapp.network.ServiceMediaApi
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private var position = 0

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _results = MutableLiveData<List<Result>>()
    val results: LiveData<List<Result>> = _results

    private val _resultsSize = MutableLiveData<Int>()
    val resultsSize: LiveData<Int> = _resultsSize

    //Set position of item in results list
    fun setPosition(pos: Int) {
        position = pos
    }

    //Get position of item in results list
    fun getPosition(): Int {
        return position
    }

    //Get results from api
    fun getMediaApiCall(
        term: String,
        mediaType: String
    ) {
        viewModelScope.launch {
             try {
                val response = ServiceMediaApi.retrofitService.getMediaData(term, mediaType)
                val mediaData = response.body()
                if (response.isSuccessful && mediaData != null) {
                    _results.value = mediaData.results
                    _resultsSize.value = mediaData.resultCount
                    _error.value = false
                } else {
                    _error.value = true
                }
            } catch (exc: Exception) {
                _error.value = true
                Log.e("SharedViewModel", "Exception:" + exc.message)
            }
        }
    }
}