package com.example.catapp.catFactsIdsList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.MainActivity
import com.example.catapp.data.CatFactId
import com.example.catapp.databinding.CatFactsListFragmentBinding
import javax.inject.Inject

interface JsonParser {
    fun parseCatFactIds(factIds: String): MutableList<CatFactId>
}


class DefaultJsonParser @Inject constructor() : JsonParser {
    override fun parseCatFactIds(factIds: String): MutableList<CatFactId> {
        val iterator = Regex("_id\\W*(\\w*)").findAll(factIds).iterator()
        val result = mutableListOf<CatFactId>()

        while ((iterator.hasNext())) {
            val id = iterator.next().groupValues[1]
            result.add(
                CatFactId(id)
            )
        }

        return result
    }

}

class CatFactsListFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactsListFragment()
    }


    @Inject
    lateinit var viewModel: CatFactsListViewModel

    @Inject
    lateinit var catFactsListAdapter: CatFactsListAdapter

    @Inject
    lateinit var jsonParser: JsonParser

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CatFactsListFragmentBinding
            .inflate(inflater, container, false)
            ?: throw  IllegalStateException(" binding is null")

        setUpBinding(binding)
        setUpObservers()
        return binding.root
    }

    private fun setUpObservers() {
            viewModel.factsIdsList.observe(viewLifecycleOwner, Observer{factsIdsList ->
                catFactsListAdapter.submitList(factsIdsList)
                catFactsListAdapter.notifyDataSetChanged()
            })

    }



    private fun setUpBinding(binding: CatFactsListFragmentBinding) {
        binding.apply {
            viewModel = this@CatFactsListFragment.viewModel
            catFactsList.apply {
                adapter = catFactsListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
