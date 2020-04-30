package com.example.catapp.catFactsIdsList

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.MainActivity
import com.example.catapp.R
import com.example.catapp.databinding.CatFactsListFragmentBinding
import com.example.catapp.di.viewModel
import com.example.catapp.state.ConfirmDialogFragment
import javax.inject.Inject


open class CatFactsIdsFragment : Fragment(), ConfirmDialogFragment.OnConfirmClickListener {

    companion object {
        fun newInstance() = CatFactsIdsFragment()
        const val SPAN_COUNT: Int = 2
    }


    val viewModel: CatFactsIdsViewModel by viewModel {
        injectViewModel()
    }


    @Inject
    lateinit var catFactsListAdapter: CatFactsListAdapter

    private var binding: CatFactsListFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectMembers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CatFactsListFragmentBinding
            .inflate(inflater, container, false)

        setUpBinding()
        setUpObservers()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.catFactsList?.adapter = null
        binding = null
    }

    private fun setUpObservers() {
        viewModel.factsIds.observe(viewLifecycleOwner, Observer { factsIdsList ->
            catFactsListAdapter.submitList(factsIdsList)
        })
        viewModel.stateModel.isError.observe(viewLifecycleOwner, Observer { isError ->
            if (isError) showErrorDialog()
        })
    }

    private fun showErrorDialog() {
        ConfirmDialogFragment(getString(R.string.error_msg), getString(R.string.try_again), this)
            .show(childFragmentManager, "")

    }


    private fun setUpBinding() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CatFactsIdsFragment.viewModel
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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    open fun injectViewModel(): CatFactsIdsViewModel =
        (activity as MainActivity).appComponent.catFactsIdsViewModel

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    open fun injectMembers() {
        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onConfirm() {
        viewModel.refresh()
    }


}
