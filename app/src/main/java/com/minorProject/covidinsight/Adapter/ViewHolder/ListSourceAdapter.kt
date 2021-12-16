package com.minorProject.covidinsight.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.minorProject.covidinsight.Interface.ItemClickListener
import com.minorProject.covidinsight.ListNews
import com.minorProject.covidinsight.Model.WebSite
import com.minorProject.covidinsight.R

class ListSourceAdapter(private val context: Context, private val webSite: WebSite): RecyclerView.Adapter<ListSourceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.item_news, parent, false)
        return ListSourceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        holder.source_title.text = webSite.sources!![position].name
        holder.setItemClickListener(object : ItemClickListener{                    //ctrl+o
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ListNews::class.java)
                intent.putExtra("source", webSite.sources!![position].id)
                context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return webSite.sources!!.size
    }

}