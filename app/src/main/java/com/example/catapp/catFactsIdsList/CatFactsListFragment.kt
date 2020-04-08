package com.example.catapp.catFactsIdsList

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.MainActivity
import com.example.catapp.databinding.CatFactsListFragmentBinding
import javax.inject.Inject


class CatFactsListFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactsListFragment()
        const val SPAN_COUNT: Int = 2

    }


    @Inject
    lateinit var viewModel: CatFactsIdsViewModel

    @Inject
    lateinit var catFactsListAdapter: CatFactsListAdapter


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
        viewModel.items.observe(viewLifecycleOwner, Observer { factsIdsList ->
            catFactsListAdapter.submitList(factsIdsList)
//            catFactsListAdapter.notifyDataSetChanged()
        })

    }


    private fun setUpBinding(binding: CatFactsListFragmentBinding) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CatFactsListFragment.viewModel
            catFactsList.apply {
                adapter = catFactsListAdapter
                layoutManager =
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                        LinearLayoutManager(requireContext())
                    else
                        GridLayoutManager(requireContext(), SPAN_COUNT)

                setHasFixedSize(true)
            }
        }

    }

}
