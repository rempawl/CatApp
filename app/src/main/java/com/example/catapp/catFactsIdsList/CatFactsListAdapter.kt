package com.example.catapp.catFactsIdsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.data.CatFactId
import com.example.catapp.databinding.CatFactsListItemBinding
import javax.inject.Inject

class CatFactsListAdapter @Inject constructor() :
    ListAdapter<CatFactId, CatFactsListAdapter.CatFactViewHolder>(CatFactDiffCallBack()) {

    inner class CatFactViewHolder(private val binding: CatFactsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.container.setOnClickListener {
                navigateToCatFactDetails(it)
            }
        }

        private fun navigateToCatFactDetails(view: View) {
            val id = binding.catFactId?._id ?: return

            view.findNavController().navigate(
                CatFactsIdsFragmentDirections
                    .navigationCatListFragmentToNavigationCatFactDetails(id)
            )
        }

        fun bind(catFactId: CatFactId) {
            binding.catFactId = catFactId
            binding.executePendingBindings()
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