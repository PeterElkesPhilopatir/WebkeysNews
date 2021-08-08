package com.peter.webkeysnews.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.peter.webkeysnews.pojo.Article

class DetailsViewModel(@Suppress("UNUSED_PARAMETER") article: Article, app: Application) :
    AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Article>()
    val selectedProperty: LiveData<Article>
        get() = _selectedProperty

    private val _navToInternet = MutableLiveData<String>()
    val navToInternet: LiveData<String>
        get() = _navToInternet

    private val _navToShare = MutableLiveData<String>()
    val navToShare: LiveData<String>
        get() = _navToShare


    init {
        _selectedProperty.value = article
        Log.i("IMG_SRC", article.urlToImage)
        Log.i("DATE", article.publishedAt)
        Log.i("SRC_LINK", article.url)
    }


    fun displayInternet(property: String) {
        _navToInternet.value = property
    }

    fun displayInternetComplete() {
        _navToInternet.value = null
    }

    fun displayShare(property: String) {
        _navToShare.value = property
    }

    fun displayShareComplete() {
        _navToShare.value = null
    }




}