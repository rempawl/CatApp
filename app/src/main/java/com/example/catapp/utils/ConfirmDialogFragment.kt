package com.example.catapp.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.catapp.R

class ConfirmDialogFragment(
    private val title: String,
    private val positiveText: String,
    private val listener: OnConfirmClickListener,
    private val message: String = ""
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        @Suppress("DEPRECATION")
        retainInstance = true

        return activity?.let {
            AlertDialog
                .Builder(requireContext())
                .setPositiveButton(positiveText) { _, _ ->
                    listener.onConfirm()
                }
                .setMessage(message)
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
                .setTitle(title)
                .create()


        } ?: throw IllegalStateException("activity is null")

    }

    interface OnConfirmClickListener {
        fun onConfirm()
    }
}