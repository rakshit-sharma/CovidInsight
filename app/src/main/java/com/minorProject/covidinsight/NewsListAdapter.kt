package com.minorProject.covidinsight

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val newsList: ArrayList<NewsRVModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(newsList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(updatedNews: ArrayList<NewsRVModel>){
        newsList.clear()
        newsList.addAll(updatedNews)

        notifyDataSetChanged()
    }
}


class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView:TextView = itemView.findViewById(R.id.newsTitle)
    val image:ImageView = itemView.findViewById(R.id.newsImage)
    val author:TextView = itemView.findViewById(R.id.newsAuthor)
}

interface NewsItemClicked{
    fun onItemClicked(newsList: NewsRVModel)
}



