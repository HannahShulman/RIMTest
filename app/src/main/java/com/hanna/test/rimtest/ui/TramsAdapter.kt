package com.hanna.test.rimtest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hanna.test.rimtest.R
import com.hanna.test.rimtest.entities.Tram
import com.hanna.test.rimtest.utils.OpenForTesting

class TramsAdapter : ListAdapter<Tram, TramViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Tram>() {
            override fun areItemsTheSame(oldItem: Tram, newItem: Tram): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: Tram, newItem: Tram): Boolean {
                return oldItem.destination == newItem.destination && oldItem.dueMins == newItem.dueMins
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_tram_info, parent,
            false
        )
        return TramViewHolder(view)
    }

    override fun onBindViewHolder(holder: TramViewHolder, position: Int) {
        holder.bindData(currentList[position])
    }

}

@OpenForTesting
class TramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val destinationTv: TextView = itemView.findViewById(R.id.destination_tv)
    val dueMinsTv: TextView = itemView.findViewById(R.id.due_min_tv)
    fun bindData(data: Tram) {
        destinationTv.text = data.destination
        dueMinsTv.text = "---".takeIf { data.dueMins.isEmpty() }
            ?: data.dueMinutesToDisplayString()
    }
}