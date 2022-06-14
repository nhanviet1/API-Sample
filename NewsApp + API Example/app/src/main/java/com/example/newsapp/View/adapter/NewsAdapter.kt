package com.example.newsapp.View.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.Model2.Article
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsItemLayoutBinding

class NewsAdapter(
    private val listNews: List<Article>,
    private val callback: Callback
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(data: Article, position: Int) {
            val binding = NewsItemLayoutBinding.bind(itemView)
            binding.tvTitle.setText(data.title)
            val res = itemView.context
            val ImageView = binding.ivPicture
            Glide.with(res).load(data.urlToImage).into(ImageView)
            binding.root.setOnClickListener {
                callback.onClickItem(item = data, position)
            }
        }
    }

    interface Callback {
        fun onClickItem(item: Article ,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listNews[holder.bindingAdapterPosition], position)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

}