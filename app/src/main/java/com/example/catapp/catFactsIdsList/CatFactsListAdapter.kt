package com.example.catapp.catFactsIdsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.databinding.CatFactsListItemBinding
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class CatFactsListAdapter @AssistedInject constructor(
    @Assisted private val onItemClickListener: (CatFactId) -> Unit
) : ListAdapter<CatFactId, CatFactsListAdapter.CatFactViewHolder>(CatFactDiffCallBack()) {

    @AssistedInject.Factory
    interface Factory {
        fun create(onItemClickListener: (CatFactId) -> Unit): CatFactsListAdapter
    }

    inner class CatFactViewHolder(private val binding: CatFactsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(catFactId: CatFactId) {
            binding.apply {
                this.catFactId = catFactId
                container.setOnClickListener { onItemClickListener(catFactId) }
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CatFactsListItemBinding.inflate(inflater, parent, false)
        return CatFactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatFactViewHolder, position: Int) {
        val id = getItem(position)
        holder.bind(id)
    }
}

private class CatFactDiffCallBack : DiffUtil.ItemCallback<CatFactId>() {
    override fun areItemsTheSame(oldItem: CatFactId, newItem: CatFactId): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CatFactId, newItem: CatFactId): Boolean {
        return oldItem._id == newItem._id
    }

}