package com.peter.webkeysnews.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peter.webkeysnews.R
import com.peter.webkeysnews.network.ApiService
import com.peter.webkeysnews.pojo.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ArrayList

class NewsViewModel : ViewModel(){
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    enum class ApiStatus { LOADING, ERROR, DONE }

    private val _data = MutableLiveData<List<Article>>()
    val data: LiveData<List<Article>>
        get() = _data

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _navigateToSelectedProperty = MutableLiveData<Article>()
    val navigateToSelectedProperty: LiveData<Article>
        get() = _navigateToSelectedProperty

    init {
        getData()
    }
    private fun getData() {
        coroutineScope.launch {
            var getPropertiesDeferred = ApiService.NewsApi.retrofitService.getResponse("apple","" +
                    "" )
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getPropertiesDeferred.await().articles
                _data.value = listResult
                _status.value = ApiStatus.DONE
                Log.i("RESPONSE", _status.value.toString())
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _data.value = ArrayList()
                Log.e("ERROR",e.message.toString())
            }
        }
    }

    fun displayPropertyDetails(property: Article) {
        _navigateToSelectedProperty.value = property
    }
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}