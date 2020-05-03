package com.example.catapp.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.catapp.state.State

@BindingAdapter("showWhen")
fun showWhen(view: View, shouldShow: Boolean) {
    view.visibility = if (shouldShow) View.VISIBLE else View.GONE
}





