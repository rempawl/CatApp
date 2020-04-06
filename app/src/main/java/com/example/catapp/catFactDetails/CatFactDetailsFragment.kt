package com.example.catapp.catFactDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.catapp.R

class CatFactDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactDetailsFragment()
    }

    private lateinit var viewModel: CatFactDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cat_fact_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CatFactDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
