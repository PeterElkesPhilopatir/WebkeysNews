package com.peter.webkeysnews.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.peter.webkeysnews.R
import com.peter.webkeysnews.network.ApiService
import com.peter.webkeysnews.pojo.Article
import com.peter.webkeysnews.pojo.datasource.PostDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ArrayList

class NewsViewModel(private val apiService: ApiService) : ViewModel() {
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

    val listData = Pager(PagingConfig(2)) {
        PostDataSource(apiService)
    }.flow.cachedIn(coroutineScope)

    init {
//        getData()

    }
//    private fun getData() {
//        coroutineScope.launch {
//            var getPropertiesDeferred = ApiService.NewsApi.retrofitService.getResponse(1,"apple",
//                    "5a22e7ef1ab04624b9526cb150e6ce74" )
//            try {
//                _status.value = ApiStatus.LOADING
//   +             val listResult = getPropertiesDeferred.await().articles
//                _data.value = listResult
//                _status.value = ApiStatus.DONE
//                Log.i("RESPONSE", _status.value.toString())
//            } catch (e: Exception) {
//                _status.value = ApiStatus.ERROR
//                _data.value = ArrayList()
//                Log.e("ERROR",e.message.toString())
//            }
//        }
//    }

    fun displayPropertyDetails(property: Article) {
        _navigateToSelectedProperty.value = property
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}