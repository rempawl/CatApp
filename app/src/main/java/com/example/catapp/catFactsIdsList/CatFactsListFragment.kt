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
import com.example.catapp.di.viewModel
import javax.inject.Inject


class CatFactsListFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactsListFragment()
        const val SPAN_COUNT: Int = 2

    }


    val viewModel: CatFactsIdsViewModel by viewModel {
        (activity as MainActivity).appComponent.catFactsIdsViewModel
    }

    @Inject
    lateinit var catFactsListAdapter: CatFactsListAdapter

    var binding : CatFactsListFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = CatFactsListFragmentBinding
            .inflate(inflater, container, false)

        setUpBinding()
        setUpObservers()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.catFactsList?.adapter = null
        binding  = null
    }

    private fun setUpObservers() {
        viewModel.items.observe(viewLifecycleOwner, Observer { factsIdsList ->
            catFactsListAdapter.submitList(factsIdsList)
        })
        viewModel.wasInitialLoadPerformed.observe(viewLifecycleOwner, Observer {
            if (!it) viewModel.init()
        })
    }


    private fun setUpBinding() {
        binding?.apply {
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
