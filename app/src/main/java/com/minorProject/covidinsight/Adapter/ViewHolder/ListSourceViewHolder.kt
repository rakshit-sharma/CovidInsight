package com.minorProject.covidinsight.Adapter.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.minorProject.covidinsight.Interface.ItemClickListener
import com.minorProject.covidinsight.R


class ListSourceViewHolder(itemView:View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener

    var source_title:TextView = itemView.findViewById(R.id.source_news_name)

    init {
        itemView.setOnClickListener(this)
    }


    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!,adapterPosition)
    }



}