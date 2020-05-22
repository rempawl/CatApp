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
import com.example.catapp.data.Result
import com.example.catapp.data.entities.CatFact
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

    private var binding: CatFactDetailsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CatFactDetailsFragmentBinding.inflate(inflater, container, false)

        setUpObservers()
        setUpBinding()

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onConfirm() {
        viewModel.fetchData()
    }

    @Suppress("UNCHECKED_CAST")
    private fun setUpObservers() {
        viewModel.result.observe(viewLifecycleOwner, Observer { res ->
            if (res is Result.Error) showErrorDialog(res.message)
            else if (res is Result.Success) setUpText(res.data as CatFact)
        })
    }

    private fun setUpText(catFact: CatFact) {
        binding!!.apply {
            factText.text = getString(R.string.fact_text, catFact.text)
            updateDate.text = getString(R.string.update_date, catFact.updatedAt)
        }
    }

    private fun showErrorDialog(message: String) {
        ConfirmDialogFragment(
            title = getString(R.string.error_msg),
            positiveText = getString(R.string.try_again),
            listener = this,
            message = message
        ).show(childFragmentManager, "")
    }

    private fun setUpBinding() {
        binding!!.viewModel = this.viewModel
        binding!!.lifecycleOwner = viewLifecycleOwner

    }

    protected open fun injectViewModel(): CatFactDetailsViewModel =
        (activity as MainActivity).appComponent.catFactDetailsViewModelFactory.create(args.id)


}
