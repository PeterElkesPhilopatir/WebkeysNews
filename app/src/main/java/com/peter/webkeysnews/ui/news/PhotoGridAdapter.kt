package com.peter.webkeysnews.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter.webkeysnews.databinding.GridViewItemBinding
import com.peter.webkeysnews.pojo.Article

class PhotoGridAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Article, NewsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NewsViewHolder {
        return NewsViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(news)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

class NewsViewHolder(private var binding: GridViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article) {
        binding.property = article
        binding.executePendingBindings()
    }
}

class OnClickListener(val clickListener: (article: Article) -> Unit) {
    fun onClick(article:Article) = clickListener(article)
}


