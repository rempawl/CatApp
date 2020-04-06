package com.example.catapp.catList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.data.CatFact
import com.example.catapp.databinding.CatFactsListItemBinding

class CatFactsListAdapter : ListAdapter<CatFact, CatFactsListAdapter.CatFactViewHolder>(CatFactDiffCallBack()) {
    inner class CatFactViewHolder(binding : CatFactsListItemBinding ) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFactViewHolder {
        TODO()
    }

    override fun onBindViewHolder(holder: CatFactViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

private class CatFactDiffCallBack :DiffUtil.ItemCallback<CatFact>(){
    override fun areItemsTheSame(oldItem: CatFact, newItem: CatFact): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: CatFact, newItem: CatFact): Boolean {
        TODO("Not yet implemented")
    }

}