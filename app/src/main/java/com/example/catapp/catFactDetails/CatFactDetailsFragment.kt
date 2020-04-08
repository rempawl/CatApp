package com.example.catapp.catFactDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.catapp.MainActivity
import com.example.catapp.databinding.CatFactDetailsFragmentBinding
import com.example.catapp.di.viewModel
import javax.inject.Inject

class CatFactDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactDetailsFragment()
    }


    private val args: CatFactDetailsFragmentArgs by navArgs()

    private val viewModel: CatFactDetailsViewModel by viewModel {
        (activity as MainActivity).appComponent.catFactDetailsViewModelFactory.create(args.id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

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

    private fun setUpObservers() {
        viewModel.wasInitialLoadPerformed.observe(viewLifecycleOwner, Observer {
            if (!it) viewModel.init()
        })
    }

    private fun setUpBinding(binding: CatFactDetailsFragmentBinding) {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.items.observe(viewLifecycleOwner, Observer { fact ->
            binding.catFact = fact

        })


    }

}
