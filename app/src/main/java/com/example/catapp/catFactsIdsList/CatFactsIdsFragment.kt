package com.example.catapp.catFactsIdsList

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.MainActivity
import com.example.catapp.R
import com.example.catapp.data.Result
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.databinding.CatFactsIdsFragmentBinding
import com.example.catapp.di.viewModel
import com.example.catapp.utils.ConfirmDialogFragment


open class CatFactsIdsFragment : Fragment(), ConfirmDialogFragment.OnConfirmClickListener {

    companion object {
        fun newInstance() = CatFactsIdsFragment()
        const val SPAN_COUNT: Int = 2
    }

    private val viewModel: CatFactsIdsViewModel by viewModel {
        injectViewModel()
    }

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }

    private val catFactsListAdapter: CatFactsListAdapter by lazy {

        CatFactsListAdapter(onItemClickListener = { factId ->
            navigateToFactDetails(factId)
        })
    }

    private var binding: CatFactsIdsFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectMembers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CatFactsIdsFragmentBinding
            .inflate(inflater, container, false)

        setUpBinding()
        setUpObservers()

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.catFactsList.adapter = null
        binding = null
    }

    @Suppress("UNCHECKED_CAST")
    private fun setUpObservers() {
        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result is Result.Success<*>)
                catFactsListAdapter.submitList(result.data as List<CatFactId>)
            else if (result is Result.Error) showErrorDialog(result.message)
        })
    }

    private fun showErrorDialog(message: String) {
        ConfirmDialogFragment(
            title = getString(R.string.error_msg),
            positiveText = getString(R.string.try_again),
            listener = this,
            message = message
        )
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


    protected open fun injectViewModel(): CatFactsIdsViewModel =
        appComponent.catFactsIdsViewModel

    protected open fun injectMembers() {
        appComponent.inject(this)
    }

    override fun onConfirm() {
        viewModel.fetchData()
    }

    private fun navigateToFactDetails(factId: CatFactId) {
        findNavController().navigate(
            CatFactsIdsFragmentDirections
                .navigationCatListFragmentToNavigationCatFactDetails(factId._id)
        )
    }

}
