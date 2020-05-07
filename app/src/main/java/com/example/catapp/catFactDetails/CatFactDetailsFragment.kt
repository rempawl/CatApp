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
import com.example.catapp.data.State
import com.example.catapp.databinding.CatFactDetailsFragmentBinding
import com.example.catapp.di.viewModel
import com.example.catapp.utils.ConfirmDialogFragment


open class CatFactDetailsFragment : Fragment(), ConfirmDialogFragment.OnConfirmClickListener {

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
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            if (state is State.Error) showErrorDialog(state.message)
        })
    }

    private fun showErrorDialog(message: String) {
        ConfirmDialogFragment(
            title =  getString(R.string.error_msg) ,
            positiveText = getString(R.string.try_again),
            listener = this,
            message = message
        ).show(childFragmentManager, "")
    }

    private fun setUpBinding(binding: CatFactDetailsFragmentBinding) {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    protected open fun injectViewModel(): CatFactDetailsViewModel =
        (activity as MainActivity).appComponent.catFactDetailsViewModelFactory.create(args.id)


}
