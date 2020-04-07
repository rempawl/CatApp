package com.example.catapp.catFactDetails

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.catapp.MainActivity

import com.example.catapp.R
import javax.inject.Inject

class CatFactDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactDetailsFragment()
    }

    @Inject
    lateinit var viewModel: CatFactDetailsViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cat_fact_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
