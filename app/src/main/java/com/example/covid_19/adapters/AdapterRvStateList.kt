package com.example.covid_19.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.R
import com.example.covid_19.modal.StateDataList


class AdapterRvStateList(
    private val context: Context,
    private val stateData: ArrayList<StateDataList> = ArrayList(),
    private val itemClicked: ItemClicked
) :
    RecyclerView.Adapter<AdapterRvStateList.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view_state, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return stateData[0].statewise.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvStateName.text = stateData[0].statewise[position].state
        holder.tvCasesState.text = stateData[0].statewise[position].confirmed
        holder.parent.setOnClickListener {
            itemClicked.onItemClicked(holder.adapterPosition)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStateName = itemView.findViewById<TextView>(R.id.tvState)!!
        val tvCasesState = itemView.findViewById<TextView>(R.id.tvCasesState)!!
        val parent = itemView.findViewById<ConstraintLayout>(R.id.parentItemViewState)!!

    }

    interface ItemClicked {
        fun onItemClicked(position: Int)
    }
}