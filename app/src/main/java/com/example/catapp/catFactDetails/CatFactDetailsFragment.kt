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
import javax.inject.Inject

class CatFactDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactDetailsFragment()
    }

    @Inject
    lateinit var viewModelFactory: CatFactDetailsViewModel.Factory

    private val args: CatFactDetailsFragmentArgs by navArgs()

    private val viewModel: CatFactDetailsViewModel by lazy {
        viewModelFactory.create(args.id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CatFactDetailsFragmentBinding
            .inflate(inflater, container, false)



        setUpBinding(binding)

        return binding.root
    }

    private fun setUpBinding(binding: CatFactDetailsFragmentBinding) {
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.items.observe(viewLifecycleOwner, Observer { fact ->
            binding.catFact = fact

        })


    }

}
