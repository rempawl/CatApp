package com.example.catapp.catFactDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.catapp.MainActivity
import com.example.catapp.R
import com.example.catapp.databinding.CatFactDetailsFragmentBinding
import com.example.catapp.di.viewModel
import com.example.catapp.state.ErrorDialogFragment

class FakeCatFactDetailsFragment : CatFactDetailsFragment()  {

    override fun injectViewModel(): CatFactDetailsViewModel {
        return viewModel
    }

    companion object {
        lateinit var viewModel: CatFactDetailsViewModel
    }

}

open class CatFactDetailsFragment : Fragment(),ErrorDialogFragment.OnConfirmClickListener {

    companion object {
        fun newInstance() = CatFactDetailsFragment()
    }


    private val args: CatFactDetailsFragmentArgs by navArgs()

    private val viewModel: CatFactDetailsViewModel by viewModel {
        injectViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CatFactDetailsFragmentBinding
            .inflate(inflater, container, false)

        setUpObservers()
        setUpBinding(binding)

        return binding.root
    }

    override fun onConfirm() {
        viewModel.refresh()
    }

    private fun setUpObservers() {
        viewModel.stateModel.isError.observe(viewLifecycleOwner, Observer { isError ->
            if (isError) showErrorDialog()
        })
        viewModel.wasInitialLoadPerformed.observe(viewLifecycleOwner, Observer {
            if (!it) viewModel.init()
        })
    }

    private fun showErrorDialog() {
        ErrorDialogFragment(getString(R.string.error_msg), getString(R.string.try_again))
            .show(childFragmentManager, "")
    }

    private fun setUpBinding(binding: CatFactDetailsFragmentBinding) {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.catFactDetail.observe(viewLifecycleOwner, Observer { fact ->
            binding.catFact = fact
        })


    }

    protected open fun injectViewModel(): CatFactDetailsViewModel =
        (activity as MainActivity).appComponent.catFactDetailsViewModelFactory.create(args.id)


}
