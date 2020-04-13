package com.example.catapp.state

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.catapp.R

@Suppress("DEPRECATION")
class ErrorDialogFragment(
    private val title: String,
    private val positiveText: String
) : DialogFragment() {


    lateinit var listener: OnConfirmClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frag = parentFragment ?: throw IllegalStateException("no parent fragment")
        when (frag) {
            is OnConfirmClickListener -> listener = frag
            else -> throw NotImplementedError("Parent fragment should implement OnConfirmSelectedListener interface")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true

        return activity?.let {
            AlertDialog
                .Builder(requireContext())
                .setPositiveButton(positiveText) { _, _ ->
                    listener.onConfirm()
                    dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
                .setTitle(title)
                .create()


        } ?: throw IllegalStateException("activity is null")

    }

    interface OnConfirmClickListener {
        fun onConfirm()
    }
}