package com.minorProject.covidinsight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CautionRVAdapter(private var cautionList: List<CautionModel>):
    RecyclerView.Adapter<CautionRVAdapter.CautionRVViewHolder>() {

    class CautionRVViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val precautionArrow : ImageView = itemView.findViewById(R.id.PrecautionArrow)
        val textCaution : TextView = itemView.findViewById(R.id.cautionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CautionRVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.caution_rv_items, parent, false)
        return CautionRVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CautionRVViewHolder, position: Int) {
        val cautionData = cautionList[position]
        holder.precautionArrow.setImageResource(cautionData.cautionArrow)
        holder.textCaution.text = cautionData.textPrecaution
    }

    override fun getItemCount(): Int {
        return cautionList.size
    }
}