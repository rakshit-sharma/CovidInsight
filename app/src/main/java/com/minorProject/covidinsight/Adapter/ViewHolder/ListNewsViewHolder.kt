package com.minorProject.covidinsight.Adapter.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.curioustechizen.ago.RelativeTimeTextView
import com.minorProject.covidinsight.Interface.ItemClickListener
import com.minorProject.covidinsight.R

class ListNewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private lateinit var itemClickListener: ItemClickListener

    var article_title:TextView = itemView.findViewById(R.id.article_title)
    var article_image:ImageView = itemView.findViewById(R.id.article_image)
    var article_time:RelativeTimeTextView = itemView.findViewById(R.id.article_time)

    init {
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View) {
        itemClickListener.onClick(v, adapterPosition)
    }
}