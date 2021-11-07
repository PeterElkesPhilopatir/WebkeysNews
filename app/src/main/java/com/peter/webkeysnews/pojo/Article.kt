package com.peter.webkeysnews.pojo

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var id: String,
    var urlToImage: String,
    var title: String,
    var url: String,
    var auther: String,
    var description: String,
    var publishedAt: String,
    var content: String
) : Parcelable
