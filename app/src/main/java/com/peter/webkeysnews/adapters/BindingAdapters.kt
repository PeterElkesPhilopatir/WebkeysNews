package com.peter.webkeysnews

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.peter.webkeysnews.pojo.Article
import com.peter.webkeysnews.ui.news.NewsViewModel
import com.peter.webkeysnews.ui.news.PhotoGridAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Log.i("IMG",imgUrl.toString())


    try {
        imgUrl?.let {
            var imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(imgView)
        }
    } catch (e: Exception) {
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?) {
//    val adapter = recyclerView.adapter as PhotoGridAdapter
//    adapter.submitList(data)
}

@BindingAdapter("txtBinder")
fun bindTextView(tv : TextView, data: String?) {
   tv.text = data
    Log.i("TITLE",data.toString())
}
@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: NewsViewModel.ApiStatus?) {
    when (status) {
        NewsViewModel.ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NewsViewModel.ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NewsViewModel.ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}



